package com.irentaspro.iam.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.irentaspro.common.domain.model.Entidad;

/**
 * Entidad de dominio que representa un rol dentro del sistema IAM (Identity &
 * Access Management).
 * Un rol define un conjunto de permisos que determinan qué acciones puede
 * realizar un usuario.
 */
public class Rol extends Entidad {

    private String nombre;
    private final List<String> permisos = new ArrayList<>();

    // ----------------------------
    // Constructores
    // ----------------------------

    public Rol(String nombre) {
        super();
        this.nombre = nombre;
        validarInvariantes();
    }

    public Rol(UUID id, String nombre, List<String> permisosIniciales) {
        super(id);
        this.nombre = nombre;
        if (permisosIniciales != null) {
            this.permisos.addAll(permisosIniciales);
        }
        validarInvariantes();
    }

    // ----------------------------
    // Comportamientos del dominio
    // ----------------------------

    /**
     * Asigna un permiso al rol si aún no lo tiene.
     */
    public void asignarPermiso(String permiso) {
        if (permiso == null || permiso.isBlank()) {
            throw new IllegalArgumentException("El permiso no puede ser nulo ni vacío.");
        }
        if (!permisos.contains(permiso)) {
            permisos.add(permiso);
        }
    }

    /**
     * Revoca (elimina) un permiso del rol.
     */
    public void revocarPermiso(String permiso) {
        permisos.remove(permiso);
    }

    /**
     * Verifica si el rol contiene un permiso específico.
     */
    public boolean tienePermiso(String permiso) {
        return permisos.contains(permiso);
    }

    @Override
    public void validarInvariantes() {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del rol no puede estar vacío.");
        }
    }

    // ----------------------------
    // Getters
    // ----------------------------

    public String getNombre() {
        return nombre;
    }

    public List<String> getPermisos() {
        // Devolver lista inmutable para evitar modificaciones externas
        return Collections.unmodifiableList(permisos);
    }

    // ----------------------------
    // Setters controlados
    // ----------------------------

    public void setNombre(String nombre) {
        this.nombre = nombre;
        validarInvariantes();
    }

    /**
     * Reemplaza completamente la lista de permisos (solo en casos administrativos).
     */
    public void setPermisos(List<String> nuevosPermisos) {
        this.permisos.clear();
        if (nuevosPermisos != null) {
            nuevosPermisos.stream()
                    .filter(p -> p != null && !p.isBlank())
                    .distinct()
                    .forEach(permisos::add);
        }
    }
}
