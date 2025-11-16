package com.irentaspro.iam.domain.model;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.TestTemplate;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import com.irentaspro.iam.domain.model.Usuario;
import com.irentaspro.iam.domain.model.valueobject.Email;
import com.irentaspro.iam.domain.model.valueobject.PasswordHash;
    
class UsuarioTest {
    
    private Usuario usuario;
    private PasswordHash passwordHash;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
    }

    // Test para el método esPremium 
    @Test
    void devolverTrueSiEsPremium(){
        //1. Arrange
        usuario.setTipoCuenta("premium");
        //2. Act
        boolean usuarioTest = usuario.esPremium();
        //3. Assert
        assertTrue(usuarioTest);
    }

    @Test
    void devolverFalseSiNoEsPremium(){
        //1. Arrange
        usuario.setTipoCuenta("free");
        //2. Act
        boolean usuarioTest = usuario.esPremium();
        //3. Assert
        assertFalse(usuarioTest);
    }

    @Test
    void usuarioTipoCuentaNull(){
        usuario.setTipoCuenta(null);
        boolean usuarioTest = usuario.esPremium();
        assertFalse(usuarioTest);
    }
    //Finalizado test para el método esPremium

    @Test
    void validarCredenciales(){
        String credenciales = "Creedencial correcta";
        boolean usuarioTest = usuario.autenticar(credenciales);
        assertTrue(usuarioTest);
    }

    @Test
    void credencialesIncorrectas(){
        String credencialVacia = "";
        String credencialNula = null;
        for( int i = 0; i < 2; i++ ){
            boolean usuarioTest;
            if (i == 0) {
                usuarioTest = usuario.autenticar(credencialVacia);
            } else {
                usuarioTest = usuario.autenticar(credencialNula);
            }
        assertFalse(usuarioTest);
        assertFalse(usuarioTest);
        }
    }

    
    @Test
    void cambiarPassword(){
        PasswordHash nuevaPassword = new PasswordHash("nuevaPasswordHash", "Hash_123");
        
        usuario.cambiarPassword(nuevaPassword);
        

        assertNotNull(usuario.getPasswordHash());
        assertEquals(nuevaPassword, usuario.getPasswordHash());
    }

    
    @Test
    void cambiarPasswordNull(){
        PasswordHash nuevaPassword = null;
        assertThrows(IllegalArgumentException.class, () ->{
            usuario.cambiarPassword(nuevaPassword);
        });
    }

    //Test debe mejorar, ya que falta implementar UUUD para generar token
    @Test
    void generacionToken(){
    String token = usuario.generarToken();
    assertNotNull(token);
    }

    @Test
    void validarGeneracionIDalUsuario(){
        UUID idUsuario = usuario.getId();
        assertNotNull(idUsuario);
    }

    @Test
    void validarInvariantesUsuarioNull(){
        usuario.setNombre(null);
        usuario.setEmail(null);

        assertThrows(IllegalStateException.class, () ->{
            usuario.validarInvariantes();
        });
    }


}
