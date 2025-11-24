package com.irentaspro.pay.application.command.handler;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.irentaspro.pay.application.command.ConfirmarPagoCommand;
import com.irentaspro.pay.application.service.PagoApplicationService;


import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional
public class ConfirmarPagoCommandHandler {

    private final PagoApplicationService pagoApplicationService;

    public void handle(ConfirmarPagoCommand command) {
        pagoApplicationService.confirmarPago(command.getPagoId());
    }
}
