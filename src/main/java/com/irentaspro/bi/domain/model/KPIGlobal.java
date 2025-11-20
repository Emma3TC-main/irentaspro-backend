package com.irentaspro.bi.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record KPIGlobal(
        long totalContratos,
        long contratosActivos,
        BigDecimal ingresosTotales,
        long pagosRealizados,
        BigDecimal deudaPendiente,
        LocalDate fechaActual) {
}