package com.irentaspro.compl.domain.services;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

class ComplianceServiceTest {

    private ComplianceService complianceService;
    private UUID usuarioIdValido;

    @BeforeEach
    void setUp() {
        complianceService = new ComplianceService();
        usuarioIdValido = UUID.randomUUID();
    } 

    @Test
    void registrarLanzaExceptionSiUsuarioEsNulo() {
        String texto = "Acepto";
        String version = "v1";

        assertThrows(IllegalArgumentException.class, () -> {
         complianceService.registrarConsentimiento(null, texto, version);
    });
    }

    @Test
    void registrarLanzaExceptionSiTextoEsNuloOVacio() {
        assertThrows(IllegalArgumentException.class, () -> {
            complianceService.registrarConsentimiento(usuarioIdValido, null, "v1");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            complianceService.registrarConsentimiento(usuarioIdValido, " ", "v1"); 
        });
    }

    @Test
    void auditarLanzaExceptionSiUsuarioEsNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            complianceService.auditarOperacion(null, "Entidad", "Accion", "127.0.0.1");
        });
    }
    
    @Test
    void auditarLanzaExceptionSiEntidadEsNulaOVacia() {
        assertThrows(IllegalArgumentException.class, () -> {
            complianceService.auditarOperacion(usuarioIdValido, null, "Accion", "127.0.0.1");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            complianceService.auditarOperacion(usuarioIdValido, "", "Accion", "127.0.0.1");
        });
    }
    
    @Test
    void retenerLanzaExceptionSiUsuarioEsNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            complianceService.retenerDatos(null);
        });
    }
}
