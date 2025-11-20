package com.irentaspro.pay.application.command;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.irentaspro.ct.domain.model.events.CalendarioPagosGenerado;
import com.irentaspro.pay.domain.model.Pago;
import com.irentaspro.pay.domain.repository.PagoRepositorio;
import com.irentaspro.pay.domain.services.PagoService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ContratoEventHandler {

    private final PagoService pagoService;
    private final PagoRepositorio pagoRepositorio;

    @EventListener
    public void on(CalendarioPagosGenerado event) {
        event.getCuotas().forEach(cuota -> {

            Pago pendiente = pagoService.crearPagoPendiente(
                    event.getContratoId(),
                    cuota.getMonto(),
                    cuota.getFecha());

            pagoRepositorio.guardar(pendiente);
        });
    }
}
