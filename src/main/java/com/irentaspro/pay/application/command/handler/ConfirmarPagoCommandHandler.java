package com.irentaspro.pay.application.command.handler;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.irentaspro.pay.application.command.ConfirmarPagoCommand;
import com.irentaspro.pay.domain.model.Pago;
import com.irentaspro.pay.domain.repository.PagoRepositorio;
import com.irentaspro.pay.domain.services.PagoService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional
public class ConfirmarPagoCommandHandler {
    private final PagoRepositorio pagoRepositorio;
    private final PagoService pagoService;

    public void handle(ConfirmarPagoCommand command) {
        Pago pago = pagoRepositorio.buscarPorId(command.getPagoId())
                .orElseThrow(() -> new IllegalArgumentException("Pago no encontrado"));

        pagoService.confirmar(pago);
        pagoRepositorio.guardar(pago);
    }
}
