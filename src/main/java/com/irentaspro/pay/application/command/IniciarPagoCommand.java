package com.irentaspro.pay.application.command;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IniciarPagoCommand {
    private final UUID contratoId;
    private final UUID usuarioId;
    private final BigDecimal monto;
    private final String moneda;
    private final String metodo; // ej. "PayPal", "Tarjeta"
    private final String tipoPago; // ej. "único", "recurrente"
}
