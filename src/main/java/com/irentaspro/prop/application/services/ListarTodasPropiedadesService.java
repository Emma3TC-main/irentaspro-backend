// src/main/java/com/irentaspro/prop/application/services/ListarTodasPropiedadesService.java
package com.irentaspro.prop.application.services;

import com.irentaspro.prop.application.dto.PropiedadResponse;
import com.irentaspro.prop.domain.model.Propiedad;
import com.irentaspro.prop.domain.repository.PropiedadRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarTodasPropiedadesService {

  private final PropiedadRepositorio repo;

  /** 🔹 NUEVO: método que espera el controller */
  public List<PropiedadResponse> listarTodas() {
    return repo.buscarTodos().stream()
        .map(this::toResponse)
        .toList();
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
