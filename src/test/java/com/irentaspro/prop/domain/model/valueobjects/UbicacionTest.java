package com.irentaspro.prop.domain.model.valueobjects;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class UbicacionTest {
    @Test
    void lanzarExceptionSiDistritoEsNuloOVacio() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Ubicacion(0.0, 0.0, null);
        });
        
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Ubicacion(0.0, 0.0, "  "); 
        });
    }

    @Test
    void lanzarExceptionSiLatitudFueraDeRango() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Ubicacion(-91.0, 0.0, "Distrito Valido"); 
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Ubicacion(91.0, 0.0, "Distrito Valido"); 
        });
    }

    @Test
    void lanzarExcepcionSiLongitudFueraDeRango() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Ubicacion(0.0, -181.0, "Distrito Valido"); 
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Ubicacion(0.0, 181.0, "Distrito Valido"); // Muy alto
        });
    }

    @Test
    void crearUbicacionConDatosValidos() {
        double latValida = -12.04318;
        double lonValida = -77.02824;
        String distritoValido = "Lima";

        Ubicacion ubicacion = assertDoesNotThrow(() -> {
            return new Ubicacion(latValida, lonValida, distritoValido);
        });


        assertNotNull(ubicacion);
        assertEquals(latValida, ubicacion.getLatitud());
        assertEquals(lonValida, ubicacion.getLongitud());
        assertEquals(distritoValido, ubicacion.getDistrito());
    }

    @Test
    void toStringDebeDevolverFormatoCorrecto() {
        Ubicacion ubicacion = new Ubicacion(-12.0889, -77.0503, "Lince");
        String formatoEsperado = "Lince (-12.0889, -77.0503)";
        assertEquals(formatoEsperado, ubicacion.toString());
    }
}
