package com.irentaspro.iam.domain.model.valueobject;

public class PasswordHash {
    private final String valor;
    private final String algoritmo;

    public PasswordHash(String valor, String algoritmo) {
        this.valor = valor;
        this.algoritmo = algoritmo;
    }

    public String getValor() {
        return valor;
    }

    public String getAlgoritmo() {
        return algoritmo;
    }
}
