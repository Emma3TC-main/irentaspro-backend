package com.irentaspro.prop.domain.model.valueobjects;

import java.util.Objects;

/**
 * Value Object que representa el hash de un documento digital,
 * asegurando su integridad mediante un algoritmo de cifrado (SHA-256, MD5,
 * etc.)
 * y su valor correspondiente.
 */
public final class HashDocumento {

    private final String valor; // Valor hexadecimal o base64 del hash
    private final String algoritmo; // Ejemplo: "SHA-256", "MD5"

    public HashDocumento(String valor, String algoritmo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El valor del hash no puede estar vacío.");
        }
        if (algoritmo == null || algoritmo.isBlank()) {
            throw new IllegalArgumentException("El algoritmo del hash no puede estar vacío.");
        }

        this.valor = valor.trim();
        this.algoritmo = algoritmo.trim().toUpperCase();
    }

    public String getValor() {
        return valor;
    }

    public String getAlgoritmo() {
        return algoritmo;
    }

    /**
     * Valida la estructura básica del hash según su algoritmo.
     * (Puedes ampliar esta lógica según tus requisitos).
     */
    public boolean validar() {
        if ("SHA-256".equals(algoritmo) && valor.length() != 64) {
            return false; // SHA-256 debe tener 64 caracteres hexadecimales
        }
        if ("MD5".equals(algoritmo) && valor.length() != 32) {
            return false; // MD5 debe tener 32 caracteres
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("Hash[%s]: %s", algoritmo, valor);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof HashDocumento))
            return false;
        HashDocumento that = (HashDocumento) o;
        return valor.equals(that.valor) && algoritmo.equalsIgnoreCase(that.algoritmo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor.toLowerCase(), algoritmo.toUpperCase());
    }
}
