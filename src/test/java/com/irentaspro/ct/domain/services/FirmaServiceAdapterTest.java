package com.irentaspro.ct.domain.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.irentaspro.common.domain.model.valueobjects.HashDocumento;
import com.irentaspro.common.domain.model.valueobjects.Monto;
import com.irentaspro.ct.domain.model.Contrato;
import com.irentaspro.ct.domain.model.FirmaDigital;
import com.irentaspro.ct.domain.model.valueobjects.PeriodoContrato;

class FirmaServiceAdapterTest {

    private FirmaServiceAdapter firmaAdapter;
    
    private Contrato contratoValido;

    @BeforeEach
    void setUp() {
        firmaAdapter = new FirmaServiceAdapter();
   
        contratoValido = new Contrato(
            UUID.randomUUID(),
            UUID.randomUUID(),
            new PeriodoContrato(LocalDate.now(), LocalDate.now().plusYears(1)),
            new Monto(1200.00, "USD"), 
            new ArrayList<>()
        );
    }

    @Test
    void lanzarExceptionSiContratoEsNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            firmaAdapter.solicitarFirma(null);
        });
    }

    @Test
    void lanzarExceptionSiHashEsNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            firmaAdapter.validarFirma(null);
        });
    }

    @Test
    void debeSolicitarFirma() {
        // 1. Arrange (contratoValido viene de setUp)
        
        // 2. Act
        // Usamos assertDoesNotThrow como pediste
        FirmaDigital firma = assertDoesNotThrow(() -> {
            // Este método llama al constructor de FirmaDigital, 
            // que también probamos (TDD en TDD)
            return firmaAdapter.solicitarFirma(contratoValido);
        });

        // 3. Assert
        // Verificamos que la simulación funciona
        assertNotNull(firma);
        assertEquals("ProveedorX", firma.getProveedor());
        assertEquals("abc123hash", firma.getHashDocumento().valor());
    }
    @Test
    void debeValidarFirmaCorrectamente() {
        HashDocumento hashValido = new HashDocumento("hashValido", "SHA-256");

        boolean resultadoValido = assertDoesNotThrow(() -> 
            firmaAdapter.validarFirma(hashValido)
        );

        assertTrue(resultadoValido);
    }
    
    @Test
    void debeDevolverFalseSiValorDeHashEsInvalido() {
        }
}
