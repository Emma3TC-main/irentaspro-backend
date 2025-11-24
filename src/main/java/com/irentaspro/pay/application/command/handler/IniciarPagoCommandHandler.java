package com.irentaspro.pay.application.command.handler;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.irentaspro.pay.application.command.IniciarPagoCommand;
import com.irentaspro.pay.application.dto.PagoDTO;
import com.irentaspro.pay.application.service.PagoApplicationService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional
public class IniciarPagoCommandHandler {

    private final PagoApplicationService pagoApplicationService;

    /**
     * Retorna la referencia de PayPal si corresponde.
     */
    public String handle(IniciarPagoCommand command) {
        PagoDTO dto = PagoDTO.builder()
                .contratoId(command.getContratoId())
                .usuarioId(command.getUsuarioId())
                .monto(command.getMonto())
                .moneda(command.getMoneda())
                .metodo(command.getMetodo())
                .tipoPago(command.getTipoPago())
                .build();

        // Registrar pago y obtener DTO
        PagoDTO registrado = pagoApplicationService.registrarPago(dto);

        // Retornar la referencia externa si es PayPal
        return registrado.getReferenciaExterna();
    }
}
