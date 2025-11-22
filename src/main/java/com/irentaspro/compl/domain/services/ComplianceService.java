package com.irentaspro.compl.domain.services;

import java.util.UUID;

import com.irentaspro.common.domain.model.ServiciosDominio;
import com.irentaspro.compl.domain.model.AuditLog;
import com.irentaspro.compl.domain.model.Consentimiento;
import com.irentaspro.compl.domain.model.HashEvidencia;

public class ComplianceService implements ServiciosDominio {

    @Override
    public void ejecutar() {
        // Método genérico del contrato, sin prints
    }

    /**
     * Registra un nuevo consentimiento aceptado por el usuario.
     */
    public Consentimiento registrarConsentimiento(UUID usuarioId, String texto, String version) {
        Consentimiento consentimiento = Consentimiento.crearNuevo(texto, version, usuarioId);
        consentimiento.aceptar();
        return consentimiento;
    }

    /**
     * Registra un evento de auditoría del sistema.
     */
    public AuditLog auditarOperacion(UUID usuarioId, String entidad, String accion, String ip) {
        HashEvidencia evidencia = new HashEvidencia(
                "hash_" + entidad + "_" + accion + "_" + usuarioId,
                "SHA-256");

        return AuditLog.crear(usuarioId, entidad, accion, ip, evidencia);
    }

    /**
     * Simula la retención de datos según políticas.
     */
    public void retenerDatos(UUID usuarioId) {
        // Lógica de dominio: aquí solo aplican reglas, no logs ni prints
        // (podrías delegar esta acción a un evento de dominio si lo necesitas)
    }
}
