// src/main/java/com/irentaspro/prop/infrastructure/adapters/in/rest/PropiedadController.java
package com.irentaspro.prop.infrastructure.adapters.in.rest;

import com.irentaspro.iam.domain.repository.IAuthRepositorio;
import com.irentaspro.prop.application.dto.PropiedadRequest;
import com.irentaspro.prop.application.dto.PropiedadResponse;
import com.irentaspro.prop.application.services.CrearPropiedadService;
import com.irentaspro.prop.application.services.ListarPropiedadesService;
import com.irentaspro.prop.application.services.ListarTodasPropiedadesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/propiedades")
public class PropiedadController {

  private final CrearPropiedadService crearPropiedadService;
  private final ListarPropiedadesService listarPropiedadesService;
  private final ListarTodasPropiedadesService listarTodasPropiedadesService;
  private final IAuthRepositorio usuarios;

  /** Catálogo público (FREE y anónimo) */
  @GetMapping
  public ResponseEntity<List<PropiedadResponse>> listarTodas() {
    return ResponseEntity.ok(listarTodasPropiedadesService.listarTodas());
  }

  /** Detalle público */
  @GetMapping("/{id}")
  public ResponseEntity<PropiedadResponse> getById(@PathVariable UUID id) {
    return ResponseEntity.ok(listarPropiedadesService.obtenerPorId(id));
  }

  /** Crear propiedad (requiere JWT y plan PREMIUM) */
  @PostMapping
  public ResponseEntity<PropiedadResponse> crear(@RequestBody PropiedadRequest request,
                                                 Principal principal) {
    String email = principal.getName();

    var usuario = usuarios.buscarPorEmail(email)
        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

    if (usuario.getTipoCuenta() == null ||
        !usuario.getTipoCuenta().equalsIgnoreCase("PREMIUM")) {
      throw new IllegalArgumentException("Solo usuarios PREMIUM pueden crear propiedades");
    }

    UUID ownerId = usuario.getId();
    var response = crearPropiedadService.crearPropiedad(request, ownerId);
    return ResponseEntity.ok(response);
  }
}
