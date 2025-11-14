package com.irentaspro.compl.domain.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.irentaspro.compl.domain.model.AuditLog;
import com.irentaspro.compl.domain.model.HashEvidencia;

class AuditLogTest {

    private UUID idValido;
    private UUID usuarioIdValido;
    private String entidadValida;
    private String accionValida;
    private LocalDateTime fechaValida;
    private String ipValida;
    private HashEvidencia hashValido;

    @BeforeEach
    void setUp() {
        idValido = UUID.randomUUID();
        usuarioIdValido = UUID.randomUUID();
        entidadValida = "Pago";
        accionValida = "PAGO_CONFIRMADO";
        fechaValida = LocalDateTime.now();
        ipValida = "192.168.1.1";

        hashValido = new HashEvidencia("hash123", "SHA-256");
    }

@Test
    void lanzarExceptionSiUsuarioIdEsNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new AuditLog(idValido, null, entidadValida, accionValida, fechaValida, ipValida, hashValido);
        });
    }
    
    @Test
    void lanzarExceptionSiEntidadEsNulaOVacia() {
        assertThrows(IllegalArgumentException.class, () -> {
            new AuditLog(idValido, usuarioIdValido, null, accionValida, fechaValida, ipValida, hashValido);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new AuditLog(idValido, usuarioIdValido, " ", accionValida, fechaValida, ipValida, hashValido);
        });
    }

    @Test
    void lanzarExceptionSiAccionEsNulaOVacia() {
        assertThrows(IllegalArgumentException.class, () -> {
            new AuditLog(idValido, usuarioIdValido, entidadValida, null, fechaValida, ipValida, hashValido);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new AuditLog(idValido, usuarioIdValido, entidadValida, "", fechaValida, ipValida, hashValido);
        });
    }
    
    @Test
    void lanzarExceptionSiFechaEsNula() {
        assertThrows(IllegalArgumentException.class, () -> {
            new AuditLog(idValido, usuarioIdValido, entidadValida, accionValida, null, ipValida, hashValido);
        });
    }

    @Test
    void lanzarExceptionSiHashEsNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new AuditLog(idValido, usuarioIdValido, entidadValida, accionValida, fechaValida, ipValida, null);
        });
    }

    @Test
    void debeCrearAuditLogValidoConConstructor() {
     
        AuditLog auditLog = assertDoesNotThrow(() -> {
            return new AuditLog(idValido, usuarioIdValido, entidadValida, accionValida, fechaValida, ipValida, hashValido);
        });

        assertNotNull(auditLog);
        assertEquals(idValido, auditLog.getId());
        assertEquals(usuarioIdValido, auditLog.getUsuarioId());
        assertEquals(entidadValida, auditLog.getEntidad());
        assertEquals(accionValida, auditLog.getAccion());
        assertEquals(fechaValida, auditLog.getFecha());
        assertEquals(ipValida, auditLog.getIp());
        assertEquals(hashValido, auditLog.getHashEvidencia());
    }

    @Test
    void crearDebeAsignarDatosYFechaActual() {
        LocalDateTime tiempoAntes = LocalDateTime.now().minusSeconds(1);

        AuditLog auditLog = AuditLog.crear(usuarioIdValido, entidadValida, accionValida, ipValida, hashValido);

        assertNotNull(auditLog);
        assertNotNull(auditLog.getId()); 
        assertEquals(usuarioIdValido, auditLog.getUsuarioId());
        assertEquals(entidadValida, auditLog.getEntidad());
        assertEquals(accionValida, auditLog.getAccion());
        assertEquals(ipValida, auditLog.getIp());
        assertEquals(hashValido, auditLog.getHashEvidencia());
        
        assertNotNull(auditLog.getFecha());
        assertTrue(auditLog.getFecha().isAfter(tiempoAntes));
    }

}
