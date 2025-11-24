package com.irentaspro.pay.domain.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.irentaspro.common.domain.model.valueobjects.Monto;
import com.irentaspro.pay.domain.gateway.PasarelaPagoGateway;
import com.irentaspro.pay.domain.model.Pago;
import com.irentaspro.pay.domain.model.TransaccionPSP;
import com.irentaspro.pay.domain.model.EstadoPago;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PagoService {

    private final PasarelaPagoGateway pasarelaPagoGateway;

    /**
     * Flujo: registrarLocal → llamar PSP → asignarReferenciaExterna
     */
    public Pago iniciarPago(UUID contratoId, UUID usuarioId, Monto monto, String metodo, String tipoPago) {

        // Validación solo para pagos normales con contrato
        if (!"MEMBRESIA_PREMIUM".equals(tipoPago) && contratoId == null) {
            throw new IllegalArgumentException("El contrato no puede ser nulo");
        }

        // 1) Crear agregado en estado PENDIENTE
        Pago pago = new Pago(contratoId, monto, tipoPago);

        // 2) Registrar localmente (pasa a REGISTRADO)
        pago.registrarLocal(metodo);

        return pago;
    }

    /**
     * SOLO dominio: cuando la PSP confirma éxito.
     */
    public void confirmar(Pago pago) {
        pago.confirmarPorPSP(pago.getUsuarioId());
    }

    public void conciliar(Pago pago, List<TransaccionPSP> transacciones) {
        transacciones.stream()
                .filter(tx -> tx.getRef().equals(pago.getReferenciaExterna()))
                .findFirst()
                .ifPresent(tx -> pago.conciliar(tx.getRef()));
    }

    public Pago crearPagoPendiente(UUID contratoId, BigDecimal monto, LocalDate fechaVencimiento) {
        Pago pago = new Pago(contratoId, new Monto(monto, "PEN"), "PENDIENTE");
        pago.setFechaVencimiento(fechaVencimiento);
        return pago;
    }

}
