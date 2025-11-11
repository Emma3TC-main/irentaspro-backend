package com.irentaspro.prop.infrastructure.adapters.in.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.irentaspro.prop.application.dto.PropiedadRequest;
import com.irentaspro.prop.application.dto.PropiedadResponse;
import com.irentaspro.prop.application.services.ActualizarPropiedadService;
import com.irentaspro.prop.application.services.CrearPropiedadService;
import com.irentaspro.prop.application.services.ListarPropiedadesService;

@RestController
@RequestMapping("/api/v1/propiedades")
@CrossOrigin(origins = "*")
public class PropiedadController {

    private final CrearPropiedadService crearService;

    private final ActualizarPropiedadService actualizarService;

    private final ListarPropiedadesService listarService;

    public PropiedadController(
            CrearPropiedadService crearService,
            ActualizarPropiedadService actualizarService,
            ListarPropiedadesService listarService) {
        this.crearService = crearService;
        this.actualizarService = actualizarService;
        this.listarService = listarService;
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody PropiedadRequest request) {
        try {
            return ResponseEntity.ok(crearService.ejecutar(request));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PropiedadResponse> actualizar(@PathVariable UUID id, @RequestBody PropiedadRequest request) {
        return ResponseEntity.ok(actualizarService.ejecutar(id, request));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<PropiedadResponse>> listarPorOwner(@PathVariable UUID ownerId) {
        return ResponseEntity.ok(listarService.ejecutar(ownerId));
    }

}
