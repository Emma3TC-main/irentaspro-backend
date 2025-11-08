package com.irentaspro.prop.domain.model.valueobjects;

public class HashDocumento {
    private final String valor;
    private final String algoritmo;

    public HashDocumento(String valor, String algoritmo) {
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
        return String.format("Hash[%s]: %s", algoritmo, valor);
    }
}
