package com.irentaspro.pay.domain.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.irentaspro.pay.domain.model.Pago;
import com.irentaspro.pay.domain.model.TransaccionPSP;
import com.irentaspro.common.domain.model.valueobjects.Monto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

class PagoServiceTest {
    
    private PagoService pagoServiceTest;
    private UUID contratoIdTest;
    private UUID usuarioIdTest;
    private Pago pagoTest;

    @BeforeEach
    void setUp(){
        pagoServiceTest = new PagoService();
    }

    @Test
    void crearValidoIniciarPago (){
        contratoIdTest = contratoIdTest.randomUUID();
        usuarioIdTest = usuarioIdTest.randomUUID();
        pagoTest = pagoServiceTest.iniciarPago(contratoIdTest, usuarioIdTest, new Monto(1200.00,"USD"), "Contrato", "Paypal");

        assertNotNull(pagoTest, "El pago no está vacio");
        assertEquals("registrado",pagoTest.getEstado());
        assertEquals(contratoIdTest, pagoTest.getContratoId());
        assertEquals(usuarioIdTest, pagoTest.getUsuarioId());

    }

    @Test
    void conciliarDebeActualizarEstadoSiTransaccionCoincide() {
        Pago pago = new Pago(UUID.randomUUID(), UUID.randomUUID(), new Monto(100.0, "USD"), "tarjeta", "contrato", "pendiente");
        pago.registrar();
        pago.confirmar(); 
        
        pago.asignarReferenciaExterna("ref-123-abc");

        List<TransaccionPSP> transacciones = new ArrayList<>();
        TransaccionPSP transaccionCoincidente = new TransaccionPSP("Stripe", "ref-123-abc", new HashMap<>());
        transacciones.add(transaccionCoincidente);

        // No debe lanzar excepción
        assertDoesNotThrow(() -> {
            pagoServiceTest.conciliar(pago, transacciones);
        });

        assertEquals("conciliado", pago.getEstado());
    }

    @Test
    void lanzarExceptionAConciliarSiPSPNoCoincide() {
        Pago pago = new Pago(UUID.randomUUID(), UUID.randomUUID(), new Monto(100.0, "USD"), "tarjeta", "contrato", "pendiente");
        pago.registrar();
        pago.confirmar(); 
        pago.asignarReferenciaExterna("ref-123-abc");

        // Creamos una lista con una transacción que NO coincide
        List<TransaccionPSP> transacciones = new ArrayList<>();
        TransaccionPSP transaccionErronea = new TransaccionPSP("Stripe", "ref-OTRA-COSA-456", new HashMap<>());
        transacciones.add(transaccionErronea);

        
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            pagoServiceTest.conciliar(pago, transacciones);
        });
        
        assertEquals("No se encontró transacción PSP relacionada.", exception.getMessage());
        assertEquals("confirmado", pago.getEstado());
    }
    @Test
    void confirmarEstadoDePagoAConfirmado() {
        Pago pago = new Pago(UUID.randomUUID(), UUID.randomUUID(), new Monto(100.0, "USD"), "tarjeta", "contrato", "pendiente");
        pago.registrar();
        assertEquals("registrado", pago.getEstado());
        
        pagoServiceTest.confirmar(pago);

        assertEquals("confirmado", pago.getEstado());
    }
}

