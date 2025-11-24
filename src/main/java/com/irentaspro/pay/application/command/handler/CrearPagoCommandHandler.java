package com.irentaspro.pay.application.command.handler;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.irentaspro.pay.application.command.CrearPagoCommand;
import com.irentaspro.pay.application.dto.PagoDTO;
import com.irentaspro.pay.application.service.PagoApplicationService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional
public class CrearPagoCommandHandler {

    private final PagoApplicationService pagoApplicationService;

    public PagoDTO handle(CrearPagoCommand command) {
        PagoDTO dto = PagoDTO.builder()
                .contratoId(command.getContratoId())
                .usuarioId(command.getUsuarioId())
                .monto(command.getMonto())
                .moneda(command.getMoneda())
                .metodo(command.getMetodo())
                .tipoPago(command.getTipoPago())
                .build();

        return pagoApplicationService.registrarPago(dto);
    }
}
