package com.irentaspro.prop.domain.model.valueobjects;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


class HashDocumentoTest {

    @Test
    void lanzarExceptionValorONull(){
        String valor = null;
        String algoritmo = "xd";

        assertThrows(IllegalArgumentException.class, ()->{
            new HashDocumento(valor,algoritmo);
        });

        assertThrows(IllegalArgumentException.class, ()->{
            new HashDocumento("",algoritmo);
        });
    }

    @Test
    void lanzarExceptionAlgoritmoValorONull(){
        String valor = "Valor de pruba";
        String algortmo = null;

        assertThrows(IllegalArgumentException.class, ()->{
            new HashDocumento(valor, algortmo);
        });
        
        assertThrows(IllegalArgumentException.class,()->{
            new HashDocumento(valor,"");
        });
    }

    @Test
    void validarCreacionHash(){
        String valor = "ASE758";
        String algoritmo = "NIIMIO1";

        HashDocumento hashDocumentoTest = new HashDocumento(valor, algoritmo);

        assertNotNull(hashDocumentoTest);
        assertEquals(valor, hashDocumentoTest.getValor());
        assertEquals(algoritmo, hashDocumentoTest.getAlgoritmo());

    }
}
