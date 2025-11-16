package com.irentaspro.iam.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;


import com.irentaspro.iam.domain.model.Rol;

class RolTest {
    private Rol rolTest ;

    @BeforeEach
    void setUp(){
        rolTest = new Rol ();
        rolTest.setNombre("Rol de Prueba");
    }

    @Test
    public void AsignarNuevoRol(){
        //1. Arrange
        
        //2. Act
        rolTest.asignarPermiso("VER_CONTRATO");        

        //3. Assert
        assertEquals(1, rolTest.getPermisos().size());
        assertTrue(rolTest.getPermisos().contains("VER_CONTRATO"));

    }

    @Test
    public void evitarAsignarRol(){
        //1.Arrange
        rolTest.asignarPermiso("VER_CONTRATO");
        //2. Act
        rolTest.asignarPermiso("VER_CONTRATO");
        //3 Assert
        assertEquals(1, rolTest.getPermisos().size());
    }

    @Test
    public void noAsignarRolNull(){
        rolTest.asignarPermiso(null);
        assertEquals(0, rolTest.getPermisos().size());
    }

}
