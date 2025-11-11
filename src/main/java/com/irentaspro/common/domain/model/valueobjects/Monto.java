package com.irentaspro.common.domain.model.valueobjects;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Value Object que representa un monto monetario dentro del dominio.
 * Incluye el valor y la moneda asociada (ej. "PEN", "USD").
 */
public class Monto {

        private final BigDecimal valor;
        private final String moneda;

        public Monto(BigDecimal valor, String moneda) {
                if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
                        throw new IllegalArgumentException("El monto debe ser positivo y no nulo");
                }
                if (moneda == null || moneda.isBlank()) {
                        throw new IllegalArgumentException("La moneda no puede ser nula ni vacía");
                }
                this.valor = valor;
                this.moneda = moneda.toUpperCase();
        }

        public BigDecimal valor() {
                return valor;
        }

        public String moneda() {
                return moneda;
        }

        @Override
        public boolean equals(Object obj) {
                if (this == obj)
                        return true;
                if (!(obj instanceof Monto))
                        return false;
                Monto other = (Monto) obj;
                return Objects.equals(valor, other.valor) && Objects.equals(moneda, other.moneda);
        }

        @Override
        public int hashCode() {
                return Objects.hash(valor, moneda);
        }

        @Override
        public String toString() {
                return valor.toPlainString() + " " + moneda;
        }
}
