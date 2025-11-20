package com.irentaspro.notif.application.dto;

import java.util.UUID;

public record NotificacionDTO(
        UUID id,
        String destinatario,
        String asunto,
        String mensaje,
        String tipo,
        String estado) {
}