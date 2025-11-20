package com.irentaspro.ct.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CuotaContratoDTO {
    private int numero;
    private LocalDate fecha;
    private BigDecimal monto;
    private boolean pagado;
}
