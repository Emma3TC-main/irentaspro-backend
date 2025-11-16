package com.irentaspro.iam.domain.model;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.time.LocalTime;
import java.time.chrono.*;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SesionTest {
    
    private Sesion sesionTest;
    private Date fechaExpiracion;
    private Date fechaActual;//solo para esta test, se crea está variable

    @BeforeEach
    public void setUp(){
        Instant ahora = Instant.now();
        Instant futuro = ahora.plus(1,ChronoUnit.HOURS);
        
        fechaActual = Date.from(ahora);
        fechaExpiracion = Date.from(futuro);
        
        sesionTest = new Sesion();
        sesionTest.setToken("1z3ge78da");
        sesionTest.setExpiracion(fechaExpiracion);
        sesionTest.setActiva(true);
    }

    

    @Test
    public void validarElCambioDeInvalidar(){
        //1. Arrange
        assertTrue(sesionTest.isActiva(),"En un 1er momento, la sesión es activa");
        //2. Act
        sesionTest.invalidar();
        //3. Act
        assertFalse(sesionTest.isActiva());
    }


    @Test
    public void invalidarFechaFuturaConActual(){
        //Validamos la fecha correcta
        assertEquals(sesionTest.getExpiracion(), fechaExpiracion);
        Instant pasado = Instant.now().minus(1, ChronoUnit.HOURS);
        sesionTest.setExpiracion(Date.from(pasado));
        
        boolean expirado = sesionTest.isExpirado();
        assertTrue(expirado);
    
    }

}
