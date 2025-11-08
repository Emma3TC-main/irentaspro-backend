package com.irentaspro.prop.domain.model.valueobjects;

public class Direccion {
    private final String calle;
    private final String distrito;
    private final String provincia;

    public Direccion(String calle, String distrito, String provincia) {
        this.calle = calle;
        this.distrito = distrito;
        this.provincia = provincia;
    }

    public String getCalle() {
        return calle;
    }

    public String getDistrito() {
        return distrito;
    }

    public String getProvincia() {
        return provincia;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s", calle, distrito, provincia);
    }
}
