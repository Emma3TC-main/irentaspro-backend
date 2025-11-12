package com.irentaspro.ct.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class ContratoDTO {
    private UUID id;
    private UUID propiedadId;
    private UUID inquilinoId;
    private LocalDate inicio;
    private LocalDate fin;
    private BigDecimal monto;
    private String moneda;
    private String estado;
    private List<String> clausulas;
}
