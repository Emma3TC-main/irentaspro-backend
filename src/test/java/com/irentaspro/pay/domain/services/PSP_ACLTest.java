package com.irentaspro.pay.domain.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.irentaspro.pay.domain.model.Pago;
import com.irentaspro.common.domain.model.valueobjects.Monto;
import java.util.Map;
import java.util.UUID;

class PSP_ACLTest {
   
   private PSP_ACL acl;
    private Pago pagoDePrueba;

    @BeforeEach
    void setUp() {
        acl = new PSP_ACL();
        
        UUID contratoId = UUID.randomUUID();
        UUID usuarioId = UUID.randomUUID();
        Monto monto = new Monto(150.50, "PEN");
        
        pagoDePrueba = new Pago(contratoId, usuarioId, monto, "yape", "membresia", "registrado");
    }

    @Test
    void traducirSolicitudDebeMapearCorrectamente() {
        
        Map<String, Object> solicitudTraducida = acl.traducirSolicitud(pagoDePrueba);
        
        assertNotNull(solicitudTraducida);
        
        
        assertEquals(150.50, solicitudTraducida.get("monto"));
        assertEquals("PEN", solicitudTraducida.get("moneda"));
        assertEquals(pagoDePrueba.getId().toString(), solicitudTraducida.get("referencia"));
        assertEquals("yape", solicitudTraducida.get("metodo"));
        
        assertEquals(4, solicitudTraducida.size());
    }

    @Test
    void mapearRespuestaNoDebeLanzarExcepcion() {

        Map<String, Object> respuestaPSP = Map.of("status", "success", "id_externo", "xyz789");

        assertDoesNotThrow(() -> {
            acl.mapearRespuesta(respuestaPSP);
        });
    }
}
