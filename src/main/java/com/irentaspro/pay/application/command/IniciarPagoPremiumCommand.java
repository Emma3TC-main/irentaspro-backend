package com.irentaspro.pay.application.command;

import java.math.BigDecimal;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IniciarPagoPremiumCommand {

    @NotNull
    private UUID usuarioId;

    @NotNull
    @Positive
    private BigDecimal monto;

    @NotNull
    private String moneda;

    private String metodo = "PayPal"; // opcional, default PayPal
}
