package com.irentaspro.pay.domain.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.irentaspro.common.domain.model.valueobjects.Monto;
import com.irentaspro.pay.domain.gateway.PasarelaPagoGateway;
import com.irentaspro.pay.domain.model.Pago;
import com.irentaspro.pay.domain.model.TransaccionPSP;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PagoService {

    private final PasarelaPagoGateway pasarelaPagoGateway;

    public Pago iniciarPago(UUID contratoId, UUID usuarioId, Monto monto, String metodo, String tipoPago) {
        // Llama a la pasarela (simulada o real)
        String ref = pasarelaPagoGateway.procesarPago(monto.valor(), monto.moneda(), metodo, tipoPago);

        // Crea el agregado de dominio
        Pago pago = new Pago(contratoId, usuarioId, monto, metodo, tipoPago, "registrado");
        pago.asignarReferenciaExterna(ref);

        return pago;
    }

    public void conciliar(Pago pago, List<TransaccionPSP> transacciones) {
        transacciones.stream()
                .filter(tx -> tx.getRef().equals(pago.getReferenciaExterna()))
                .findFirst()
                .ifPresentOrElse(tx -> {
                    pago.confirmar(); // Cambia estado y genera evento de dominio
                }, () -> {
                    throw new IllegalStateException("No se encontró transacción asociada al pago: " + pago.getId());
                });
    }

    public void confirmar(Pago pago) {
        pago.confirmar();
    }
}
