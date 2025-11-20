package com.irentaspro.ct.application.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ClausulaDTO {

    @NotBlank(message = "tipo de cláusula es obligatorio")
    @Size(max = 100, message = "tipo demasiado largo")
    private String tipo;

    @NotBlank(message = "descripción es obligatoria")
    @Size(max = 2000, message = "descripción demasiado larga")
    private String descripcion;
}
