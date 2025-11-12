package com.irentaspro.pay.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap; 
import java.util.Map;   

public class TransaccionPSPTest {
    
    private TransaccionPSP transaccionPSPTest;

    @Test
    void lanzarExceptionProviderNullOVacio(){
        Map <String, Object> payloadVacio = new HashMap<>(); 
        assertThrows(IllegalArgumentException.class, () -> {
            new TransaccionPSP(null,"454848949808", payloadVacio);
        });
                
        assertThrows(IllegalArgumentException.class, () -> {
            new TransaccionPSP("","454848949808", payloadVacio);
        });
    }   
       
    @Test
    void lanzarExceptionRefNullOVacio(){
        Map <String, Object> payloadVacio = new HashMap<>(); 
        assertThrows(IllegalArgumentException.class, () -> {
            new TransaccionPSP("Stripe",null, payloadVacio);
        });
                
        assertThrows(IllegalArgumentException.class, () -> {
            new TransaccionPSP("Stripe","", payloadVacio);
        });
    } 
    
    @Test
    void crearUnTransaccionPSPValida(){
        String providerValido = "Stripe";
        String refValida = "ref-abc-123";
        Map<String, Object> payloadValido = new HashMap<>();
        payloadValido.put("monto", 100.0);
        payloadValido.put("moneda", "USD");
        
        transaccionPSPTest = new TransaccionPSP(providerValido, refValida, payloadValido);
        assertNotNull(transaccionPSPTest);
        assertNotNull(transaccionPSPTest.getId()); // ¡Probando la herencia de Entidad!
        assertEquals(providerValido, transaccionPSPTest.getProvider());
        assertEquals(refValida, transaccionPSPTest.getRef());
        assertEquals(payloadValido, transaccionPSPTest.getPayload());
        assertEquals(100.0, transaccionPSPTest.getPayload().get("monto"));
    }
    

}
