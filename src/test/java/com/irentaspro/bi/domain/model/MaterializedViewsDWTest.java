package com.irentaspro.bi.domain.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MaterializedViewsDWTest {

    private MaterializedViewsDW vistaDW;

    @BeforeEach
    void setUp() {
        vistaDW = new MaterializedViewsDW();
    }

    @Test
    void debeCrearInstanciaCorrectamente() {
        MaterializedViewsDW nuevaVista = assertDoesNotThrow(() -> {
            return new MaterializedViewsDW();
        });
        
        assertNotNull(nuevaVista);
    }
    @Test
    void debeEstarNoActualizadoAlCrear() {
        // 1. Arrange (vistaDW creada en setUp)
        
        // 2. Act
        // Llamamos al getter para ver el estado
        boolean estado = vistaDW.isActualizado();
        
        // 3. Assert
        assertFalse(estado, "La vista debe inicializarse como 'no actualizada'");
    }

    @Test
    void debeCambiarEstadoAlActualizar() {
        assertFalse(vistaDW.isActualizado(), "Pre-condición: Debe estar 'false'");

        vistaDW.actualizar();

        assertTrue(vistaDW.isActualizado(), "El estado debió cambiar a 'true'");
    }

    @Test
    void debePermanecerActualizadoSiSeActualizaDeNuevo() {
        vistaDW.actualizar();
        assertTrue(vistaDW.isActualizado(), "Pre-condición: Debe estar 'true'");

        vistaDW.actualizar();
        assertTrue(vistaDW.isActualizado(), "El estado debe seguir siendo 'true'");
    }
}
