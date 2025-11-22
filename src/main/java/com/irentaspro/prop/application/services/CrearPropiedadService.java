package com.irentaspro.prop.application.services;

import com.irentaspro.prop.application.dto.PropiedadRequest;
import com.irentaspro.prop.application.dto.PropiedadResponse;
import com.irentaspro.prop.domain.model.Propiedad;
import com.irentaspro.prop.domain.model.valueobjects.Direccion;
import com.irentaspro.prop.domain.model.valueobjects.Precio;
import com.irentaspro.prop.domain.model.valueobjects.Ubicacion;
import com.irentaspro.prop.domain.repository.PropiedadRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CrearPropiedadService {

  private final PropiedadRepositorio repo;

  /** Firma usada por el controller */
  public PropiedadResponse crearPropiedad(PropiedadRequest request, UUID ownerId) {

    // ---- Value Objects del dominio
    var direccion = new Direccion(
        nullSafe(request.getCalle()),
        nullSafe(request.getDistrito()),
        nullSafe(request.getProvincia())
    );

    // getLatitud() y getLongitud() son double (primitivo) -> no comparar con null
    double lat = request.getLatitud();   // si el campo no viene, será 0.0 por default del primitivo
    double lon = request.getLongitud();  // ídem
    var ubicacion = new Ubicacion(lat, lon, request.getDistrito());

    var precio = new Precio(
        request.getPrecio() == null ? BigDecimal.ZERO : request.getPrecio(),
        nullSafe(request.getMoneda())
    );

    // ---- Crear modelo de dominio
    var prop = new Propiedad(
        ownerId,
        nullSafe(request.getTitulo()),
        nullSafe(request.getDescripcion()),
        direccion,
        ubicacion,
        precio
    );

    // Persistir (tu repositorio de dominio hace el mapping a JPA)
    var saved = repo.guardar(prop);

    // ---- Respuesta DTO
    return PropiedadResponse.builder()
        .id(saved.getId())
        .ownerId(saved.getOwnerId())
        .titulo(saved.getTitulo())
        .descripcion(saved.getDescripcion())
        .calle(saved.getDireccion() != null ? saved.getDireccion().getCalle() : "")
        .distrito(saved.getDireccion() != null ? saved.getDireccion().getDistrito() : "")
        .provincia(saved.getDireccion() != null ? saved.getDireccion().getProvincia() : "")
        .latitud(saved.getUbicacion() != null ? saved.getUbicacion().getLatitud() : 0.0)
        .longitud(saved.getUbicacion() != null ? saved.getUbicacion().getLongitud() : 0.0)
        .moneda(saved.getPrecio() != null ? saved.getPrecio().getMoneda() : "PEN")
        .precio(saved.getPrecio() != null ? saved.getPrecio().getValor() : null)
        .build();
  }

  private String nullSafe(String s) {
    return s == null ? "" : s.trim();
  }
}
