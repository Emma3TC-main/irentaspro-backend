package com.irentaspro.ct.domain.model;

import java.util.List;
import java.util.UUID;

import com.irentaspro.common.domain.model.AggregateRoot;
import com.irentaspro.common.domain.model.valueobjects.Monto;
import com.irentaspro.ct.domain.model.valueobjects.EstadoContrato;
import com.irentaspro.ct.domain.model.valueobjects.PeriodoContrato;

public class Contrato extends AggregateRoot {
    private UUID propiedadId;
    private UUID inquilinoId;
    private PeriodoContrato periodo;
    private Monto monto;
    private EstadoContrato estado;
    private List<Clausula> clausulas;
    private FirmaDigital firmaDigital;

    // Constructor
    public Contrato(UUID propiedadId, UUID inquilinoId, PeriodoContrato periodo, Monto monto,
            List<Clausula> clausulas) {
            
        if (periodo == null) {    
        throw new IllegalArgumentException("El 'periodo' no puede ser nulo.");
        }
        if (monto == null) {
            throw new IllegalArgumentException("El 'monto' no puede ser nulo.");
        }
        if (clausulas == null) {
            throw new IllegalArgumentException("La lista de 'clausulas' no puede ser nula.");
        }
        this.propiedadId = propiedadId;
        this.inquilinoId = inquilinoId;
        this.periodo = periodo;
        this.monto = monto;
        this.clausulas = clausulas;
        this.estado = new EstadoContrato("BORRADOR");
        this.validarInvariantes();
    }

    // Método de negocio
    public void generarCalendarioPagos() {
        // Lógica para generar el calendario de pagos basado en el periodo y monto
    }

    public void firmar(FirmaDigital firma) {
        // Lógica para firmar el contrato digitalmente
        if (firma == null) {
            throw new IllegalArgumentException("La 'firma' digital no puede ser nula.");
        }

        if (this.estado.es("BORRADOR")) {
            this.firmaDigital = firma;
            this.estado = new EstadoContrato("FIRMADO");
        } else {
            throw new IllegalStateException("El contrato no puede ser firmado en su estado actual.");
        }
    }

    public void renovar(PeriodoContrato nuevoPeriodo) {
        if (nuevoPeriodo == null) {
            throw new IllegalArgumentException("El 'nuevoPeriodo' de renovación no puede ser nulo.");
        }

        if (this.estado.es("FINALIZADO")) {
            this.periodo = nuevoPeriodo;
            this.estado = new EstadoContrato("RENOVADO");
        } else {
            throw new IllegalStateException("Solo se pueden renovar contratos finalizados.");
        }
    }

    public void finalizar() {
        if (this.estado.es("FIRMADO") || this.estado.es("RENOVADO")) {
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
    }

    // Getters
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
