package com.irentaspro.pay.domain.services;

import java.util.List;

import com.irentaspro.pay.domain.model.Pago;
import com.irentaspro.pay.domain.model.TransaccionPSP;

public class ConciliacionService {
    public void conciliarPagos(List<Pago> pagos, List<TransaccionPSP> transacciones) {
        for (Pago p : pagos) {
            boolean match = transacciones.stream()
                    .anyMatch(t -> t.getRef().equals(p.getReferenciaExterna()));
            if (match) {
                p.conciliar(); // dispara un PagoConciliado
            }
        }
    }
}
