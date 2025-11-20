package com.irentaspro.ct.domain.model.events;

import java.math.BigDecimal;
import java.util.UUID;

import com.irentaspro.common.domain.model.DomainEvent;

public class PagoRegistrado extends DomainEvent {
    private final UUID contratoId;
    private final BigDecimal monto;

    public PagoRegistrado(UUID contratoId, BigDecimal monto) {
        super("contrato.pago.registrado");
        this.contratoId = contratoId;
        this.monto = monto;
    }

    public UUID getContratoId() {
        return contratoId;
    }

    public BigDecimal getMonto() {
        return monto;
    }
}