package com.irentaspro.ct.domain.model;

public class Clausula {
    private final String tipo;
    private final String descripcion;

    public Clausula(String tipo, String descripcion) {
        if (tipo == null || tipo.isBlank())
            throw new IllegalArgumentException("El tipo de cláusula no puede ser vacío.");
        if (descripcion == null || descripcion.isBlank())
            throw new IllegalArgumentException("La descripción de la cláusula no puede ser vacía.");
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
