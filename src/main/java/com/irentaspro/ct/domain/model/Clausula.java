package com.irentaspro.ct.domain.model;

public class Clausula {
    private final String tipo;
    private final String descripcion;

    public Clausula(String tipo, String descripcion) {
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
