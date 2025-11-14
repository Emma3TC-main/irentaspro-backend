package com.irentaspro.common.domain.model.valueobjects;

public record Direccion(String calle, String distrito, String provincia) {  
        public Direccion { 
        if (calle == null || calle.isBlank()) {
            throw new IllegalArgumentException("La 'calle' no puede ser nula o vacía.");
        }
        if (distrito == null || distrito.isBlank()) {
            throw new IllegalArgumentException("El 'distrito' no puede ser nulo o vacío.");
        }
        if (provincia == null || provincia.isBlank()) {
            throw new IllegalArgumentException("La 'provincia' no puede ser nula o vacía.");
        }

        }   
}
