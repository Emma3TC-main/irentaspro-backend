package com.irentaspro.prop.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;

@Data
public class PropiedadRequest {

    private UUID ownerId;
    private String titulo;
    private String descripcion;

    private String calle;
    private String distrito;
    private String provincia;

    private double latitud;
    private double longitud;

    private BigDecimal precio;
    private String moneda;
}
