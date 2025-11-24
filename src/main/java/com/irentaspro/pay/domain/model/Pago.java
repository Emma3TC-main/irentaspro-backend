package com.irentaspro.pay.domain.model;

import java.time.LocalDate;
import java.util.UUID;
import com.irentaspro.common.domain.model.AggregateRoot;
import com.irentaspro.common.domain.model.valueobjects.Monto;
import com.irentaspro.pay.domain.events.ComprobanteEmitido;
import com.irentaspro.pay.domain.events.PagoConciliado;
import com.irentaspro.pay.domain.events.PagoConfirmado;

/**
 * Aggregate root: Pago
 * - usa EstadoPago (enum)
 * - usuarioId puede ser nulo en PENDIENTE hasta recibir confirmación PSP
 * - el dominio no llama a PSP: las llamadas externas se hacen en Application
 * layer
 */
public class Pago extends AggregateRoot {

    private UUID contratoId;
    private UUID usuarioId; // puede ser null en PENDIENTE
    private Monto monto;
    private String metodo;
    private String tipoPago;
    private EstadoPago estado;
    private String referenciaExterna;
    private ComprobanteFiscal comprobanteFiscal;
    private LocalDate fechaVencimiento;

    // Constructor principal
    public Pago(UUID contratoId, Monto monto, String tipoPago) {
        this.id = UUID.randomUUID();
        this.contratoId = contratoId;
        this.monto = monto;
        this.tipoPago = tipoPago;
        this.metodo = null;
        this.estado = EstadoPago.PENDIENTE;
        validarInvariantes();
    }

    // Constructor completo para rehidratación
    public Pago(UUID id, UUID contratoId, UUID usuarioId, Monto monto, String metodo, String tipoPago,
            EstadoPago estado, String referenciaExterna) {
        this.id = id;
        this.contratoId = contratoId;
        this.usuarioId = usuarioId;
        this.monto = monto;
        this.metodo = metodo;
        this.tipoPago = tipoPago;
        this.estado = estado;
        this.referenciaExterna = referenciaExterna;
        validarInvariantes();
    }

    protected Pago() {
    }

    // --- Operaciones de dominio ---

    public void registrarLocal(String metodo) {
        if (this.estado != EstadoPago.PENDIENTE) {
            throw new IllegalStateException("Sólo se puede registrar un pago en estado PENDIENTE.");
        }
        this.metodo = metodo;
        this.estado = EstadoPago.REGISTRADO;
    }

    public void asignarReferenciaExterna(String ref) {
        if (ref == null || ref.isBlank()) {
            throw new IllegalArgumentException("Referencia externa inválida.");
        }
        this.referenciaExterna = ref;
    }

    public void confirmarPorPSP(UUID usuarioId) {
        if (this.estado != EstadoPago.REGISTRADO && this.estado != EstadoPago.PENDIENTE) {
            throw new IllegalStateException("El pago debe estar REGISTRADO o PENDIENTE para confirmarse.");
        }
        this.usuarioId = usuarioId != null ? usuarioId : this.usuarioId;
        this.estado = EstadoPago.CONFIRMADO;
        this.registrarEvento(new PagoConfirmado(this.getId(), this.usuarioId, this.tipoPago));
    }

    public void conciliar(String referenciaExternaConfirmada) {
        if (this.estado != EstadoPago.CONFIRMADO) {
            throw new IllegalStateException("Sólo pagos CONFIRMADOS pueden conciliarse.");
        }
        if (referenciaExternaConfirmada == null || referenciaExternaConfirmada.isBlank())
            throw new IllegalArgumentException("Referencia externa para conciliación inválida.");
        this.referenciaExterna = referenciaExternaConfirmada;
        this.estado = EstadoPago.CONCILIADO;
        this.registrarEvento(new PagoConciliado(this.getId(), this.referenciaExterna));
    }

    public void generarComprobante(ComprobanteFiscal cf) {
        if (this.estado != EstadoPago.CONCILIADO && this.estado != EstadoPago.CONFIRMADO) {
            throw new IllegalStateException("Debe estar CONCILIADO o CONFIRMADO para generar comprobante.");
        }
        this.comprobanteFiscal = cf;
        this.registrarEvento(new ComprobanteEmitido(this.getId(), cf.getTicketSUNAT()));
    }

    public boolean estaConfirmado() {
        return this.estado == EstadoPago.CONFIRMADO || this.estado == EstadoPago.CONCILIADO;
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

    public EstadoPago getEstado() {
        return estado;
    }

    public String getReferenciaExterna() {
        return referenciaExterna;
    }

    public ComprobanteFiscal getComprobanteFiscal() {
        return comprobanteFiscal;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    // Solo para rehidratación desde el repositorio
    public void setComprobanteFiscal(ComprobanteFiscal cf) {
        this.comprobanteFiscal = cf;
    }

    // Para generar las cuotas de pago

    public void setFechaVencimiento(LocalDate fecha) {
        this.fechaVencimiento = fecha;
    }

    @Override
    public void validarInvariantes() {
        if (!"MEMBRESIA_PREMIUM".equals(tipoPago) && contratoId == null)
            throw new IllegalArgumentException("El contrato no puede ser nulo.");

        if (monto == null)
            throw new IllegalArgumentException("El monto no puede ser nulo.");
        if (estado == null)
            throw new IllegalArgumentException("El estado no puede ser nulo.");
        if ((estado == EstadoPago.REGISTRADO || estado == EstadoPago.CONFIRMADO) && usuarioId == null) {
            // permitir null, se asigna al confirmar por PSP
        }
        if (estado == EstadoPago.REGISTRADO && (metodo == null || metodo.isBlank())) {
            throw new IllegalArgumentException("El método de pago es obligatorio cuando está REGISTRADO.");
        }
    }

}
