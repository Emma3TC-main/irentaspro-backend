package com.irentaspro.ct.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;

@Data
public class ContratoResponseDTO {
    private UUID id;
    private String estado;
    private BigDecimal montoPendiente;
}
