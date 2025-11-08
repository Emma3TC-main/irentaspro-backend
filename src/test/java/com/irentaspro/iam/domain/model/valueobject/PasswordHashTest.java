package com.irentaspro.iam.domain.model.valueobject;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import com.irentaspro.iam.domain.model.valueobject.PasswordHash;

class PasswordHashTest {
    //Test forzando Exception
    @Test
    void PasswordValorNull(){
    //1. Arrange
    String valor = null;
    String algoritmo = "SHA-256";
    //2. Preparando el Test
    assertThrows(IllegalArgumentException.class, ()-> {
        //3. Act
        new PasswordHash(valor, algoritmo);
    });
    }    

    @Test
    void PasswordAlgoritmoNull(){
        String valor = "JuanPerezHash";
        String algoritmo = null;
        
        assertThrows(IllegalArgumentException.class, ()-> {
            new PasswordHash(valor, algoritmo);
        });

    }

    @Test
    void PasswordValorVacio(){
        String valor = "";
        String algoritmo = "SHA-256";
        
        assertThrows(IllegalArgumentException.class, ()-> {
            new PasswordHash(valor, algoritmo);
        });

    }

    //Test normales
    @Test
    void PasswordHashValido(){
        //1. Arrange
        String valor = "JuanPerezHash";
        String algoritmo = "SHA-256";
        //2. Act
        PasswordHash passwordHash = new PasswordHash(valor, algoritmo);
        //3. Assert
        assertEquals(valor, passwordHash.getValor());
        assertEquals(algoritmo, passwordHash.getAlgoritmo());
    }
    
}
