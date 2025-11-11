package com.irentaspro.prop.domain.model;

import com.irentaspro.common.domain.model.Entidad;
import com.irentaspro.prop.domain.model.valueobjects.HashDocumento;

/**
 * Representa un documento asociado a una propiedad (ej. contrato, título,
 * certificado catastral).
 * Incluye información sobre su tipo, ubicación y hash de validación.
 */
public class DocumentoPropiedad extends Entidad {

    private String tipo; // Ejemplo: "Contrato", "Título", "Certificado"
    private String url; // Ruta o URL del documento digital
    private HashDocumento hash; // Hash de integridad del documento

    // Constructor principal
    public DocumentoPropiedad(String tipo, String url, HashDocumento hash) {
        super();
        this.tipo = tipo;
        this.url = url;
        this.hash = hash;
        validarInvariantes();
    }

    @Override
    public void validarInvariantes() {
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("El tipo de documento no puede estar vacío.");
        }
        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("La URL del documento no puede estar vacía.");
        }
        if (hash == null) {
            throw new IllegalArgumentException("El hash del documento no puede ser nulo.");
        }
    }

    /**
     * Valida el documento con base en sus atributos.
     */
    public boolean validar() {
        return tipo != null && !tipo.isBlank()
                && url != null && !url.isBlank()
                && hash != null
                && hash.validar();
    }

    // Getters y Setters
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
        validarInvariantes();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        validarInvariantes();
    }

    public HashDocumento getHash() {
        return hash;
    }

    public void setHash(HashDocumento hash) {
        this.hash = hash;
        validarInvariantes();
    }
}
