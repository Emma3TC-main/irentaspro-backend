package com.irentaspro.ct.domain.model.valueobjects;

public class EstadoContrato {
    private final String estado;

    public EstadoContrato(String estado) {
        if (estado == null || estado.isBlank()) {
            throw new IllegalArgumentException("El 'estado' no puede ser nulo o vacío.");
        }
        this.estado = estado;
    }

    public String getEstado() { return estado; }

    public boolean es(String valor) {
        return this.estado.equalsIgnoreCase(valor);
    }
}
