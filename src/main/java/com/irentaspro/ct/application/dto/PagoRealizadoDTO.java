package com.irentaspro.ct.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class PagoRealizadoDTO {
    private UUID pagoId;
    private BigDecimal monto;
    private LocalDateTime fecha;
    private String referenciaExterna;
    private UUID usuarioId;
}
