package com.irentaspro.common.domain.model.valueojects;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.irentaspro.common.domain.model.valueobjects.Monto;

class MontoTest {

    @Test
    void lanzarExceptionSiValorEsNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Monto(null, "USD");
        });
    }

    @Test
    void lanzarExceptionSiValorEsNegativo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Monto(-50.0, "USD");
        });
    }

    @Test
    void lanzarExceptionSiMonedaEsNulaOVacia() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Monto(100.0, null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Monto(100.0, "  "); 
        });
    }
 
    @Test
    void debeCrearMontoValido() {
        
        Double valorValido = 150.75;
        String monedaValida = "USD";

        // 2. Act
        // Usamos assertDoesNotThrow como pediste
        Monto monto = assertDoesNotThrow(() -> {
            return new Monto(valorValido, monedaValida);
        });

        // 3. Assert
        assertNotNull(monto);
        assertEquals(valorValido, monto.valor()); // getter implícito de record
        assertEquals(monedaValida, monto.moneda()); // getter implícito de record
    }

    @Test
    void debePermitirMontoCero() {
        Double valorCero = 0.00;

        String monedaValida = "PEN";

        Monto monto = assertDoesNotThrow(() -> {
            return new Monto(valorCero, monedaValida);
        });
        
        assertNotNull(monto);
        assertEquals(valorCero, monto.valor());
    }
}
