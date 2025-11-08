package com.irentaspro.iam.domain.model;

import java.util.ArrayList;
import java.util.List;

import com.irentaspro.common.domain.model.Entidad;

public class Rol extends Entidad {

    private String nombre;
    private List<String> permisos = new ArrayList<>();

    // Lógica para asignar permisos , mejorar!

    public void asignarPermiso(String permiso) {
        if (!permisos.contains(permiso)) {
            permisos.add(permiso);
        }
    }

    // falta constructor

    // Getters y Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<String> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<String> permisos) {
        this.permisos = permisos;
    }

}
