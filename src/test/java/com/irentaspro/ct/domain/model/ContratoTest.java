package com.irentaspro.ct.domain.model;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.irentaspro.common.domain.model.valueobjects.Monto;
import com.irentaspro.ct.domain.model.valueobjects.PeriodoContrato;

class ContratoTest {
    
    private UUID propiedadIdValido;
    private UUID inquilinoIdValido;
    private PeriodoContrato periodoValido;
    private Monto montoValido;
    private List<Clausula> clausulasValidas;
    private FirmaDigital firmaValida;
    
    @BeforeEach
    void setUp() {
        propiedadIdValido = UUID.randomUUID();
        inquilinoIdValido = UUID.randomUUID();
        periodoValido = new PeriodoContrato(LocalDate.now(), LocalDate.now().plusYears(1));
        montoValido = new Monto(1200.00, "USD");
        clausulasValidas = new ArrayList<>();
        clausulasValidas.add(new Clausula("PENALIDAD", "Pagar 100 por día de retraso."));
        
        // firmaValida = new FirmaDigital(
        //     "ProveedorX", 
        //     "Cert123", 
        //     //new HashDocumento("hash123", "SHA-256"), // Usamos un HashDocumento REAL
        //     LocalDateTime.now()
        // );
    }


    @Test
    void lanzarExceptionSiPeriodoEsNulo() {
        // Test ROJO ❌: El constructor debe validar 'periodo'
        assertThrows(IllegalArgumentException.class, () -> {
            new Contrato(propiedadIdValido, inquilinoIdValido, null, montoValido, clausulasValidas);
        });
    }

    @Test
    void lanzarExceptionSiMontoEsNulo() {
        // Test ROJO ❌: El constructor debe validar 'monto'
        assertThrows(IllegalArgumentException.class, () -> {
            new Contrato(propiedadIdValido, inquilinoIdValido, periodoValido, null, clausulasValidas);
        });
    }

    @Test
    void lanzarExceptionSiClausulasEsNulo() {
        // Test ROJO ❌: El constructor debe validar 'clausulas' (no la lista, sino el objeto)
        assertThrows(IllegalArgumentException.class, () -> {
            new Contrato(propiedadIdValido, inquilinoIdValido, periodoValido, montoValido, null);
        });
    }

    // --- Grupo 2: Pruebas de Lógica de Métodos (TDD ROJO ❌) ---

    @Test
    void firmarLanzaExceptionSiFirmaEsNula() {
        // 1. Arrange
        // Creamos un contrato en estado BORRADOR (el estado inicial)
        Contrato contrato = new Contrato(propiedadIdValido, inquilinoIdValido, periodoValido, montoValido, clausulasValidas);
        
        // 2. Act & 3. Assert
        // Test ROJO ❌: El método 'firmar' no valida la firma nula
        assertThrows(IllegalArgumentException.class, () -> {
            contrato.firmar(null);
        });
    }
    
    @Test
    void renovarLanzaExceptionSiPeriodoEsNulo() {
        // 1. Arrange
        // Forzamos un contrato a estar en estado FINALIZADO
        Contrato contrato = new Contrato(propiedadIdValido, inquilinoIdValido, periodoValido, montoValido, clausulasValidas);
        // (Simulación de ciclo de vida para llegar a FINALIZADO)
        contrato.firmar(firmaValida);
        contrato.finalizar();
        assertTrue(contrato.getEstado().es("FINALIZADO"), "Pre-condición fallida");

        // 2. Act & 3. Assert
        // Test ROJO ❌: El método 'renovar' no valida el nuevo periodo
        assertThrows(IllegalArgumentException.class, () -> {
            contrato.renovar(null);
        });
    }
}
