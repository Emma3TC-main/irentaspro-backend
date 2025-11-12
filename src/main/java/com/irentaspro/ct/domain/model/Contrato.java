package com.irentaspro.ct.domain.model;

import java.util.List;
import java.util.UUID;
import com.irentaspro.common.domain.model.AggregateRoot;
import com.irentaspro.common.domain.model.valueobjects.Monto;
import com.irentaspro.ct.domain.model.valueobjects.EstadoContrato;
import com.irentaspro.ct.domain.model.valueobjects.PeriodoContrato;

public class Contrato extends AggregateRoot {
    private final UUID propiedadId;
    private final UUID inquilinoId;
    private PeriodoContrato periodo;
    private final Monto monto;
    private EstadoContrato estado;
    private final List<Clausula> clausulas;
    private FirmaDigital firmaDigital;

    public Contrato(UUID propiedadId, UUID inquilinoId, PeriodoContrato periodo, Monto monto,
            List<Clausula> clausulas) {
        this.propiedadId = propiedadId;
        this.inquilinoId = inquilinoId;
        this.periodo = periodo;
        this.monto = monto;
        this.clausulas = clausulas;
        this.estado = new EstadoContrato("BORRADOR");
        validarInvariantes();
    }

    // --- Métodos de negocio ---
    public void generarCalendarioPagos() {
        // TODO: Implementar en capa de aplicación o infraestructura
    }

    public void firmar(FirmaDigital firma) {
        if (estado.es("BORRADOR")) {
            this.firmaDigital = firma;
            this.estado = new EstadoContrato("FIRMADO");
        } else {
            throw new IllegalStateException("El contrato no puede ser firmado en su estado actual.");
        }
    }

    public void renovar(PeriodoContrato nuevoPeriodo) {
        if (estado.es("FINALIZADO")) {
            this.periodo = nuevoPeriodo;
            this.estado = new EstadoContrato("RENOVADO");
        } else {
            throw new IllegalStateException("Solo se pueden renovar contratos finalizados.");
        }
    }

    public void finalizar() {
        if (estado.es("FIRMADO") || estado.es("RENOVADO")) {
            this.estado = new EstadoContrato("FINALIZADO");
        } else {
            throw new IllegalStateException("Solo se pueden finalizar contratos firmados o renovados.");
        }
    }

    @Override
    public void validarInvariantes() {
        if (propiedadId == null || inquilinoId == null) {
            throw new IllegalArgumentException("El contrato debe tener propiedad e inquilino definidos.");
        }
        if (periodo == null || monto == null || clausulas == null || clausulas.isEmpty()) {
            throw new IllegalArgumentException("El contrato debe tener periodo, monto y cláusulas válidas.");
        }
    }

    // --- Getters ---
    public UUID getPropiedadId() {
        return propiedadId;
    }

    public UUID getInquilinoId() {
        return inquilinoId;
    }

    public PeriodoContrato getPeriodo() {
        return periodo;
    }

    public Monto getMonto() {
        return monto;
    }

    public EstadoContrato getEstado() {
        return estado;
    }

    public List<Clausula> getClausulas() {
        return clausulas;
    }

    public FirmaDigital getFirmaDigital() {
        return firmaDigital;
    }
}
