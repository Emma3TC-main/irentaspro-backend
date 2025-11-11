package com.irentaspro.common.domain.model;

import java.util.UUID;

/**
 * Clase base para todas las entidades del dominio.
 * Aporta identidad única y validación opcional.
 */
public abstract class Entidad {

    protected UUID id;

    protected Entidad() {
        this.id = UUID.randomUUID();
    }

    protected Entidad(UUID id) {
        this.id = (id != null) ? id : UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Cada entidad concreta puede implementar sus propias validaciones internas.
     */
    public abstract void validarInvariantes();
}
