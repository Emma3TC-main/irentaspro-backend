package com.irentaspro.iam.domain.model;

public class PasswordPolicy {

    // Objeto de validación de reglas

    public boolean validarComplejidad(String password) {
        // Ejemplo simple: mínimo 8 caracteres, al menos una mayúscula y un número
        return password != null && password.matches("^(?=.*[A-Z])(?=.*\\d).{8,}$");
    }
}
