package com.irentaspro.ct.application.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class FinalizarContratoDTO {

    @Size(max = 300, message = "motivo demasiado largo")
    private String motivo;
}
