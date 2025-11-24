package com.irentaspro.pay.application.service;

import java.math.BigDecimal;
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
import com.irentaspro.pay.infrastructure.adapters.out.gateway.PaypalGatewayAdapter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PagoApplicationService {

    private final PagoRepositorio pagoRepositorio;
    private final PagoService pagoService;
    private final ContratoGateway contratoGateway;
    private final DomainEventPublisher eventPublisher;
    private final PaypalGatewayAdapter paypalGateway;

    // ------------------------------
    // Pagos normales con contrato
    // ------------------------------
    @Transactional
    public PagoDTO registrarPago(PagoDTO dto) {
        ContratoDTO contrato = contratoGateway.obtenerContrato(dto.getContratoId())
                .orElseThrow(() -> new IllegalStateException("Contrato no encontrado"));

        if (!contrato.usuarioId().equals(dto.getUsuarioId()))
            throw new IllegalArgumentException("El usuario no pertenece al contrato");

        if (dto.getMonto().compareTo(contrato.montoPendiente()) > 0)
            throw new IllegalArgumentException("El monto excede el saldo pendiente");

        return procesarPago(dto.getContratoId(), dto.getUsuarioId(), dto.getMonto(),
                dto.getMoneda(), dto.getMetodo(), dto.getTipoPago());
    }

    // ------------------------------
    // Pagos Premium sin contrato
    // ------------------------------
    @Transactional
    public PagoDTO iniciarPagoPremium(UUID usuarioId, BigDecimal monto, String moneda, String metodo) {
        return procesarPago(null, usuarioId, monto, moneda, metodo, "MEMBRESIA_PREMIUM");
    }

    // ------------------------------
    // Lógica común de creación de pago
    // ------------------------------
    @Transactional
    private PagoDTO procesarPago(UUID contratoId, UUID usuarioId, BigDecimal monto, String moneda,
            String metodo, String tipoPago) {

        // 1️⃣ Crear pago (estado: PENDIENTE)
        Pago pago = pagoService.iniciarPago(
                contratoId,
                usuarioId,
                new Monto(monto, moneda),
                metodo,
                tipoPago);

        // 2️⃣ Guardar pago por PRIMERA VEZ → ya tendrá ID
        pago = pagoRepositorio.guardar(pago);

        // 3️⃣ Si es PayPal, recién ahora puedo crear la orden
        if ("paypal".equalsIgnoreCase(metodo)) {
            String referencia = paypalGateway.procesarPago(
                    monto, moneda, metodo, pago.getId().toString());

            pago.asignarReferenciaExterna(referencia);

            // 4️⃣ Guardar nuevamente actualizando la referencia externa
            pago = pagoRepositorio.guardar(pago);
        }

        return PagoMapper.toDTO(pago);
    }

    // ------------------------------
    // Confirmar pago (normal o premium)
    // ------------------------------
    @Transactional
    public void confirmarPago(UUID pagoId) {
        Pago pago = pagoRepositorio.buscarPorId(pagoId)
                .orElseThrow(() -> new IllegalArgumentException("Pago no encontrado"));

        pagoService.confirmar(pago);

        Pago guardado = pagoRepositorio.guardar(pago);
        eventPublisher.publish(guardado.getEventos());
        guardado.limpiarEventos();
    }

    // ------------------------------
    // Emitir comprobante para pago confirmado
    // ------------------------------
    @Transactional
    public ComprobanteFiscal emitirComprobante(EmitirComprobanteCommand command) {
        Pago pago = pagoRepositorio.buscarPorId(command.getPagoId())
                .orElseThrow(() -> new IllegalArgumentException("Pago no encontrado"));

        if (!pago.estaConfirmado())
            throw new IllegalStateException("Pago no confirmado");

        ComprobanteFiscal cf = new ComprobanteFiscal(pago, command.getTipo());
        pago.generarComprobante(cf);

        Pago guardado = pagoRepositorio.guardar(pago);
        eventPublisher.publish(guardado.getEventos());
        guardado.limpiarEventos();

        return cf;
    }

    // ------------------------------
    // Obtener pago
    // ------------------------------
    @Transactional(readOnly = true)
    public PagoDTO obtenerPago(UUID pagoId) {
        Pago pago = pagoRepositorio.buscarPorId(pagoId)
                .orElseThrow(() -> new IllegalArgumentException("Pago no encontrado"));
        return PagoMapper.toDTO(pago);
    }
}
