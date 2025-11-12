package com.irentaspro.iam.domain.model.valueobject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class EmailTest {
    //Test Forzando Excepción
    @Test
        void EmailInvalido() {
            // 1. Inicializar 
            String valorEmail = "correo-invalido.com"; // Sin @

            // 2. Accción de la excepción 
            assertThrows(IllegalArgumentException.class, () -> {
                // Verificar
                new Email(valorEmail);
            });
        }  

        
    @Test
    void EmailNulo(){
        
        String valorEmail = null;

        assertThrows(IllegalArgumentException.class, () -> {
            new Email(valorEmail);
        });
    }

    @Test
    void EmailVacio(){

        String valorEmail = "";

        assertThrows(IllegalArgumentException.class, () -> {
            new Email(valorEmail);
        });
    }

    //Test Normales
    @Test
    void EmailValido() {
        //Coloca un email valido 
        String valorEmail = "Irentaaaa98@gmail.com";
        //Crea el objeto con el email valido
        Email email = new Email(valorEmail);
        //Verifica que el email sea igual al valor proporcionado
        assertEquals(valorEmail, email.getValor());

    }  

}
