package com.irentaspro.common.domain.model.valueobjects;

public record HashDocumento(String valor, String algoritmo) {
        public HashDocumento {
                if (valor == null || valor.isBlank()) {
                        throw new IllegalArgumentException("El hash no puede ser nulo o vacío");
                }
                if (algoritmo == null || algoritmo.isBlank()) {
                        throw new IllegalArgumentException("El algoritmo no puede ser nulo o vacío");
                }
        }
}
