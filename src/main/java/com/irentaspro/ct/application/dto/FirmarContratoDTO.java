package com.irentaspro.ct.application.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class FirmarContratoDTO {

    @NotBlank(message = "algoritmo de hash es obligatorio")
    @Size(max = 50, message = "algoritmo demasiado largo")
    private String algoritmo;
}
