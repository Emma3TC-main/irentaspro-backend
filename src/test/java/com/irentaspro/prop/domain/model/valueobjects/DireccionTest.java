package com.irentaspro.prop.domain.model.valueobjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DireccionTest {

    @Test
    void lanzarExcepcionSiCalleEsNulaOVacio() {
        String calleNula = null;
        String distritoValido = "Miraflores";
        String provinciaValida = "Lima";

        assertThrows(IllegalArgumentException.class, () -> {
            new Direccion(calleNula, distritoValido, provinciaValida);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Direccion("", distritoValido, provinciaValida);
        });
    }

    @Test
    void lanzarExceptionSiDistritoEsNulaOVacio(){
        String calle = "av.Arequpa";
        String distrito= null;
        String provincia = "Lima";

        assertThrows(IllegalArgumentException.class, ()-> {
            new Direccion(calle,distrito,provincia); 
        });
        
        assertThrows(IllegalArgumentException.class, ()-> {
            new Direccion(calle,"",provincia); 
        });
    }

    @Test
    void lanzarExceptionProvinciaNulaOVacio(){
        String calle = "av.Arequpa";
        String distrito= "Lima";
        String provincia = null;

        assertThrows(IllegalArgumentException.class,() ->{
            new Direccion(calle,distrito,provincia);
        });

        assertThrows(IllegalArgumentException.class, ()-> {
            new Direccion (calle, distrito,"");
        });
    }

    @Test
    void debeCrearDireccionYDevolverValoresCorrectos() {
        String calle = "Av. Principal 456";
        String distrito = "San Isidro";
        String provincia = "Lima";

        Direccion direccion = new Direccion(calle, distrito, provincia);

        assertNotNull(direccion);
        assertEquals(calle, direccion.getCalle());
        assertEquals(distrito, direccion.getDistrito());
        assertEquals(provincia, direccion.getProvincia());
    }
    @Test
    void StringDebeDevolverFormatoCorrecto() {
        String calle = "Av. Principal 456";
        String distrito = "San Isidro";
        String provincia = "Lima";
        Direccion direccion = new Direccion(calle, distrito, provincia);
        
        String formatoEsperado = "Av. Principal 456, San Isidro, Lima";

        assertEquals(formatoEsperado, direccion.toString());
    }
}
