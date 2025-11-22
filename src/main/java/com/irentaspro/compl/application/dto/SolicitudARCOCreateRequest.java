package com.irentaspro.compl.application.dto;

import java.util.UUID;

public record SolicitudARCOCreateRequest(UUID usuarioId, String tipo) {
}