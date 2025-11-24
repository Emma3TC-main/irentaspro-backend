package com.irentaspro.pay.domain.services;

import java.util.List;

import com.irentaspro.pay.domain.model.Pago;
import com.irentaspro.pay.domain.model.TransaccionPSP;

public class ConciliacionService {

    /**
     * Conciliación por lote: marca CONCILIADO a los pagos que tengan referencia
     * coincidente en la lista de transacciones.
     */
    public void conciliarPagos(List<Pago> pagos, List<TransaccionPSP> transacciones) {
        for (Pago p : pagos) {
            transacciones.stream()
                    .filter(t -> t.getRef().equals(p.getReferenciaExterna()))
                    .findFirst()
                    .ifPresent(tx -> p.conciliar(tx.getRef()));
        }
    }
}
