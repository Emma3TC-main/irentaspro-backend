package com.irentaspro.pay.domain.events;

import java.util.UUID;

import com.irentaspro.common.domain.model.DomainEvent;

// Evento cuando se generó un comprobante fiscal (boleta o factura)
public class ComprobanteEmitido extends DomainEvent {
    private final UUID pagoId;
    private final String ticketSUNAT;

    public ComprobanteEmitido(UUID pagoId, String ticketSUNAT) {
        super("ComprobanteEmitido");
        this.pagoId = pagoId;
        this.ticketSUNAT = ticketSUNAT;
    }

    public UUID getPagoId() {
        return pagoId;
    }

    public String getTicketSUNAT() {
        return ticketSUNAT;
    }

}
