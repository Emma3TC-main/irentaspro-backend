package com.irentaspro.pay.domain.model;

import java.util.UUID;

import com.irentaspro.common.domain.model.AggregateRoot;
import com.irentaspro.common.domain.model.valueobjects.Monto;
import com.irentaspro.pay.domain.events.ComprobanteEmitido;
import com.irentaspro.pay.domain.events.PagoConciliado;
import com.irentaspro.pay.domain.events.PagoConfirmado;

/**
 * Entidad de dominio: Pago
 * Representa un pago asociado a un contrato o membresía.
 * Implementa la lógica de negocio y emite eventos de dominio.
 */
public class Pago extends AggregateRoot {

    private UUID contratoId;
    private UUID usuarioId;
    private Monto monto;
    private String metodo;
    private String tipoPago; // contrato | membresia | otro
    private String estado;
    private String referenciaExterna;
    private ComprobanteFiscal comprobanteFiscal;

    // --- Constructores de dominio ---
    public Pago(UUID contratoId, UUID usuarioId, Monto monto, String metodo, String tipoPago, String estado) {
        this.id = UUID.randomUUID();
        this.contratoId = contratoId;
        this.usuarioId = usuarioId;
        this.monto = monto;
        this.metodo = metodo;
        this.tipoPago = tipoPago;
        this.estado = estado;
        this.validarInvariantes();
    }

    /**
     * Constructor completo utilizado al rehidratar desde persistencia.
     */
    public Pago(UUID id, UUID contratoId, UUID usuarioId, Monto monto,
            String metodo, String tipoPago, String estado, String referenciaExterna) {
        this.id = id;
        this.contratoId = contratoId;
        this.usuarioId = usuarioId;
        this.monto = monto;
        this.metodo = metodo;
        this.tipoPago = tipoPago;
        this.estado = estado;
        this.referenciaExterna = referenciaExterna;
        this.validarInvariantes();
    }

    /**
     * Constructor protegido vacío para frameworks ORM.
     */
    protected Pago() {
    }

    // --- Lógica de negocio ---
    public void registrar() {
        if (monto == null || monto.valor().doubleValue() <= 0)
            throw new IllegalArgumentException("El monto debe ser positivo.");
        this.estado = "registrado";
    }

    public void confirmar() {
        if (!"registrado".equalsIgnoreCase(this.estado))
            throw new IllegalStateException("El pago debe estar registrado antes de confirmarse.");
        this.estado = "confirmado";
        this.registrarEvento(new PagoConfirmado(this.getId(), this.usuarioId, this.tipoPago));
    }

    public void generarComprobante(ComprobanteFiscal cf) {
        if (!"confirmado".equalsIgnoreCase(this.estado))
            throw new IllegalStateException("Debe confirmarse el pago antes de generar un comprobante.");
        this.comprobanteFiscal = cf;
        this.registrarEvento(new ComprobanteEmitido(this.getId(), cf.getTicketSUNAT()));
    }

    public void conciliar() {
        if (!"confirmado".equalsIgnoreCase(this.estado))
            throw new IllegalStateException("El pago debe estar confirmado antes de conciliarse.");
        this.estado = "conciliado";
        this.registrarEvento(new PagoConciliado(this.getId(), this.referenciaExterna));
    }

    // Método helper para verificar estado

    public boolean estaConfirmado() {
        return "confirmado".equalsIgnoreCase(this.estado);
    }

    // --- Getters ---
    public UUID getContratoId() {
        return contratoId;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public Monto getMonto() {
        return monto;
    }

    public String getMetodo() {
        return metodo;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public String getEstado() {
        return estado;
    }

    public String getReferenciaExterna() {
        return referenciaExterna;
    }

    public ComprobanteFiscal getComprobanteFiscal() {
        return comprobanteFiscal;
    }

    // --- Setters controlados ---
    public void asignarReferenciaExterna(String ref) {
        this.referenciaExterna = ref;
    }

    @Override
    public void validarInvariantes() {
        if (contratoId == null || usuarioId == null)
            throw new IllegalArgumentException("Contrato y usuario no pueden ser nulos.");
        if (monto == null)
            throw new IllegalArgumentException("El monto no puede ser nulo.");
    }
}
