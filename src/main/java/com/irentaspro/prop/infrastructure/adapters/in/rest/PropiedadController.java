package com.irentaspro.prop.infrastructure.adapters.in.rest;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

@RestController
@RequestMapping("/api/v1/propiedades")
@CrossOrigin(origins = "*")
public class PropiedadController {
    
    private final CrearPropiedadService crearService;

    private final ActualizarPropiedadService actualizarService;

    public PropiedadController(CrearPropiedadService crearService, ActualizarPropiedadService actualizarService) {
        this.crearService = crearService;
        this.actualizarService = actualizarService;
    }

    @PostMapping
    public ResponseEntity<PropiedadResponse> crear(@RequestBody PropiedadRequest request) {
        return ResponseEntity.ok(crearService.ejecutar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PropiedadResponse> actualizar(@PathVariable UUID id, @RequestBody PropiedadRequest request) {
        return ResponseEntity.ok(actualizarService.ejecutar(id, request));
    }
}
