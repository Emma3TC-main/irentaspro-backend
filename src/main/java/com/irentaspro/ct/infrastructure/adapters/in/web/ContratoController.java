package com.irentaspro.ct.infrastructure.adapters.in.web;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.*;

import com.irentaspro.ct.application.dto.ContratoDTO;
import com.irentaspro.ct.application.service.ContratoApplicationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/contratos")
@RequiredArgsConstructor
public class ContratoController {

    private final ContratoApplicationService contratoAppService;

    @PostMapping
    public ContratoDTO crear(@RequestBody ContratoDTO dto) {
        return contratoAppService.crearContrato(dto);
    }

    @PostMapping("/{id}/firmar")
    public ContratoDTO firmar(@PathVariable UUID id) {
        return contratoAppService.firmarContrato(id);
    }

    @GetMapping
    public List<ContratoDTO> listar() {
        return contratoAppService.listarContratos();
    }
}
