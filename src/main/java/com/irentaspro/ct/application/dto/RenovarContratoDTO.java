package com.irentaspro.ct.application.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RenovarContratoDTO {

    @NotNull(message = "nuevoInicio es obligatorio")
    private LocalDate nuevoInicio;

    @NotNull(message = "nuevoFin es obligatorio")
    private LocalDate nuevoFin;
}
