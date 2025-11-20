package com.irentaspro.bi.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record KPIContratoResponse(
        UUID contratoId,
        BigDecimal montoTotal,
        BigDecimal montoPendiente,
        LocalDate inicio,
        LocalDate fin,
        String estado) {
}