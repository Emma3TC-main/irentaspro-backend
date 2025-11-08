package com.irentaspro.prop.domain.model.valueobjects;

public class Ubicacion {
    private final double latitud;
    private final double longitud;
    private final String distrito;

    public Ubicacion(double latitud, double longitud, String distrito) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.distrito = distrito;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public String getDireccion() {
        return distrito;
    }

    @Override
    public String toString() {
        return String.format("%s (%.4f, %.4f)", distrito, latitud, longitud);
    }
}
