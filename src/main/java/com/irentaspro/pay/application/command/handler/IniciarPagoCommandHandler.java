package com.irentaspro.pay.application.command.handler;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.irentaspro.common.domain.model.valueobjects.Monto;
import com.irentaspro.pay.application.command.IniciarPagoCommand;
import com.irentaspro.pay.domain.model.Pago;
import com.irentaspro.pay.domain.repository.PagoRepositorio;
import com.irentaspro.pay.domain.services.PagoService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional
public class IniciarPagoCommandHandler {

    private final PagoRepositorio pagoRepositorio;
    private final PagoService pagoService;

    public String handle(IniciarPagoCommand command) {
        // Crear valor objeto de monto
        Monto monto = new Monto(command.getMonto(), command.getMoneda());

        // Lógica principal
        Pago pago = pagoService.iniciarPago(
                command.getContratoId(),
                command.getUsuarioId(),
                monto,
                command.getMetodo(),
                command.getTipoPago());

        // Guardar en base de datos
        pagoRepositorio.guardar(pago);

        // Retornar la referencia externa (id Paypal)
        return pago.getReferenciaExterna();
    }
}
