package com.irentaspro.common.domain.model.valueobjects;

public record EstadoContrato(String estado) {
    
public EstadoContrato {
        if (estado == null || estado.isBlank()) {
            throw new IllegalArgumentException("El 'estado' no puede ser nulo o vacío.");
        }
    }
    
}
