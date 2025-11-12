package com.irentaspro.pay.application.command.handler;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.irentaspro.pay.application.command.EmitirComprobanteCommand;
import com.irentaspro.pay.domain.model.ComprobanteFiscal;
import com.irentaspro.pay.domain.model.Pago;
import com.irentaspro.pay.domain.repository.PagoRepositorio;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional
public class EmitirComprobanteCommandHandler {
    private final PagoRepositorio pagoRepositorio;

    public ComprobanteFiscal handle(EmitirComprobanteCommand command) {
        Pago pago = pagoRepositorio.buscarPorId(command.getPagoId())
                .orElseThrow(() -> new IllegalArgumentException("Pago no encontrado"));

        if (!pago.estaConfirmado()) {
            throw new IllegalStateException("No se puede emitir comprobante: pago no confirmado");
        }

        ComprobanteFiscal comprobante = new ComprobanteFiscal(pago, command.getTipo());
        // Lógica adicional para persistir o enviar comprobante...
        /**
         * Persistencia o integración con servicio externo (ej: SUNAT)
         */
        pago.generarComprobante(comprobante);
        pagoRepositorio.guardar(pago); // opcional según tu patrón

        return comprobante;
    }
}
