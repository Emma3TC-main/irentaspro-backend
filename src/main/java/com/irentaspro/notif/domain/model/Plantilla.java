package com.irentaspro.notif.domain.model;

import com.irentaspro.common.domain.model.Entidad;

public class Plantilla extends Entidad {
    private final String nombre;
    private final String contenidoHtml;

    public Plantilla(String nombre, String contenidoHtml) {
        this.nombre = nombre;
        this.contenidoHtml = contenidoHtml;
        validarInvariantes();
    }

    @Override
    public void validarInvariantes() {
        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("El nombre de plantilla es requerido");
    }

    public String nombre() {
        return nombre;
    }

    public String contenidoHtml() {
        return contenidoHtml;
    }
}