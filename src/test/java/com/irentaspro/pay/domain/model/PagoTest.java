package com.irentaspro.pay.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import com.irentaspro.common.domain.model.valueobjects.Monto;

class PagoTest {
    private UUID contratoIdValido;
    private UUID usuarioIdValido;
    private Monto montoTest;
    private Pago pagoTest;
    @BeforeEach
    void setUp(){
        contratoIdValido = UUID.randomUUID();
        usuarioIdValido = UUID.randomUUID();
        montoTest = new Monto(100.00, "USD");
        pagoTest = new Pago(contratoIdValido, usuarioIdValido, montoTest, "paypal", "contrato", "registrado");
    }

    @Test
    void lanzarExceptionMontoCero() {
        //1.Arrange
        Monto montoIncorrecto = new Monto(0.0, "USD");
        Pago pagoTest = new Pago(contratoIdValido, usuarioIdValido, montoIncorrecto, "Paypal", "contrato", "registrado");
        //2. Act
        assertThrows(IllegalArgumentException.class, ()->{
            pagoTest.registrar();
        });
    }

    @Test
    void verificarEstadoSeaRegistrado (){
        //1. Arrange, el Pago debe proceder solo si el Estado es registrado
        pagoTest = new Pago(contratoIdValido, usuarioIdValido, montoTest, "paypal", "contrato", "confirmado");
        pagoTest.registrar();
        pagoTest.confirmar();
        assertEquals(pagoTest.getEstado(), "confirmado");
    }
    
    @Test
    void lanzarExceptionMontoNull (){
        Monto montoIncorrecto = null;
        Pago pagoTest = new Pago(contratoIdValido, usuarioIdValido, montoIncorrecto, "Paypal", "contrato", "registrado");
        assertThrows(IllegalArgumentException.class, ()->{ 
            pagoTest.registrar();
        });
    }

    @Test
    void confimarSiEstadoConfirmado (){
        pagoTest.confirmar();
        assertEquals("confirmado",pagoTest.getEstado());
    }

    @Test
    void confirmarExcepcionSiEstadoNoEsRegistrado() {
        Pago pago = new Pago(contratoIdValido, usuarioIdValido, montoTest, "tarjeta", "contrato", "pendiente");
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            pago.confirmar(); 
        });
        assertEquals("El pago debe estar registrado antes de confirmarse.", exception.getMessage());
    }

    @Test
    void confirmarConciliadoSiEstadoEsConfirmado() {
        pagoTest.registrar();
        pagoTest.confirmar(); 

        pagoTest.conciliar();

        assertEquals("conciliado", pagoTest.getEstado());
    }

    @Test
    void lanzarExceptionSiConcilicadoNoEstadoConciliado (){
        pagoTest.registrar();
        assertThrows(IllegalStateException.class, () -> {
            pagoTest.conciliar();
        });
    }
    
    @Test
    void validarInvariantesLanzarExcepcion() {
        
        Pago pago = new Pago(null, null, montoTest, "tarjeta", "contrato", "pendiente");
        
        assertThrows(IllegalArgumentException.class, () -> {
            pago.validarInvariantes();
        }); 
    }

    @Test
    void generarComprobanteDebeAsignarElComprobanteAlPago() {
        
        Pago pago = new Pago(contratoIdValido, usuarioIdValido, montoTest, "tarjeta", "contrato", "confirmado");
        ComprobanteFiscal comprobante = new ComprobanteFiscal("Factura", "<xml>...</xml>", "TICKET-123");

        pago.generarComprobante(comprobante);

        // 3. Assert
        assertNotNull(pago.getComprobanteFiscal());
        assertEquals("TICKET-123", pago.getComprobanteFiscal().getTicketSUNAT());
    }
}

