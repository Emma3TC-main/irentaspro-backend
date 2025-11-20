package com.irentaspro.ct.application.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegistrarPagoContratoDTO {

    @NotNull(message = "monto es obligatorio")
    @Positive(message = "monto debe ser mayor a cero")
    private BigDecimal monto;

    @Size(max = 255, message = "referenciaExterna demasiado larga")
    private String referenciaExterna;
}
