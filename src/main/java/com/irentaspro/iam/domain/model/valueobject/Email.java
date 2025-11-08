package com.irentaspro.iam.domain.model.valueobject;

public class Email {
    private final String valor;

    public Email(String valor) {
        // Validar formato de email (simplificado)
        if (valor == null || !valor.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Formato de email inválido");
        }
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return valor;
    }
}
