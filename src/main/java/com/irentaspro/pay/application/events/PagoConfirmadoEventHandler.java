package com.irentaspro.pay.application.events;

import java.time.LocalDateTime;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.irentaspro.pay.domain.gateway.ContratoGateway;
import com.irentaspro.pay.domain.gateway.PagoRealizadoDTO;
import com.irentaspro.pay.domain.model.Pago;
import com.irentaspro.pay.domain.repository.PagoRepositorio;
import com.irentaspro.pay.domain.events.PagoConfirmado;

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
        log.info("[PagoConfirmadoHandler] Evento recibido: {}", event.getPagoId());

        Pago pago = pagoRepositorio.buscarPorId(event.getPagoId())
                .orElseThrow(() -> new IllegalStateException("Pago no encontrado: " + event.getPagoId()));

        // Construimos DTO y notificamos al contexto Contratos
        PagoRealizadoDTO realizado = new PagoRealizadoDTO(
                pago.getId(),
                pago.getMonto().valor(),
                LocalDateTime.now(),
                pago.getReferenciaExterna());

        contratoGateway.registrarPagoEnContrato(pago.getContratoId(), realizado);

        log.info("[PagoConfirmadoHandler] Notificado al servicio Contratos para pago {}", pago.getId());
    }
}
