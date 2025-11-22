package com.irentaspro.compl.application.dto;

import java.util.UUID;

public record ConsentimientoCreateRequest(UUID usuarioId, String texto, String version) {
}