package com.irentaspro.prop.domain.model.valueobjects;

import java.math.BigDecimal;

public class Precio {
    private final BigDecimal valor;
    private final String moneda;

    public Precio(BigDecimal valor, String moneda) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El valor del precio no puede ser negativo");
        }
        this.valor = valor;
        this.moneda = moneda;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getMoneda() {
        return moneda;
    }

    @Override
    public String toString() {
        return String.format("%s %s", moneda, valor.toString());
    }
}
