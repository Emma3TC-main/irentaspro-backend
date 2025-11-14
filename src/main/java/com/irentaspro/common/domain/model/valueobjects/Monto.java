package com.irentaspro.common.domain.model.valueobjects;

import java.math.BigDecimal;

public record Monto(Double valor,String moneda) {
        //TDD, para después
        public Monto { 
        if (valor == null) {
            throw new IllegalArgumentException("El 'valor' del monto no puede ser nulo.");
        }
        // Comparamos con BigDecimal.ZERO (es < 0 ?)
        if (valor < 0) { 
            throw new IllegalArgumentException("El 'valor' del monto no puede ser negativo.");
        }
        if (moneda == null || moneda.isBlank()) {
            throw new IllegalArgumentException("La 'moneda' no puede ser nula o vacía.");
        }
    }

}