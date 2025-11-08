package com.irentaspro.pay.domain.model;

import java.util.UUID;

import com.irentaspro.common.domain.model.AggregateRoot;
import com.irentaspro.common.domain.model.valueobjects.Monto;

public class Pago extends AggregateRoot {
    private UUID contratoId;
    private UUID usuarioId;
    private Monto monto;
    private String metodo;
    private String tipoPago; // contrato | membresia | otro
    private String estado;
    private String referenciaExterna;
    private ComprobanteFiscal comprobanteFiscal;

    public Pago(UUID contratoId, UUID usuarioId, Monto monto, String metodo, String tipoPago, String estado) {
        this.contratoId = contratoId;
        this.usuarioId = usuarioId;
        this.monto = monto;
        this.metodo = metodo;
        this.tipoPago = tipoPago;
        this.estado = estado;
    }

    public void registrar() {
        if (monto == null || monto.valor().doubleValue() <= 0)
            throw new IllegalArgumentException("El monto debe ser positivo.");
        this.estado = "registrado";
    }

    public void confirmar() {
        if (!"registrado".equals(this.estado))
            throw new IllegalStateException("El pago debe estar registrado antes de confirmarse.");
        this.estado = "confirmado";
    }

    public void conciliar() {
        if (!"confirmado".equals(this.estado))
            throw new IllegalStateException("El pago debe estar confirmado antes de conciliarse.");
        this.estado = "conciliado";
    }

    // Getters
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

    // Setters controlados
    public void asignarReferenciaExterna(String ref) {
        this.referenciaExterna = ref;
    }

    public void generarComprobante(ComprobanteFiscal cf) {
        this.comprobanteFiscal = cf;
    }

    @Override
    public void validarInvariantes() {
        if (contratoId == null || usuarioId == null)
            throw new IllegalArgumentException("Contrato y usuario no pueden ser nulos.");
    }
}
