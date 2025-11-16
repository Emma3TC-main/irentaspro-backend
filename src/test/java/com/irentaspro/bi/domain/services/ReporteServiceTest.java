package com.irentaspro.bi.domain.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.irentaspro.bi.domain.model.MaterializedViewsDW;
import com.irentaspro.bi.domain.model.Reporte;
import com.irentaspro.bi.domain.service.ReporteService;

class ReporteServiceTest {
    
    @Mock
    private MaterializedViewsDW mockViewsDW; 

    private ReporteService reporteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reporteService = new ReporteService(mockViewsDW);
    }

    @Test
    void lanzarExceptionSiVistasDWNula() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ReporteService(null); 
        });
    }

    @Test
    void lanzarExceptionSiTipoReporteEsNuloOVacio() {
        assertThrows(IllegalArgumentException.class, () -> {
            reporteService.generarReporte(null, new HashMap<>());
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            reporteService.generarReporte("  ", new HashMap<>()); 
        });
    }

    @Test
    void lanzarExceptionSiReporteOFormatoEsNulo() {
        Reporte reporteValido = Reporte.crear("Test", "Contenido");

        assertThrows(IllegalArgumentException.class, () -> {
            reporteService.exportar(null, "PDF");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            reporteService.exportar(reporteValido, null);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            reporteService.exportar(reporteValido, "");
        });
    }

    @Test
    void debeCrearReporteCorrectamente() {
        String tipo = "Ventas";
        Map<String, Object> filtros = Map.of("mes", "Octubre");

        Reporte reporte = assertDoesNotThrow(() -> {
            return reporteService.generarReporte(tipo, filtros);
        });

        assertNotNull(reporte);
        assertEquals(tipo, reporte.getTipo());
        assertTrue(reporte.getContenido().contains("mes: Octubre"));
    }
    @Test
    void debeExportarReporteCorrectamente() {
        Reporte reporte = Reporte.crear("Finanzas", "Contenido...");
        String formato = "PDF";

        String nombreArchivo = assertDoesNotThrow(() -> {
            return reporteService.exportar(reporte, formato);
        });

        assertEquals("Reporte_Finanzas.pdf", nombreArchivo);
    }
    
    @Test
    void debeCrearConDWCorrectamente() {
        ReporteService servicio = assertDoesNotThrow(() -> {
            return ReporteService.crearConDW();
        });
        assertNotNull(servicio);
    }

    @Test
    void ejecutarNoDebeLanzarException() {
        assertDoesNotThrow(() -> {
            reporteService.ejecutar();
        });
    }

    @Test
    void debeLlamarActualizarEnVistasDW() {
        reporteService.actualizarDataWarehouse();

        verify(mockViewsDW, times(1)).actualizar();
        
        verifyNoMoreInteractions(mockViewsDW);
    }
}
