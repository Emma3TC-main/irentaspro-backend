package com.irentaspro.ct.domain.model;

public class Clausula {
    private final String tipo;
    private final String descripcion;

    public Clausula(String tipo, String descripcion) {
        
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("El 'tipo' de la cláusula no puede ser nulo o vacío.");
        }
        if (descripcion == null || descripcion.isEmpty()) { 
            throw new IllegalArgumentException("La 'descripcion' de la cláusula no puede ser nula o vacía.");
        }
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
