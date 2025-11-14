package com.irentaspro.ct.domain.model.valueobjects;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class PeriodoContratoTest {
 
    private final LocalDate fechaInicioValida = LocalDate.now();
    private final LocalDate fechaFinValida = LocalDate.now().plusMonths(6); 

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
        LocalDate inicio = LocalDate.of(2025, 1, 30);
        LocalDate fin = LocalDate.of(2025, 1, 15); // Fin es ANTES

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
        assertEquals(fechaInicioValida, periodo.getInicio());
        assertEquals(fechaFinValida, periodo.getFin());
    }
    
    @Test
    void debePermitirFechasIguales() {
        LocalDate mismoDia = LocalDate.now();

        PeriodoContrato periodo = assertDoesNotThrow(() -> {
            return new PeriodoContrato(mismoDia, mismoDia);
        });

        assertNotNull(periodo);
        assertEquals(mismoDia, periodo.getInicio());
        assertEquals(mismoDia, periodo.getFin());
    }    

}
