package com.irentaspro.ct.domain.model.valueobjects;

import java.time.LocalDate;

public class PeriodoContrato {
    private final LocalDate inicio;
    private final LocalDate fin;

    public PeriodoContrato(LocalDate inicio, LocalDate fin) {
        if (inicio == null || fin == null)
            throw new IllegalArgumentException("Las fechas de inicio y fin son obligatorias.");
        if (fin.isBefore(inicio))
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior al inicio.");
        this.inicio = inicio;
        this.fin = fin;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public LocalDate getFin() {
        return fin;
    }

    @Override
    public String toString() {
        return inicio + " - " + fin;
    }
}
