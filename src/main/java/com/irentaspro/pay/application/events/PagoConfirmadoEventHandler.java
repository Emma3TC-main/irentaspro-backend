package com.irentaspro.pay.application.events;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.irentaspro.pay.domain.events.PagoConfirmado;
import com.irentaspro.pay.domain.gateway.ContratoDTO;
import com.irentaspro.pay.domain.gateway.ContratoGateway;
import com.irentaspro.pay.domain.gateway.PagoRealizadoDTO;
import com.irentaspro.pay.domain.model.Pago;
import com.irentaspro.pay.domain.repository.PagoRepositorio;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class PagoConfirmadoEventHandler {

    private final PagoRepositorio pagoRepositorio;
    private final ContratoGateway contratoGateway;

    @EventListener
    public void handle(PagoConfirmado event) {

        Pago pago = pagoRepositorio.buscarPorId(event.getPagoId())
                .orElseThrow(() -> new IllegalStateException("Pago no encontrado"));

        UUID usuarioId = contratoGateway
                .obtenerContrato(pago.getContratoId())
                .map(ContratoDTO::usuarioId)
                .orElseThrow(() -> new IllegalStateException("Contrato no encontrado"));

        PagoRealizadoDTO realizado = new PagoRealizadoDTO(
                pago.getId(),
                pago.getMonto().valor(),
                LocalDateTime.now(),
                pago.getReferenciaExterna(),
                usuarioId);

        contratoGateway.registrarPagoEnContrato(pago.getContratoId(), realizado);

        log.info("Pago confirmado enviado al microservicio Contratos: {}", pago.getId());
    }
}
