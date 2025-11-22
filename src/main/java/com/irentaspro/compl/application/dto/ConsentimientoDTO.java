package com.irentaspro.compl.application.dto;

import java.time.LocalDate;
import java.util.UUID;

public record ConsentimientoDTO(UUID id, UUID usuarioId, String texto, String version, LocalDate fechaAceptacion,
        boolean aceptado) {
}
