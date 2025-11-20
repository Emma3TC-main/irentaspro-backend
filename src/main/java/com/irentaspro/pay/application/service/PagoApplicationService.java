package com.irentaspro.pay.application.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irentaspro.common.domain.model.valueobjects.Monto;
import com.irentaspro.common.infrastructure.DomainEventPublisher;
import com.irentaspro.pay.application.command.EmitirComprobanteCommand;
import com.irentaspro.pay.application.dto.PagoDTO;
import com.irentaspro.pay.application.mapper.PagoMapper;
import com.irentaspro.pay.domain.gateway.ContratoDTO;
import com.irentaspro.pay.domain.gateway.ContratoGateway;
import com.irentaspro.pay.domain.model.ComprobanteFiscal;
import com.irentaspro.pay.domain.model.Pago;
import com.irentaspro.pay.domain.repository.PagoRepositorio;
import com.irentaspro.pay.domain.services.PagoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PagoApplicationService {

    private final PagoRepositorio pagoRepositorio;
    private final PagoService pagoService;
    private final ContratoGateway contratoGateway;
    private final DomainEventPublisher eventPublisher;

    /**
     * Registra un nuevo pago en el sistema. Valida contra el servicio Contratos.
     */
    public PagoDTO registrarPago(PagoDTO dto) {

        // Validar existencia y pertenencia del contrato
        ContratoDTO contrato = contratoGateway.obtenerContrato(dto.getContratoId())
                .orElseThrow(() -> new IllegalStateException("Contrato no encontrado"));

        if (!contrato.usuarioId().equals(dto.getUsuarioId())) {
            throw new IllegalArgumentException("El usuario no pertenece al contrato");
        }

        if (dto.getMonto().compareTo(contrato.montoPendiente()) > 0) {
            throw new IllegalArgumentException("El monto excede el saldo pendiente del contrato");
        }

        // Iniciar pago en dominio (crea referencia PSP y estado 'registrado')
        Pago pago = pagoService.iniciarPago(
                dto.getContratoId(),
                dto.getUsuarioId(),
                new Monto(dto.getMonto(), dto.getMoneda()),
                dto.getMetodo(),
                dto.getTipoPago());

        // Persistir
        Pago guardado = pagoRepositorio.guardar(pago);

        // Publicar eventos si el agregado registró alguno (p.ej. si iniciarPago registra algo)
        eventPublisher.publish(guardado.getEventos());
        guardado.limpiarEventos();

        return PagoMapper.toDTO(guardado);
    }

    /**
     * Confirma un pago existente por su ID.
     */
    public void confirmarPago(UUID id) {
        Pago pago = pagoRepositorio.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Pago no encontrado"));

        pagoService.confirmar(pago);
        Pago guardado = pagoRepositorio.guardar(pago);

        // Publicar eventos generados por el agregado (PagoConfirmado, etc.)
        eventPublisher.publish(guardado.getEventos());
        guardado.limpiarEventos();
    }

    /**
     * Obtiene un pago por su ID.
     */
    @Transactional(readOnly = true)
    public PagoDTO obtenerPago(UUID id) {
        Pago pago = pagoRepositorio.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Pago no encontrado"));

        return PagoMapper.toDTO(pago);
    }

    public ComprobanteFiscal emitirComprobante(EmitirComprobanteCommand command) {
        Pago pago = pagoRepositorio.buscarPorId(command.getPagoId())
                .orElseThrow(() -> new IllegalArgumentException("Pago no encontrado"));
        if (!pago.estaConfirmado()) {
            throw new IllegalStateException("No se puede emitir comprobante: el pago no está confirmado");
        }

        ComprobanteFiscal comprobante = new ComprobanteFiscal(pago, command.getTipo());
        pago.generarComprobante(comprobante);
        Pago guardado = pagoRepositorio.guardar(pago);

        // publicar evento ComprobanteEmitido
        eventPublisher.publish(guardado.getEventos());
        guardado.limpiarEventos();

        return comprobante;
    }

}
