package com.irentaspro.pay.application.command.handler;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.irentaspro.pay.application.command.ConciliarPagoCommand;
import com.irentaspro.pay.domain.repository.PagoRepositorio;
import com.irentaspro.pay.domain.services.PagoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ConciliarPagoCommandHandler {

    private final PagoRepositorio pagoRepositorio;
    private final PagoService pagoService;

    public void handle(ConciliarPagoCommand command) {
        if (command.getTransacciones() == null || command.getTransacciones().isEmpty()) {
            log.warn("No se recibieron transacciones para conciliar.");
            return;
        }

        pagoRepositorio.buscarTodos().forEach(pago -> {
            try {
                pagoService.conciliar(pago, command.getTransacciones());
                pagoRepositorio.guardar(pago);
                log.info("Pago conciliado correctamente: {}", pago.getId());
            } catch (Exception e) {
                log.error("Error conciliando pago {}: {}", pago.getId(), e.getMessage());
            }
        });
    }
}
