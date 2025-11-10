package com.irentaspro.iam.domain.model.valueobject;

/**
 * Value Object que representa un correo electrónico válido dentro del dominio.
 * Ejemplo válido: "usuario.ejemplo+1@test-domain.com"
 */
public class Email {

    private final String valor;

    public Email(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El correo electrónico no puede estar vacío");
        }

        String email = valor.trim().toLowerCase();

        if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException(
                    "Formato de correo electrónico inválido: " + valor +
                            ". Ejemplo válido: usuario.ejemplo@test.com");
        }

        this.valor = email;
    }

    public String getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return valor;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Email))
            return false;
        Email other = (Email) obj;
        return valor.equalsIgnoreCase(other.valor);
    }

    @Override
    public int hashCode() {
        return valor.toLowerCase().hashCode();
    }
}
