package com.irentaspro.pay.infrastructure.adapters.in.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.irentaspro.pay.application.command.ConciliarPagoCommand;
import com.irentaspro.pay.application.command.handler.ConciliarPagoCommandHandler;
import com.irentaspro.pay.domain.model.TransaccionPSP;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/api/pagos/conciliar")
@RequiredArgsConstructor
public class ConciliacionController {

    private final ConciliarPagoCommandHandler handler;

    @PostMapping
    public ResponseEntity<?> conciliarPagos(@RequestBody ConciliarRequest request) {
        var command = new ConciliarPagoCommand(request.getTransacciones());
        handler.handle(command);
        return ResponseEntity.ok("Conciliación procesada correctamente");
    }

    @Data
    public static class ConciliarRequest {
        private List<TransaccionPSP> transacciones;
    }
}
