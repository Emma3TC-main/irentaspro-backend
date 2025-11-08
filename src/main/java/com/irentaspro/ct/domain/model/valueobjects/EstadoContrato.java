package com.irentaspro.ct.domain.model.valueobjects;

public class EstadoContrato {
    private final String estado;

    public EstadoContrato(String estado) {
        this.estado = estado;
    }

    public String getEstado() { return estado; }

    public boolean es(String valor) {
        return this.estado.equalsIgnoreCase(valor);
    }
}
