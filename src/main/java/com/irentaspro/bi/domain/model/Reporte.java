package com.irentaspro.bi.domain.model;

import java.time.LocalDateTime;

import com.irentaspro.common.domain.model.Entidad;

public class Reporte extends Entidad {
    private String tipo;
    private String contenido;
    private LocalDateTime fechaGeneracion;

    public Reporte(String tipo, String contenido, LocalDateTime fechaGeneracion) {
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
