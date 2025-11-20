package com.irentaspro.bi.application.usecase;

import java.util.List;
import java.util.stream.Collectors;

import com.irentaspro.bi.application.dto.KPIContratoResponse;
import com.irentaspro.bi.application.dto.KPIGlobalResponse;
import com.irentaspro.bi.domain.port.ReporteRepository;

public class ObtenerKPIsUseCase {

    private final ReporteRepository repository;

    public ObtenerKPIsUseCase(ReporteRepository repository) {
        this.repository = repository;
    }

    public KPIGlobalResponse obtenerKPIsGlobales() {
        var kpi = repository.obtenerKPIGeneral();
        return new KPIGlobalResponse(
                kpi.totalContratos(),
                kpi.contratosActivos(),
                kpi.ingresosTotales(),
                kpi.pagosRealizados(),
                kpi.deudaPendiente(),
                kpi.fechaActual());
    }

    public List<KPIContratoResponse> obtenerKPIsPorContrato() {
        return repository.obtenerKPIContratos()
                .stream()
                .map(kpi -> new KPIContratoResponse(
                        kpi.contratoId(),
                        kpi.montoTotal(),
                        kpi.montoPendiente(),
                        kpi.inicio(),
                        kpi.fin(),
                        kpi.estado()))
                .collect(Collectors.toList());
    }
}