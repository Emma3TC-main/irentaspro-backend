package com.irentaspro.compl.domain.model;

public class HashEvidencia {

    private final String valor;
    private final String algoritmo;

    public HashEvidencia(String valor, String algoritmo) {
        if (valor == null || valor.isBlank())
            throw new IllegalArgumentException("El valor del hash no puede estar vacío.");
        if (algoritmo == null || algoritmo.isBlank())
            throw new IllegalArgumentException("El algoritmo del hash no puede estar vacío.");

        this.valor = valor;
        this.algoritmo = algoritmo;
    }

    public String getValor() {
        return valor;
    }

    public String getAlgoritmo() {
        return algoritmo;
    }

    @Override
    public String toString() {
        return algoritmo + ":" + valor;
    }
}
