package com.irentaspro.prop.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.irentaspro.prop.domain.model.Propiedad;

import lombok.Data;

@Data
public class PropiedadResponse {

    private UUID id;
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

    // Constructor estático de fábrica
    public static PropiedadResponse fromDomain(Propiedad propiedad) {
        PropiedadResponse dto = new PropiedadResponse();
        dto.id = propiedad.getId();
        dto.ownerId = propiedad.getOwnerId();
        dto.titulo = propiedad.getTitulo();
        dto.descripcion = propiedad.getDescripcion();

        dto.calle = propiedad.getDireccion().getCalle();
        dto.distrito = propiedad.getDireccion().getDistrito();
        dto.provincia = propiedad.getDireccion().getProvincia();

        dto.latitud = propiedad.getUbicacion().getLatitud();
        dto.longitud = propiedad.getUbicacion().getLongitud();

        dto.precio = propiedad.getPrecio().getValor();
        dto.moneda = propiedad.getPrecio().getMoneda();
        return dto;
    }

}
