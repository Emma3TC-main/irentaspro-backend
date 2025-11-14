package com.irentaspro.common.domain.model.valueobjects;

import java.sql.Date;

public record PeriodoContrato(Date fechaInicio, Date fechaFin) {
        public PeriodoContrato { 
        if (fechaInicio == null) {
            throw new IllegalArgumentException("La 'fechaInicio' no puede ser nula.");
        }
        
        if (fechaFin == null) {
            throw new IllegalArgumentException("La 'fechaFin' no puede ser nula.");
        }
         
        if (fechaFin.before(fechaInicio)) {
            throw new IllegalArgumentException("La 'fechaFin' no puede ser anterior a la 'fechaInicio'.");
        }

        }
}
