package com.irentaspro.ct.domain.model.valueobjects;

public enum EstadoContrato {
    BORRADOR,
    FIRMADO,
    RENOVADO,
    FINALIZADO;

    public boolean es(EstadoContrato estado) {
        return this.equals(estado);
    }
}
