package com.irentaspro.ct.domain.model.valueobjects;

import java.time.LocalDate;

public class PeriodoContrato {
    private final LocalDate inicio;
    private final LocalDate fin;

    public PeriodoContrato(LocalDate inicio, LocalDate fin) {
        if (fin.isBefore(inicio)) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }
        this.inicio = inicio;
        this.fin = fin;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public LocalDate getFin() {
        return fin;
    }
}
