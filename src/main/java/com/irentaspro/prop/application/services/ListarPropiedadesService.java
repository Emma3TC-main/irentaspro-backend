// src/main/java/com/irentaspro/prop/application/services/ListarPropiedadesService.java
package com.irentaspro.prop.application.services;

import com.irentaspro.prop.application.dto.PropiedadResponse;
import com.irentaspro.prop.domain.model.Propiedad;
import com.irentaspro.prop.domain.repository.PropiedadRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ListarPropiedadesService {

  private final PropiedadRepositorio repo;

  /** 🔹 NUEVO */
  public PropiedadResponse obtenerPorId(UUID id) {
    var p = repo.buscarPorId(id)
        .orElseThrow(() -> new IllegalArgumentException("Propiedad no encontrada"));
    return toResponse(p);
  }

  private PropiedadResponse toResponse(Propiedad p) {
    return PropiedadResponse.builder()
        .id(p.getId())
        .ownerId(p.getOwnerId())
        .titulo(p.getTitulo())
        .descripcion(p.getDescripcion())
        .calle(p.getDireccion() != null ? p.getDireccion().getCalle() : "")
        .distrito(p.getDireccion() != null ? p.getDireccion().getDistrito() : "")
        .provincia(p.getDireccion() != null ? p.getDireccion().getProvincia() : "")
        .latitud(p.getUbicacion() != null ? p.getUbicacion().getLatitud() : 0.0)
        .longitud(p.getUbicacion() != null ? p.getUbicacion().getLongitud() : 0.0)
        .moneda(p.getPrecio() != null ? p.getPrecio().getMoneda() : "PEN")
        .precio(p.getPrecio() != null ? p.getPrecio().getValor() : null)
        .build();
  }
}
