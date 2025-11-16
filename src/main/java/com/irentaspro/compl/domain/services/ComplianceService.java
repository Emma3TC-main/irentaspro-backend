package com.irentaspro.compl.domain.services;

import java.util.UUID;

import com.irentaspro.common.domain.model.ServiciosDominio;
import com.irentaspro.compl.domain.model.AuditLog;
import com.irentaspro.compl.domain.model.Consentimiento;
import com.irentaspro.compl.domain.model.HashEvidencia;

public class ComplianceService implements ServiciosDominio {
    @Override
    public void ejecutar() {
        // no-op: el método general del contrato de ServicioDominio
    }   

    public Consentimiento registrarConsentimiento(UUID usuarioId, String texto, String version) {
        
        //TDD
        if (usuarioId == null) {
            throw new IllegalArgumentException("El 'usuarioId' no puede ser nulo.");
        }
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("El 'texto' no puede ser nulo o vacío.");
        }
        if (version == null || version.isEmpty()) { // Versión puede ser "1", pero no ""
            throw new IllegalArgumentException("La 'version' no puede ser nula o vacía.");
        }
        return new Consentimiento(texto, version, java.time.LocalDate.now(), usuarioId, true);
    }

    public AuditLog auditarOperacion(UUID usuarioId, String entidad, String accion, String ip) {
        
        //TDD
        if (usuarioId == null) {
            throw new IllegalArgumentException("El 'usuarioId' no puede ser nulo.");
        }
        if (entidad == null || entidad.isBlank()) {
            throw new IllegalArgumentException("La 'entidad' no puede ser nula o vacía.");
        }
        if (accion == null || accion.isEmpty()) {
            throw new IllegalArgumentException("La 'accion' no puede ser nula o vacía.");
        }
                
        HashEvidencia evidencia = new HashEvidencia("hash_" + entidad + "_" + accion, "SHA-256");
        return AuditLog.crear(usuarioId, entidad, accion, ip, evidencia);
    }

    public void retenerDatos(UUID usuarioId) {
        //TDD   
    if (usuarioId == null) {
        throw new IllegalArgumentException("El 'usuarioId' no puede ser nulo.");
    }
    
    // Ahora es seguro usar la variable
    System.out.println("Datos del usuario " + usuarioId + " retenidos...");
    }   

}
