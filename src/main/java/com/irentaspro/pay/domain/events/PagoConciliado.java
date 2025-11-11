package com.irentaspro.pay.domain.events;

import java.util.UUID;

// Evento cuando el pago fue conciliado con la pasarela PSP
public record PagoConciliado(UUID pagoId, String referenciaExterna) {
}