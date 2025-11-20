package com.irentaspro.pay.domain.gateway;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PagoRealizadoDTO(
        UUID pagoId,
        BigDecimal monto,
        LocalDateTime fecha,
        String referenciaExterna,
        UUID usuarioId) {
}
