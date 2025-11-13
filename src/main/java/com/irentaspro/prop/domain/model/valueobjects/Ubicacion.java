package com.irentaspro.prop.domain.model.valueobjects;

public class Ubicacion {
    private final double latitud;
    private final double longitud;
    private final String distrito;

    public Ubicacion(double latitud, double longitud, String distrito) {
        //Añadido por el TDD
        if (distrito == null || distrito.isBlank()) {
            throw new IllegalArgumentException("El distrito no puede ser nulo o vacío.");
        }
        if (latitud < -90.0 || latitud > 90.0) {
            throw new IllegalArgumentException("La latitud debe estar entre -90 y 90.");
        }
        if (longitud < -180.0 || longitud > 180.0) {
            throw new IllegalArgumentException("La longitud debe estar entre -180 y 180.");
        }
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

    public String getDistrito() {
        return distrito;
    }

    @Override
    public String toString() {
        return String.format("%s (%.4f, %.4f)", distrito, latitud, longitud);
    }
}
