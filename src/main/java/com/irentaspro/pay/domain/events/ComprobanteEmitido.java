package com.irentaspro.pay.domain.events;

import java.util.UUID;

// Evento cuando se generó un comprobante fiscal (boleta o factura)
public record ComprobanteEmitido(UUID pagoId, String ticketSUNAT) {
}
