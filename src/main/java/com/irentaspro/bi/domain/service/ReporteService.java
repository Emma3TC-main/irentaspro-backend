package com.irentaspro.bi.domain.service;

import java.util.Map;

import com.irentaspro.bi.domain.model.MaterializedViewsDW;
import com.irentaspro.bi.domain.model.Reporte;
import com.irentaspro.common.domain.model.ServiciosDominio;

/**
 * Servicio de dominio para generación y exportación de reportes.
 */
public class ReporteService implements ServiciosDominio {
    private final MaterializedViewsDW materializedViewsDW;

    public ReporteService(MaterializedViewsDW materializedViewsDW) {
        this.materializedViewsDW = materializedViewsDW;
    }

    @Override
    public void ejecutar() {
        // No-op: implementación del contrato de ServicioDominio
    }

    /**
     * Genera un reporte a partir de un tipo y un conjunto de filtros.
     */
    public Reporte generarReporte(String tipo, Map<String, Object> filtros) {
        // Simula la creación del contenido del reporte en base a filtros
        StringBuilder contenido = new StringBuilder("📘 Reporte tipo: ").append(tipo).append("\n");
        if (filtros != null && !filtros.isEmpty()) {
            contenido.append("Filtros aplicados:\n");
            filtros.forEach((k, v) -> contenido.append(" - ").append(k).append(": ").append(v).append("\n"));
        }
        contenido.append("Fecha: ").append(java.time.LocalDateTime.now());

        System.out.println("✅ Generando reporte de tipo: " + tipo);
        return Reporte.crear(tipo, contenido.toString());
    }

    /**
     * Exporta un reporte a un formato específico (ej: PDF, CSV, JSON).
     */
    public String exportar(Reporte reporte, String formato) {
        System.out.println("📤 Exportando reporte " + reporte.getTipo() + " en formato: " + formato);
        return "Reporte_" + reporte.getTipo() + "." + formato.toLowerCase();
    }

    /**
     * Actualiza las vistas materializadas del DW antes de generar nuevos reportes.
     */
    public void actualizarDataWarehouse() {
        materializedViewsDW.actualizar();
    }

    // Método de conveniencia
    public static ReporteService crearConDW() {
        return new ReporteService(new MaterializedViewsDW());
    }
}
