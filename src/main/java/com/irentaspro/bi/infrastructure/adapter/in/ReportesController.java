package com.irentaspro.bi.infrastructure.adapter.in;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.irentaspro.bi.application.dto.KPIContratoResponse;
import com.irentaspro.bi.application.dto.KPIGlobalResponse;
import com.irentaspro.bi.application.usecase.ObtenerKPIsUseCase;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ReportesController {

    private final ObtenerKPIsUseCase useCase;

    public ReportesController(ObtenerKPIsUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping("/kpi-global")
    public ResponseEntity<KPIGlobalResponse> obtenerGlobal() {
        return ResponseEntity.ok(useCase.obtenerKPIsGlobales());
    }

    @GetMapping("/kpi-contratos")
    public ResponseEntity<List<KPIContratoResponse>> obtenerContratos() {
        return ResponseEntity.ok(useCase.obtenerKPIsPorContrato());
    }
}