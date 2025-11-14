package com.irentaspro.ct.domain.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.irentaspro.common.domain.model.valueobjects.Monto;
import com.irentaspro.ct.domain.model.Contrato;
import com.irentaspro.ct.domain.model.FirmaDigital;
import com.irentaspro.ct.domain.model.valueobjects.PeriodoContrato;
import com.irentaspro.common.domain.model.valueobjects.HashDocumento;

class ContratoServiceTest {
    
    @Mock
    private IFirmaAdapter mockFirmaAdapter; 

    
    private ContratoService contratoService;
    private Contrato contratoValido;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        contratoService = new ContratoService(mockFirmaAdapter);
        
        contratoValido = new Contrato(
            UUID.randomUUID(),
            UUID.randomUUID(),
            new PeriodoContrato(LocalDate.now(), LocalDate.now().plusYears(1)),
            new Monto(1200.00,"USD"), // (Asumiendo que Monto usa Double)
            new ArrayList<>()
        );
    }

    @Test
    void lanzarExceptionSiAdapterEsNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ContratoService(null); 
        });
    }

    @Test
    void firmarLanzaExceptionSiContratoEsNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            contratoService.firmar(null);
        });
    }
    @Test
    void validarEstadoLanzaExceptionSiContratoEsNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            contratoService.validarEstado(null);
        });
    }

    @Test
    void debeFirmarContrato() {
        FirmaDigital firmaDeRespuesta = new FirmaDigital(
            "MockProveedor", "MockCert", new HashDocumento("mockHash", "SHA-256"), LocalDateTime.now()
        );

        when(mockFirmaAdapter.solicitarFirma(contratoValido))
            .thenReturn(firmaDeRespuesta); 

            assertDoesNotThrow(() -> {
            contratoService.firmar(contratoValido);
        });

        assertTrue(contratoValido.getEstado().es("FIRMADO"), "El estado debió cambiar a FIRMADO");
        assertEquals(firmaDeRespuesta, contratoValido.getFirmaDigital(), "La firma no se asignó al contrato");
        verify(mockFirmaAdapter, times(1)).solicitarFirma(contratoValido);
        
        verifyNoMoreInteractions(mockFirmaAdapter);
    }
    @Test
    void debeValidarEstado() {

        boolean esValido = contratoService.validarEstado(contratoValido);

        assertTrue(esValido);
    }
    
    @Test
    void ejecutarNoDebeLanzarError() {
        assertDoesNotThrow(() -> {
            contratoService.ejecutar();
        });
    }


}
