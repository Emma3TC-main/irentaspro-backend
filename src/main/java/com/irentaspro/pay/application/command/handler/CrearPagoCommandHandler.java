package com.irentaspro.pay.application.command.handler;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.irentaspro.pay.application.command.CrearPagoCommand;
import com.irentaspro.pay.application.dto.PagoDTO;
import com.irentaspro.pay.application.service.PagoApplicationService;

import lombok.RequiredArgsConstructor;

/**
 * Command Handler encargado de procesar el comando CrearPagoCommand.
 * Separa la capa de entrada (controller o bus de comandos) de la lógica de
 * aplicación.
 */
@Component
@RequiredArgsConstructor
@Transactional
public class CrearPagoCommandHandler {

    private final PagoApplicationService pagoApplicationService;

    public PagoDTO handle(CrearPagoCommand command) {
        // Mapea el comando al DTO
        PagoDTO dto = PagoDTO.builder()
                .contratoId(command.getContratoId())
                .usuarioId(command.getUsuarioId())
                .monto(command.getMonto())
                .moneda(command.getMoneda())
                .metodo(command.getMetodo())
                .tipoPago(command.getTipoPago())
                .build();

        // Llama al servicio de aplicación para registrar el pago
        return pagoApplicationService.registrarPago(dto);
    }
}
