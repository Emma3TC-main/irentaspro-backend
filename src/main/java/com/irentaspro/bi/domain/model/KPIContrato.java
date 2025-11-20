package com.irentaspro.bi.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record KPIContrato(
        UUID contratoId,
        BigDecimal montoTotal,
        BigDecimal montoPendiente,
        LocalDate inicio,
        LocalDate fin,
        String estado) {
}