package com.irentaspro.ct.domain.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.irentaspro.common.domain.model.valueobjects.HashDocumento;

class FirmaDigitalTest {
    
private String proveedorValido;
    private String certificadoValido;
    private HashDocumento hashValido;
    private LocalDateTime selloTiempoValido;

    @BeforeEach
    void setUp() {
        proveedorValido = "ProveedorDeFirmaX";
        certificadoValido = "CERT-XYZ-12345";
        
        hashValido = new HashDocumento("a1b2c3d4e5", "SHA-256"); 
        selloTiempoValido = LocalDateTime.now();
    }
    @Test
    void lanzarExceptionSiProveedorEsNuloOVacio() {
        assertThrows(IllegalArgumentException.class, () -> {
            new FirmaDigital(null, certificadoValido, hashValido, selloTiempoValido);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new FirmaDigital("  ", certificadoValido, hashValido, selloTiempoValido); // .isBlank()
        });
    }

    @Test
    void lanzarExceptionSiCertificadoEsNuloOVacio() {
        assertThrows(IllegalArgumentException.class, () -> {
            new FirmaDigital(proveedorValido, null, hashValido, selloTiempoValido);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new FirmaDigital(proveedorValido, "", hashValido, selloTiempoValido); // isEmpty()
        });
    }
    
    @Test
    void lanzarExceptionSiHashEsNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new FirmaDigital(proveedorValido, certificadoValido, null, selloTiempoValido);
        });
    }

    @Test
    void lanzarExceptionSiSelloTiempoEsNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new FirmaDigital(proveedorValido, certificadoValido, hashValido, null);
        });
    }

    @Test
    void debeCrearFirmaDigitalValida() {
        FirmaDigital firma = assertDoesNotThrow(() -> {
            return new FirmaDigital(proveedorValido, certificadoValido, hashValido, selloTiempoValido);
        });

        assertNotNull(firma);
        assertEquals(proveedorValido, firma.getProveedor());
        assertEquals(certificadoValido, firma.getCertificado());
        assertEquals(hashValido, firma.getHashDocumento());
        assertEquals(selloTiempoValido, firma.getSelloTiempo());
    }
}
