package com.irentaspro.common.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;

/**
 * Clase base para agregados raíz.
 * Permite registrar y recuperar eventos de dominio.
 */
public abstract class AggregateRoot extends Entidad {

    @Getter
    private final List<DomainEvent> eventos = new ArrayList<>();

    protected AggregateRoot() {
        super();
    }

    protected AggregateRoot(java.util.UUID id) {
        super(id);
    }

    /**
     * Registra un nuevo evento de dominio.
     */
    protected void registrarEvento(DomainEvent evento) {
        eventos.add(evento);
    }

    protected void agregarEventoDominio(DomainEvent evento) {
        registrarEvento(evento);
    }

    /**
     * Devuelve los eventos de dominio registrados (solo lectura).
     */
    public List<DomainEvent> getEventos() {
        return Collections.unmodifiableList(eventos);
    }

    /**
     * Limpia los eventos después de que han sido publicados.
     */
    public void limpiarEventos() {
        eventos.clear();
    }

    /**
     * Cada agregado define sus propias invariantes de negocio.
     */
    @Override
    public abstract void validarInvariantes();
}
