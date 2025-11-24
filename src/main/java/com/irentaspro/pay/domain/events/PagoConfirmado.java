package com.irentaspro.pay.domain.events;

import java.util.UUID;
import com.irentaspro.common.domain.model.DomainEvent;

public class PagoConfirmado extends DomainEvent {
    private final UUID pagoId;
    private final UUID usuarioId;
    private final String tipoPago;

    public PagoConfirmado(UUID pagoId, UUID usuarioId, String tipoPago) {
        super("PagoConfirmado");
        this.pagoId = pagoId;
        this.usuarioId = usuarioId;
        this.tipoPago = tipoPago;
    }

    public UUID getPagoId() {
        return pagoId;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public String getTipoPago() {
        return tipoPago;
    }
}
