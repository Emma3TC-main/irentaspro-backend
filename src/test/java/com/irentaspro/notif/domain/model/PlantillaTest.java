package com.irentaspro.notif.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class PlantillaTest {
  @Test
    void lanzarExceptionSiNombreEsNuloOVacio() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Plantilla(null, "<html>Hola</html>");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Plantilla("  ", "<html>Hola</html>"); // isBlank()
        });
    }

    @Test
    void lanzarExceptionSiContenidoEsNuloOVacio() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Plantilla("Bienvenida", null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Plantilla("Bienvenida", ""); // isEmpty()
        });
    }

    @Test
    void renderizarDebeReemplazarMarcadoresCorrectamente() {
        String html = "<h1>Hola {{0}}</h1><p>Tu pago de {{1}} fue recibido.</p>";
        Plantilla plantilla = new Plantilla("TestRender", html);
        
        String[] datos = {"Aaron", "$500.00"};
        String resultadoEsperado = "<h1>Hola Aaron</h1><p>Tu pago de $500.00 fue recibido.</p>";

        String resultadoReal = plantilla.renderizar(datos);

        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    void renderizarNoDebeReemplazarSiFaltanDatos() {
        String html = "<h1>Hola {{0}}</h1><p>Tu código es {{1}}.</p>";
        Plantilla plantilla = new Plantilla("TestFaltante", html);
        
        String[] datos = {"Iker"}; 
        
        String resultadoEsperado = "<h1>Hola Iker</h1><p>Tu código es {{1}}.</p>";

        String resultadoReal = plantilla.renderizar(datos);

        assertEquals(resultadoEsperado, resultadoReal);
    }
}