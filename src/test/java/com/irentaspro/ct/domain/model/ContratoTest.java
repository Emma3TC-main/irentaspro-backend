package com.irentaspro.ct.domain.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.irentaspro.common.domain.model.valueobjects.HashDocumento;
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
        
        firmaValida = new FirmaDigital(
            "ProveedorX", 
            "Cert123", 
            new HashDocumento("hash123", "SHA-256"), 
            LocalDateTime.now()
        );
    }
    

    @Test
    void lanzarExceptionSiPeriodoEsNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contrato(propiedadIdValido, inquilinoIdValido, null, montoValido, clausulasValidas);
        });
    }

    @Test
    void lanzarExceptionSiMontoEsNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contrato(propiedadIdValido, inquilinoIdValido, periodoValido, null, clausulasValidas);
        });
    }

    @Test
    void lanzarExceptionSiClausulasEsNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contrato(propiedadIdValido, inquilinoIdValido, periodoValido, montoValido, null);
        });
    }

    @Test
    void firmarLanzaExceptionSiFirmaEsNula() {
        Contrato contrato = new Contrato(propiedadIdValido, inquilinoIdValido, periodoValido, montoValido, clausulasValidas);

        assertThrows(IllegalArgumentException.class, () -> {
            contrato.firmar(null);
        });
    }
    
    @Test
    void renovarLanzaExceptionSiPeriodoEsNulo() {
        Contrato contrato = new Contrato(propiedadIdValido, inquilinoIdValido, periodoValido, montoValido, clausulasValidas);

        contrato.firmar(firmaValida);
        contrato.finalizar();
        assertTrue(contrato.getEstado().es("FINALIZADO"), "Pre-condición fallida");

        assertThrows(IllegalArgumentException.class, () -> {
            contrato.renovar(null);
        });
    }
@Test
    void debeCrearContratoValido() {
        Contrato contrato = assertDoesNotThrow(() -> {
            return new Contrato(propiedadIdValido, inquilinoIdValido, periodoValido, montoValido, clausulasValidas);
        });

        assertNotNull(contrato);
        assertNotNull(contrato.getId()); 
        assertEquals(propiedadIdValido, contrato.getPropiedadId());
        assertEquals(montoValido, contrato.getMonto());
        
        assertNotNull(contrato.getEstado());
        assertTrue(contrato.getEstado().es("BORRADOR"));
    }

    @Test
    void firmarDebeLanzarExceptionSiYaEstaFirmado() {
        Contrato contrato = new Contrato(propiedadIdValido, inquilinoIdValido, periodoValido, montoValido, clausulasValidas);
        contrato.firmar(firmaValida); 
        assertTrue(contrato.getEstado().es("FIRMADO")); 

        assertThrows(IllegalStateException.class, () -> {
            contrato.firmar(firmaValida);
        });
    }

    @Test
    void renovarDebeLanzarExceptionSiNoEstaFinalizado() {
        Contrato contrato = new Contrato(propiedadIdValido, inquilinoIdValido, periodoValido, montoValido, clausulasValidas);
        contrato.firmar(firmaValida); 
        
        assertThrows(IllegalStateException.class, () -> {
            contrato.renovar(periodoValido);
        });
    }
    
    @Test
    void finalizarDebeCambiarEstadoSiEstaFirmado() {
        
        Contrato contrato = new Contrato(propiedadIdValido, inquilinoIdValido, periodoValido, montoValido, clausulasValidas);
        contrato.firmar(firmaValida); 
        
        
        assertDoesNotThrow(() -> {
            contrato.finalizar();
        });

        assertTrue(contrato.getEstado().es("FINALIZADO"));
    }

    @Test
    void finalizarDebeLanzarExceptionSiEstaEnBorrador() {
        Contrato contrato = new Contrato(propiedadIdValido, inquilinoIdValido, periodoValido, montoValido, clausulasValidas);
        assertTrue(contrato.getEstado().es("BORRADOR")); 
        
        assertThrows(IllegalStateException.class, () -> {
            contrato.finalizar();
        });
    }
    
    
    @Test
    void lanzarExceptionSiPropiedadIdEsNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contrato(null, inquilinoIdValido, periodoValido, montoValido, clausulasValidas);
        });
    }
    
    @Test
    void lanzarExceptionSiInquilinoIdEsNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contrato(propiedadIdValido, null, periodoValido, montoValido, clausulasValidas);
        });
    }
}
