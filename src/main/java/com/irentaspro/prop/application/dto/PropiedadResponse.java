package com.irentaspro.prop.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

import com.irentaspro.prop.domain.model.Propiedad;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropiedadResponse {

  private UUID id;
  private UUID ownerId;
  private String titulo;
  private String descripcion;

  private String calle;
  private String distrito;
  private String provincia;

  private Double latitud;
  private Double longitud;

  private String moneda;
  private BigDecimal precio;

  // 🔹 Factory estático para mapear desde el dominio
  public static PropiedadResponse fromDomain(Propiedad p) {
    if (p == null) return null;

    return PropiedadResponse.builder()
        .id(p.getId())
        .ownerId(p.getOwnerId())
        .titulo(ns(p.getTitulo()))
        .descripcion(ns(p.getDescripcion()))
        .calle(p.getDireccion() != null ? ns(p.getDireccion().getCalle()) : "")
        .distrito(p.getDireccion() != null ? ns(p.getDireccion().getDistrito()) : "")
        .provincia(p.getDireccion() != null ? ns(p.getDireccion().getProvincia()) : "")
        .latitud(p.getUbicacion() != null ? p.getUbicacion().getLatitud() : 0.0)
        .longitud(p.getUbicacion() != null ? p.getUbicacion().getLongitud() : 0.0)
        .moneda(p.getPrecio() != null ? ns(p.getPrecio().getMoneda()) : "PEN")
        .precio(p.getPrecio() != null ? p.getPrecio().getValor() : null)
        .build();
  }

  private static String ns(String s) { return s == null ? "" : s.trim(); }
}
