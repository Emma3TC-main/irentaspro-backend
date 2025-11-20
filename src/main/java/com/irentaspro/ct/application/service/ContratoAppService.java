package com.irentaspro.ct.application.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.irentaspro.common.domain.model.DomainEvent;
import com.irentaspro.common.domain.model.valueobjects.Monto;
import com.irentaspro.common.infrastructure.DomainEventPublisher;
import com.irentaspro.ct.application.dto.CalendarioCuotasDTO;
import com.irentaspro.ct.application.dto.ClausulaDTO;
import com.irentaspro.ct.application.dto.ContratoDTO;
import com.irentaspro.ct.application.dto.ContratoResponseDTO;
import com.irentaspro.ct.application.dto.CuotaContratoDTO;
import com.irentaspro.ct.application.dto.CrearContratoDTO;
import com.irentaspro.ct.application.dto.FirmarContratoDTO;
import com.irentaspro.ct.application.dto.FinalizarContratoDTO;
import com.irentaspro.ct.application.dto.RegistrarPagoContratoDTO;
import com.irentaspro.ct.application.dto.RenovarContratoDTO;
import com.irentaspro.ct.application.dto.events.CalendarioPagosGeneradoPayload;
import com.irentaspro.ct.application.mapper.ContratoApplicationMapper;
import com.irentaspro.ct.domain.model.Clausula;
import com.irentaspro.ct.domain.model.Contrato;
import com.irentaspro.ct.domain.model.FirmaDigital;
import com.irentaspro.ct.domain.model.valueobjects.PeriodoContrato;
import com.irentaspro.ct.domain.model.valueobjects.CuotaContrato;
import com.irentaspro.ct.domain.repository.ContratoRepositorio;
import com.irentaspro.ct.domain.services.ContratoService;
import com.irentaspro.ct.infrastructure.adapters.out.entities.CuotaJpaEntity;
import com.irentaspro.ct.infrastructure.adapters.out.jpa.CuotaJpaRepository;

import lombok.RequiredArgsConstructor;

/**
 * Servicio de aplicación que expone casos de uso del agregado Contrato.
 *
 * - Crea contratos
 * - Firma contratos (usa ContratoService + IFirmaAdapter vía dominio)
 * - Genera calendario de cuotas
 * - Registra pagos
 * - Renueva y finaliza contratos
 * - Obtiene/lista contratos
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ContratoAppService {

    private final ContratoRepositorio contratoRepositorio;
    private final ContratoService contratoService; // servicio de dominio para firma (usa IFirmaAdapter)
    private final DomainEventPublisher eventPublisher;
    private final CuotaJpaRepository cuotaRepository;

    // -----------------------
    // Crear Contrato
    // -----------------------
    public ContratoResponseDTO crearContrato(CrearContratoDTO dto) {
        // validar campos mínimos
        if (dto == null)
            throw new IllegalArgumentException("Payload de creación es requerido.");
        if (dto.getPropiedadId() == null)
            throw new IllegalArgumentException("propiedadId es requerido.");
        if (dto.getPropietarioId() == null)
            throw new IllegalArgumentException("propietarioId es requerido.");
        if (dto.getInquilinoId() == null)
            throw new IllegalArgumentException("inquilinoId es requerido.");
        if (dto.getInicio() == null || dto.getFin() == null)
            throw new IllegalArgumentException("Fechas de periodo requeridas.");
        if (dto.getMonto() == null || dto.getMonto().compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Monto inválido.");

        // mapear DTO -> dominio
        PeriodoContrato periodo = new PeriodoContrato(dto.getInicio(), dto.getFin());
        Monto monto = new Monto(dto.getMonto(), dto.getMoneda() == null ? "PEN" : dto.getMoneda());

        List<Clausula> clausulas = dto.getClausulas() == null
                ? List.of()
                : dto.getClausulas().stream()
                        .map(c -> new Clausula(c.getTipo() == null ? "GENERAL" : c.getTipo(), c.getDescripcion()))
                        .collect(Collectors.toList());

        Contrato contrato = new Contrato(
                dto.getPropiedadId(),
                dto.getPropietarioId(),
                dto.getInquilinoId(),
                periodo,
                monto,
                clausulas);

        contrato.validarInvariantes();

        Contrato guardado = contratoRepositorio.guardar(contrato);

        return toResponseDTO(guardado);
    }

    // -----------------------
    // Firmar Contrato
    // -----------------------
    public ContratoResponseDTO firmarContrato(UUID contratoId, FirmarContratoDTO dto) {
        Contrato contrato = contratoRepositorio.buscarPorId(contratoId)
                .orElseThrow(() -> new IllegalArgumentException("Contrato no encontrado"));

        contratoService.firmarContrato(contrato);

        Contrato guardado = contratoRepositorio.guardar(contrato);

        publishAndClear(guardado);

        return toResponseDTO(guardado);
    }

    // -----------------------
    // Generar Calendario
    // -----------------------
    public CalendarioCuotasDTO generarCalendario(UUID contratoId) {

        Contrato contrato = contratoRepositorio.buscarPorId(contratoId)
                .orElseThrow(() -> new IllegalArgumentException("Contrato no encontrado"));

        // Genera cuotas dentro del agregado (dominio)
        List<CuotaContrato> cuotas = contrato.generarCalendarioPagos();

        // Persistir contrato completo (incluye las nuevas cuotas)
        Contrato guardado = contratoRepositorio.guardar(contrato);

        // Publicar eventos de dominio
        publishAndClear(guardado);

        // DTO de salida
        CalendarioCuotasDTO resultado = new CalendarioCuotasDTO();
        resultado.setCuotas(
                cuotas.stream()
                        .map(c -> new CuotaContratoDTO(
                                c.getNumero(),
                                c.getFechaPago(),
                                c.getMonto(),
                                c.isPagado()))
                        .collect(Collectors.toList()));

        return resultado;
    }

    // -----------------------
    // Registrar Pago
    // -----------------------
    public ContratoResponseDTO registrarPago(UUID contratoId, RegistrarPagoContratoDTO dto) {
        Contrato contrato = contratoRepositorio.buscarPorId(contratoId)
                .orElseThrow(() -> new IllegalArgumentException("Contrato no encontrado"));

        if (dto == null || dto.getMonto() == null || dto.getMonto().compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Monto de pago inválido.");

        // Registrar pago en el agregado
        contrato.registrarPago(dto.getMonto());

        // Persistir cambios del contrato
        Contrato guardado = contratoRepositorio.guardar(contrato);

        // Actualizar tabla de cuotas (lógica simple: marcar cuotas completas que
        // coincidan con monto)
        var pendientes = cuotaRepository.findByContratoIdAndPagadoFalse(contratoId);
        BigDecimal restante = dto.getMonto();
        for (var cuotaEntity : pendientes) {
            if (restante.compareTo(BigDecimal.ZERO) <= 0)
                break;
            if (restante.compareTo(cuotaEntity.getMonto()) >= 0) {
                cuotaEntity.setPagado(true);
                restante = restante.subtract(cuotaEntity.getMonto());
                cuotaRepository.save(cuotaEntity);
            } else {
                // pago parcial: no marcamos como pagado (modelo actual no soporta saldo por
                // cuota)
                break;
            }
        }

        publishAndClear(guardado);
        return toResponseDTO(guardado);
    }

    // -----------------------
    // Renovar Contrato
    // -----------------------
    public ContratoResponseDTO renovar(UUID contratoId, RenovarContratoDTO dto) {
        Contrato contrato = contratoRepositorio.buscarPorId(contratoId)
                .orElseThrow(() -> new IllegalArgumentException("Contrato no encontrado"));

        if (dto == null || dto.getNuevoInicio() == null || dto.getNuevoFin() == null)
            throw new IllegalArgumentException("Fechas de renovación inválidas.");

        PeriodoContrato nuevoPeriodo = new PeriodoContrato(dto.getNuevoInicio(), dto.getNuevoFin());
        contrato.renovar(nuevoPeriodo);

        Contrato guardado = contratoRepositorio.guardar(contrato);
        publishAndClear(guardado);

        return toResponseDTO(guardado);
    }

    // -----------------------
    // Finalizar Contrato
    // -----------------------
    public ContratoResponseDTO finalizar(UUID contratoId, FinalizarContratoDTO dto) {
        Contrato contrato = contratoRepositorio.buscarPorId(contratoId)
                .orElseThrow(() -> new IllegalArgumentException("Contrato no encontrado"));

        // validar reglas de negocio: no permitir finalizar si hay saldo pendiente
        contrato.finalizar();

        Contrato guardado = contratoRepositorio.guardar(contrato);
        publishAndClear(guardado);

        return toResponseDTO(guardado);
    }

    // -----------------------
    // Obtener / Listar
    // -----------------------
    @Transactional(readOnly = true)
    public ContratoDTO obtenerContrato(UUID contratoId) {
        Contrato contrato = contratoRepositorio.buscarPorId(contratoId)
                .orElseThrow(() -> new IllegalArgumentException("Contrato no encontrado"));
        return ContratoApplicationMapper.toDTO(contrato);
    }

    @Transactional(readOnly = true)
    public List<ContratoDTO> listarContratos() {
        return contratoRepositorio.buscarTodos().stream().map(ContratoApplicationMapper::toDTO)
                .collect(Collectors.toList());
    }

    // -----------------------
    // Helpers
    // -----------------------
    private ContratoResponseDTO toResponseDTO(Contrato contrato) {
        ContratoResponseDTO r = new ContratoResponseDTO();
        r.setId(contrato.getId());
        r.setEstado(contrato.getEstado().toString());
        r.setMontoPendiente(contrato.getMontoPendiente());
        return r;
    }

    @SuppressWarnings("unchecked")
    private void publishAndClear(Contrato agregado) {
        if (agregado == null)
            return;
        var events = agregado.getEventos();
        if (events != null && !events.isEmpty()) {
            // publicar usando DomainEventPublisher
            try {
                eventPublisher.publish((List<DomainEvent>) (List<?>) events);
            } catch (ClassCastException ex) {
                // si no coincide la firma, omitir (se asume que Outbox se encargará)
            }
            agregado.limpiarEventos();
        }
    }
}
