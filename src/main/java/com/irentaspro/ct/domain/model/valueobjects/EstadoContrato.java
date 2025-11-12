package com.irentaspro.ct.domain.model.valueobjects;

public class EstadoContrato {
    private final String estado;

    public EstadoContrato(String estado) {
        if (estado == null || estado.isBlank()) {
            throw new IllegalArgumentException("El estado no puede ser vacío.");
        }
        this.estado = estado.toUpperCase();
    }

    public String getEstado() {
        return estado;
    }

    public boolean es(String valor) {
        return this.estado.equalsIgnoreCase(valor);
    }

    @Override
    public String toString() {
        return estado;
    }
}
