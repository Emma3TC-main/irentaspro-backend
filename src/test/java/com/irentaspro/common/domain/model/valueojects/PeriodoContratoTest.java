package com.irentaspro.common.domain.model.valueojects;

import org.junit.jupiter.api.Test;

import com.irentaspro.common.domain.model.valueobjects.PeriodoContrato;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date; 
import java.time.LocalDate;

class PeriodoContratoTest {

    private final Date fechaInicioValida = Date.valueOf(LocalDate.now());
    private final Date fechaFinValida = Date.valueOf(LocalDate.now().plusMonths(6)); 
    
    
    @Test
    void lanzarExceptionSiInicioEsNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PeriodoContrato(null, fechaFinValida);
        });
    }

    @Test
    void lanzarExceptionSiFinEsNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PeriodoContrato(fechaInicioValida, null);
        });
    }

    @Test
    void lanzarExceptionSiFinEsAntesDeInicio() {
        Date inicio = Date.valueOf(LocalDate.of(2025, 1, 30));
        Date fin = Date.valueOf(LocalDate.of(2025, 1, 15)); 

        assertThrows(IllegalArgumentException.class, () -> {
            new PeriodoContrato(inicio, fin);
        });
    }

    @Test
    void debeCrearPeriodoValido() {
         PeriodoContrato periodo = assertDoesNotThrow(() -> {
            return new PeriodoContrato(fechaInicioValida, fechaFinValida);
        });

        assertNotNull(periodo);
        assertEquals(fechaInicioValida, periodo.fechaInicio()); 
        assertEquals(fechaFinValida, periodo.fechaFin()); 
    
    }

    @Test
    void debePermitirFechasIguales() {
        Date mismoDia = Date.valueOf(LocalDate.now());
        assertDoesNotThrow(() -> {
            new PeriodoContrato(mismoDia, mismoDia);
        });
    }

}
