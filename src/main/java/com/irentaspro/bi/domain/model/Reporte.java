package com.irentaspro.bi.domain.model;

import java.time.LocalDateTime;
import com.irentaspro.common.domain.model.Entidad;

/**
 * Entidad del dominio que representa un reporte BI.
 * Cada reporte contiene su tipo, contenido y fecha de generación.
 */
public class Reporte extends Entidad {

    private String tipo;
    private String contenido;
    private LocalDateTime fechaGeneracion;

    // Constructor principal
    public Reporte(String tipo, String contenido, LocalDateTime fechaGeneracion) {
        super();
        this.tipo = tipo;
        this.contenido = contenido;
        this.fechaGeneracion = fechaGeneracion;
        validarInvariantes();
    }

    // Método de fábrica para crear reportes
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

    // Comportamientos del dominio
    public void actualizarContenido(String nuevoContenido) {
        if (nuevoContenido == null || nuevoContenido.isBlank()) {
            throw new IllegalArgumentException("El contenido del reporte no puede estar vacío");
        }
        this.contenido = nuevoContenido;
        this.fechaGeneracion = LocalDateTime.now();
    }

    @Override
    public void validarInvariantes() {
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("El tipo del reporte no puede estar vacío");
        }
        if (contenido == null || contenido.isBlank()) {
            throw new IllegalArgumentException("El contenido del reporte no puede estar vacío");
        }
        if (fechaGeneracion == null) {
            throw new IllegalArgumentException("La fecha de generación del reporte no puede ser nula");
        }
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
