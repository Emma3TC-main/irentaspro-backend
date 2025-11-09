package com.irentaspro.iam.domain.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import com.irentaspro.iam.domain.model.PasswordPolicy;
import com.irentaspro.iam.domain.model.Usuario;
import com.irentaspro.iam.domain.repository.IAuthRepositorio;
 

class AuthServiceTest {
    // Creación de mocks necesarios
    private IAuthRepositorio mockRepositorio;
    private PasswordPolicy mockPasswordPolicy;
    // Instancia del servicio a probar
    private AuthService authService;

    @BeforeEach
    void setUp(){
        // Inicialización de mocks 
        mockRepositorio = mock(IAuthRepositorio.class);
        mockPasswordPolicy = mock(PasswordPolicy.class);
        //inicialización del servicio con los mocks
        authService = new AuthService(mockRepositorio, mockPasswordPolicy);
    }

    @Test
    void devolverTokenGenerado(){
        // 1. Arrange
        Usuario usuario = new Usuario();
        // 2. Act
        String token = authService.issueToken(usuario);
        // 3. Assert
        assertEquals("TOKEN GENERADO", token);
    }

    @Test
    void validarTokenNull(){
        String token = null;
        boolean resultado = authService.validateToken(token);
        assertFalse(resultado);
    }

    @Test
    void validarTokenVacio(){
        String token = "";
        boolean resultado = authService.validateToken(token);
        assertFalse(resultado);
    
    }

    //Prueba momentante incompleta para validar token correcto
    @Test
    void validarTokenCorrecto(){
        String token = "TOKEN_GENERICO_VALIDO";
        boolean resultado = authService.validateToken(token);
        //assertFalse(resultado);
        //Cuando se implemente la lógica de validación
        assertTrue(resultado); 
    }
    
    @Test
    void ejecutarNoDebeFallar(){
        assertDoesNotThrow(() -> authService.ejecutar());
    }
}
