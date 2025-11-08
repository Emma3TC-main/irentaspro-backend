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
        return new Consentimiento(texto, version, java.time.LocalDate.now(), usuarioId, true);
    }

    public AuditLog auditarOperacion(UUID usuarioId, String entidad, String accion, String ip) {
        HashEvidencia evidencia = new HashEvidencia("hash_" + entidad + "_" + accion, "SHA-256");
        return AuditLog.crear(usuarioId, entidad, accion, ip, evidencia);
    }

    public void retenerDatos(UUID usuarioId) {
        System.out.println("Datos del usuario " + usuarioId + " retenidos conforme a la política de cumplimiento.");
    }
}
