package com.irentaspro.prop.domain.model;

import com.irentaspro.common.domain.model.Entidad;
import com.irentaspro.prop.domain.model.valueobjects.HashDocumento;

public class DocumentoPropiedad extends Entidad {
    private String tipo;
    private String url;
    private HashDocumento hash;

    public DocumentoPropiedad(String tipo, String url, HashDocumento hash) {
        this.tipo = tipo;
        this.url = url;
        this.hash = hash;
    }

    public boolean validar() {
        // Lógica de validación del documento
        return tipo != null && !tipo.isEmpty() && url != null && !url.isEmpty() && hash != null;
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
