package com.irentaspro.prop.domain.model.valueobjects;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class PrecioTest {
    
    @Test    
    void lanzarExceptionDeValorNegativo() {
        BigDecimal valorNegativo = new BigDecimal("-10.00");
        String monedaValida = "USD";
        assertThrows(IllegalArgumentException.class, () -> {
            new Precio(valorNegativo, monedaValida);
        });        
    }

    @Test
    void lanzarExceptionSiValorEsNull(){
        String monedaValida = "USD";
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->{
            new Precio(null, monedaValida);
        });
        assertEquals("El valor del precio no puede ser negativo", exception.getMessage());
    }

    @Test
    void lanzarExceptionSiMonedaEsNula() {

        BigDecimal valorValido = BigDecimal.TEN; 
        assertThrows(IllegalArgumentException.class, () -> {
            new Precio(valorValido, null);
        });
    }

    @Test
    void debePermitirValorCero() {
        BigDecimal valorCero = BigDecimal.ZERO;
        String monedaValida = "PEN";
        
        assertDoesNotThrow(() -> {
            new Precio(valorCero, monedaValida);
        });
    }

    @Test
    void debeCrearPreciValido() {

        BigDecimal valorValido = new BigDecimal("1500.50");
        String monedaValida = "USD";

        Precio precio = new Precio(valorValido, monedaValida);

        assertNotNull(precio);
        assertEquals(valorValido, precio.getValor());
        assertEquals(monedaValida, precio.getMoneda());
    }
    
    @Test
    void toStringDebeDevolverFormatoCorrecto() {

        Precio precio = new Precio(new BigDecimal("120.50"), "USD");
        String formatoEsperado = "USD 120.50";

        assertEquals(formatoEsperado, precio.toString());
    }
}

