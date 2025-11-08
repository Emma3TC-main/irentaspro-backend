package com.irentaspro.pay.domain.services;

import java.util.List;
import java.util.UUID;

import com.irentaspro.common.domain.model.valueobjects.Monto;
import com.irentaspro.pay.domain.model.Pago;
import com.irentaspro.pay.domain.model.TransaccionPSP;

public class PagoService {

    public Pago iniciarPago(UUID contratoId, UUID usuarioId, Monto monto, String metodo, String tipoPago) {
        Pago pago = new Pago(contratoId, usuarioId, monto, metodo, tipoPago, "pendiente");
        pago.registrar();
        return pago;
    }

    public void conciliar(Pago pago, List<TransaccionPSP> transacciones) {
        boolean encontrada = transacciones.stream()
                .anyMatch(t -> t.getRef().equals(pago.getReferenciaExterna()));

        if (!encontrada)
            throw new IllegalStateException("No se encontró transacción PSP relacionada.");

        pago.conciliar();
    }

    public void confirmar(Pago pago) {
        pago.confirmar();
    }
}
