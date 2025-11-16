package com.irentaspro.prop.domain.model;

import com.irentaspro.common.domain.model.Entidad;
import com.irentaspro.prop.domain.model.valueobjects.HashDocumento;

public class DocumentoPropiedad extends Entidad {
    private String tipo;
    private String url;
    private HashDocumento hash;

    public DocumentoPropiedad(String tipo, String url, HashDocumento hash) {
        //Aplicando TDD, para el Test DocumentoPropiedadTest
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("El 'tipo' de documento no puede ser nulo o vacío.");
        }
        if (url == null || url.isBlank()) { // .isBlank() es mejor que !isEmpty()
            throw new IllegalArgumentException("La 'url' del documento no puede ser nula o vacía.");
        }
        if (hash == null) {
            throw new IllegalArgumentException("El 'hash' del documento no puede ser nulo.");
        }

        this.tipo = tipo;
        this.url = url;
        this.hash = hash;
    }


    // Getters y Setters
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HashDocumento getHash() {
        return hash;
    }

    public void setHash(HashDocumento hash) {
        this.hash = hash;
    }

}
