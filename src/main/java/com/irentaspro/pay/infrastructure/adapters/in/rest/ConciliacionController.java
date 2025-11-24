package com.irentaspro.pay.infrastructure.adapters.in.rest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.irentaspro.pay.application.command.ConciliarPagoCommand;
import com.irentaspro.pay.application.command.handler.ConciliarPagoCommandHandler;
import com.irentaspro.pay.application.dto.TransaccionPSPDTO;
import com.irentaspro.pay.domain.model.TransaccionPSP;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pagos/conciliar")
@RequiredArgsConstructor
public class ConciliacionController {

    private static final Logger log = LoggerFactory.getLogger(ConciliacionController.class);

    private final ConciliarPagoCommandHandler handler;
    private final ObjectMapper mapper; // Para convertir Map <-> JSON si es necesario

    @PostMapping
    public ResponseEntity<?> conciliarPagos(@Valid @RequestBody ConciliarRequest request) {
        log.info("[Conciliacion] Recibidas {} transacciones", request.getTransacciones().size());

        List<TransaccionPSP> transaccionesDominio = request.getTransacciones()
                .stream()
                .map(dto -> {
                    if (dto.getProvider() == null || dto.getProvider().isBlank()) {
                        throw new IllegalArgumentException("Provider obligatorio");
                    }
                    if (dto.getRef() == null || dto.getRef().isBlank()) {
                        throw new IllegalArgumentException("Ref obligatorio");
                    }
                    Map<String, Object> payload = dto.getPayload() != null ? dto.getPayload() : Map.of();
                    return new TransaccionPSP(dto.getProvider(), dto.getRef(), payload);
                })
                .collect(Collectors.toList());

        handler.handle(new ConciliarPagoCommand(transaccionesDominio));

        return ResponseEntity.ok().body(new ApiSimpleResponse("Conciliación procesada correctamente"));
    }

    @Data
    public static class ConciliarRequest {
        @NotEmpty(message = "La lista de transacciones no puede estar vacía")
        private List<TransaccionPSPDTO> transacciones;
    }

    @Data
    public static class ApiSimpleResponse {
        private final String mensaje;
    }
}
