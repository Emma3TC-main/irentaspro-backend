package com.irentaspro.bi.domain.model;

import java.time.LocalDateTime;

import com.irentaspro.common.domain.model.Entidad;

public class Reporte extends Entidad {
    private String tipo;
    private String contenido;
    private LocalDateTime fechaGeneracion;

    public Reporte(String tipo, String contenido, LocalDateTime fechaGeneracion) {
        
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("El 'tipo' de reporte no puede ser nulo o vacío.");
        }
        if (contenido == null) {
            // Permitimos contenido vacío "", pero no nulo
            throw new IllegalArgumentException("El 'contenido' del reporte no puede ser nulo.");
        }
        if (fechaGeneracion == null) {
            throw new IllegalArgumentException("La 'fechaGeneracion' del reporte no puede ser nula.");
        }
        this.tipo = tipo;
        this.contenido = contenido;
        this.fechaGeneracion = fechaGeneracion;
    }

    public static Reporte crear(String tipo, String contenido) {
        return new Reporte(tipo, contenido, LocalDateTime.now());
    }

    // Getters
    public String getTipo() {
        return tipo;
    }

    public String getContenido() {
        return contenido;
    }

    public LocalDateTime getFechaGeneracion() {
        return fechaGeneracion;
    }

    // Métodos de dominio
    public void actualizarContenido(String nuevoContenido) {
        
        if (nuevoContenido == null) {
            throw new IllegalArgumentException("El 'nuevoContenido' del reporte no puede ser nulo.");
        }
        this.contenido = nuevoContenido;
        this.fechaGeneracion = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Reporte{" +
                "id=" + getId() +
                ", tipo='" + tipo + '\'' +
                ", fechaGeneracion=" + fechaGeneracion +
                '}';
    }
}
