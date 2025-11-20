package com.irentaspro.bi.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record KPIGlobalResponse(
        long totalContratos,
        long contratosActivos,
        BigDecimal ingresosTotales,
        long pagosRealizados,
        BigDecimal deudaPendiente,
        LocalDate fechaActual) {
}