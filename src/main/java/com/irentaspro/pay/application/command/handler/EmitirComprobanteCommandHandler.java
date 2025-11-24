package com.irentaspro.pay.application.command.handler;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.irentaspro.pay.application.command.EmitirComprobanteCommand;
import com.irentaspro.pay.application.service.PagoApplicationService;
import com.irentaspro.pay.domain.model.ComprobanteFiscal;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional
public class EmitirComprobanteCommandHandler {

    private final PagoApplicationService pagoApplicationService;

    public ComprobanteFiscal handle(EmitirComprobanteCommand command) {
        return pagoApplicationService.emitirComprobante(command);
    }
}
