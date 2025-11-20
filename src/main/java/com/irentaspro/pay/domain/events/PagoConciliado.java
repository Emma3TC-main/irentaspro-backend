package com.irentaspro.pay.domain.events;

import java.util.UUID;

import com.irentaspro.common.domain.model.DomainEvent;

// Evento cuando el pago fue conciliado con la pasarela PSP
public class PagoConciliado extends DomainEvent {
    private final UUID pagoId;
    private final String referenciaExterna;

    public PagoConciliado(UUID pagoId, String referenciaExterna) {
        super("PagoConciliado");
        this.pagoId = pagoId;
        this.referenciaExterna = referenciaExterna;
    }

    public UUID getPagoId() {
        return pagoId;
    }

    public String getReferenciaExterna() {
        return referenciaExterna;
    }

}
