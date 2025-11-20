package com.irentaspro.pay.domain.gateway;

import java.math.BigDecimal;
import java.util.UUID;

public record ContratoDTO(
        UUID contratoId,
        UUID usuarioId,
        BigDecimal montoPendiente) {
}
