package com.irentaspro.pay.application.command.handler;

import org.springframework.stereotype.Component;

import com.irentaspro.pay.application.command.IniciarPagoPremiumCommand;
import com.irentaspro.pay.application.dto.PagoDTO;
import com.irentaspro.pay.application.service.PagoApplicationService;

@Component
public class IniciarPagoPremiumCommandHandler {

    private final PagoApplicationService pagoService;

    public IniciarPagoPremiumCommandHandler(PagoApplicationService pagoService) {
        this.pagoService = pagoService;
    }

    public PagoDTO handle(IniciarPagoPremiumCommand command) {
        return pagoService.iniciarPagoPremium(
                command.getUsuarioId(),
                command.getMonto(),
                command.getMoneda(),
                command.getMetodo() != null ? command.getMetodo() : "PayPal");
    }
}
