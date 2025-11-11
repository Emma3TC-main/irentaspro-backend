package com.irentaspro.pay.infrastructure.adapters.in.rest;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.irentaspro.pay.application.dto.PagoDTO;
import com.irentaspro.pay.application.service.PagoApplicationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {
    private final PagoApplicationService pagoService;

    @PostMapping
    public ResponseEntity<PagoDTO> registrarPago(@RequestBody PagoDTO dto) {
        PagoDTO nuevoPago = pagoService.registrarPago(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPago);
    }

    @PostMapping("/{id}/confirmar")
    public ResponseEntity<Void> confirmarPago(@PathVariable UUID id) {
        pagoService.confirmarPago(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoDTO> obtenerPago(@PathVariable UUID id) {
        PagoDTO pago = pagoService.obtenerPago(id);
        return ResponseEntity.ok(pago);
    }
}
