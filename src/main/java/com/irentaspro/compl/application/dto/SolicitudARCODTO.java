package com.irentaspro.compl.application.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.irentaspro.compl.domain.model.SolicitudARCO.EstadoSolicitud;

public record SolicitudARCODTO(
        UUID id,
        UUID usuarioId,
        String tipoSolicitud,
        LocalDate fecha,
        EstadoSolicitud estado,
        String respuesta) {
}
