package com.irentaspro.pay.application.command;

import java.util.UUID;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.irentaspro.ct.application.dto.events.CalendarioPagosGeneradoPayload;
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
    public void on(CalendarioPagosGeneradoPayload payload) {

        UUID contratoId = payload.getContratoId();

        payload.getCuotas().forEach(cuota -> {

            Pago pendiente = pagoService.crearPagoPendiente(
                    contratoId,
                    cuota.getMonto(),
                    cuota.getFecha());

            pagoRepositorio.guardar(pendiente);
        });
    }

}
