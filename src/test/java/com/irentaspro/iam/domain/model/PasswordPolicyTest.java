package com.irentaspro.iam.domain.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class PasswordPolicyTest {
    
    //Crea la instancia de PasswordPolicy
    private PasswordPolicy passwordPolicy;
    
    //BeforeEach se ejecuta antes de cada test, con esto se inicializa la instancia
    @BeforeEach
    void setUp() {
        passwordPolicy = new PasswordPolicy();
    }

    //Test para confirmar el estado esperado
    @Test
    void devolverFalsePasswordNull(){
        //1. Arrange
        String password = null;
        //2. Act
        boolean resultado = passwordPolicy.validarComplejidad(password);
        //3. Assert
        assertFalse(resultado);
    }
    
    @Test
    void devolverFalseRegexNoCumplido(){
        String password = "password";
        boolean resultado = passwordPolicy.validarComplejidad(password);
        assertFalse(resultado);
    }

   @Test
   void delvolverFalseMinCaracteresNoCumplido(){
        String password = "asss";
        Boolean resultado = passwordPolicy.validarComplejidad(password);    
        assertFalse(resultado);
   }

   @Test
    void devolverFalseNoMayuscula(){
          String password = "password1234";
          Boolean resultado = passwordPolicy.validarComplejidad(password);    
          assertFalse(resultado);
    }

    @Test
    void devolverFalseNoNumero(){
          String password = "Password";
          Boolean resultado = passwordPolicy.validarComplejidad(password);    
          assertFalse(resultado);
    }

    @Test
    void devolverTruePasswordCumple(){
          String password = "Password1234";
          Boolean resultado = passwordPolicy.validarComplejidad(password);    
          assertTrue(resultado);
    }

    
}
