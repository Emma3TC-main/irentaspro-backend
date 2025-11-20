package com.irentaspro.ct.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CrearContratoDTO {

    @NotNull(message = "propiedadId es obligatorio")
    private UUID propiedadId;

    @NotNull(message = "propietarioId es obligatorio")
    private UUID propietarioId;

    @NotNull(message = "inquilinoId es obligatorio")
    private UUID inquilinoId;

    @NotNull(message = "inicio es obligatorio")
    private LocalDate inicio;

    @NotNull(message = "fin es obligatorio")
    private LocalDate fin;

    @NotNull(message = "monto es obligatorio")
    @Positive(message = "monto debe ser mayor a cero")
    private BigDecimal monto;

    @NotBlank(message = "moneda es obligatoria")
    @Size(min = 2, max = 5, message = "moneda debe ser un código válido")
    private String moneda;

    @NotNull(message = "clausulas no puede ser nulo, envía lista vacía si no hay")
    private List<ClausulaDTO> clausulas;
}
