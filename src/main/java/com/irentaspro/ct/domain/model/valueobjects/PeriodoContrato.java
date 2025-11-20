package com.irentaspro.ct.domain.model.valueobjects;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

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

    /**
     * Devuelve la cantidad de meses completos/parcelas del periodo, contado de
     * forma
     * inclusiva entre el mes/año de inicio y el mes/año de fin.
     *
     * Ejemplos:
     * - inicio=2025-01-15, fin=2025-01-31 -> 1
     * - inicio=2025-01-15, fin=2025-03-01 -> 3
     *
     * Esto es apropiado para calcular cuántas cuotas mensuales se deben generar.
     */
    public long mesesEntre() {
        YearMonth ymInicio = YearMonth.from(inicio);
        YearMonth ymFin = YearMonth.from(fin);
        long meses = ChronoUnit.MONTHS.between(ymInicio, ymFin) + 1; // inclusivo
        return meses;
    }

    /**
     * Devuelve el primer día del mes de inicio del periodo.
     * Útil si al generar calendario quieres normalizar la fecha al primer día.
     */
    public LocalDate primerDiaInicio() {
        return inicio.withDayOfMonth(1);
    }

    @Override
    public String toString() {
        return inicio + " - " + fin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof PeriodoContrato))
            return false;
        PeriodoContrato that = (PeriodoContrato) o;
        return Objects.equals(inicio, that.inicio) && Objects.equals(fin, that.fin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inicio, fin);
    }

    public int getMesesDeDuracion() {
        YearMonth ymInicio = YearMonth.from(inicio);
        YearMonth ymFin = YearMonth.from(fin);
        return (int) (ChronoUnit.MONTHS.between(ymInicio, ymFin) + 1);
    }

    public boolean contiene(LocalDate fecha) {
        return (fecha.isEqual(inicio) || fecha.isAfter(inicio))
                && (fecha.isEqual(fin) || fecha.isBefore(fin));
    }
}
