package com.irentaspro.pay.domain.events;

import java.util.UUID;

// Evento cuando el pago fue confirmado por el sistema
public record PagoConfirmado(UUID pagoId, UUID usuarioId, String tipoPago) {
}
