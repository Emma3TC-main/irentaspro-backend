package com.irentaspro.prop.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonAppend.Prop;
import com.irentaspro.common.domain.model.AggregateRoot;
import com.irentaspro.prop.domain.model.valueobjects.Precio;
import com.irentaspro.prop.domain.model.valueobjects.Ubicacion;

public class Propiedad extends AggregateRoot {
    private UUID ownerId;
    private String titulo;
    private String descripcion;
    private String direccion;
    private Ubicacion ubicacion;
    private Precio precio;
    private List<DocumentoPropiedad> documentos = new ArrayList<>();

    // Constructor general, luego se usará inyección de dependencias y la capa de
    // applicación para crear instancias
    public Propiedad(UUID ownerId, String titulo, String descripcion, String direccion, Ubicacion ubicacion,
            Precio precio) {
        this.ownerId = ownerId;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.ubicacion = ubicacion;
        this.precio = precio;
        validarInvariantes();
    }

    // Métodos de dominio y lógica de negocio aquí
    public void publicar() {
        // Lógica para publicar la propiedad (ej: marcar como disponible)
    }

    public void actualizar(String nuevoTitulo, String nuevaDescripcion,
            Precio nuevoPrecio) {
        this.titulo = nuevoTitulo;
        this.descripcion = nuevaDescripcion;
        this.precio = nuevoPrecio;
        validarInvariantes();
    }

    // Mejorar la lógica de las validaciones
    @Override
    public void validarInvariantes() {
        if (ownerId == null || titulo == null || titulo.isBlank()) {
            throw new IllegalStateException("La propiedad debe tener un propietario y un título válido.");
        }
        if (precio == null) {
            throw new IllegalStateException("La propiedad debe tener un precio asignado.");
        }
    }

    // Getters

    public void agregarDocumento(DocumentoPropiedad documento) {
        if (documento == null) {
            return;
        }
        this.documentos.add(documento);
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public Precio getPrecio() {
        return precio;
    }

    public List<DocumentoPropiedad> getDocumentos() {
        return documentos;
    }

    // setters

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setPrecio(Precio precio) {
        this.precio = precio;
    }

    public void setDocumentos(List<DocumentoPropiedad> documentos) {
        this.documentos = documentos;
    }

}
