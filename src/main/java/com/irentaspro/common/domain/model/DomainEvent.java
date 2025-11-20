package com.irentaspro.common.domain.model;

import java.time.Instant;

public abstract class DomainEvent {
    private final String nombre;
    private final Instant fechaOcurrencia;

    protected DomainEvent(String nombre) {
        this.nombre = nombre;
        this.fechaOcurrencia = Instant.now();
    }

    public String getNombre() {
        return nombre;
    }

    public Instant getFechaOcurrencia() {
        return fechaOcurrencia;
    }

}
