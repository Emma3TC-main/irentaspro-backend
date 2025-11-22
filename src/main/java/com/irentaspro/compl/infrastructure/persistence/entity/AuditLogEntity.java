package com.irentaspro.compl.infrastructure.persistence.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "audit_log")
@Getter
@Setter
@NoArgsConstructor
public class AuditLogEntity {
    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name = "usuario_id", columnDefinition = "uuid", nullable = false)
    private UUID usuarioId;

    @Column(nullable = false)
    private String entidad;

    @Column(nullable = false)
    private String accion;

    @Column(nullable = false)
    private LocalDateTime fecha;

    private String ip;

    @Column(name = "hash_evidencia", nullable = true)
    private String hashEvidencia; // guardamos "algoritmo:valor" (HashEvidencia.toString)

    public AuditLogEntity(UUID id, UUID usuarioId, String entidad, String accion, LocalDateTime fecha, String ip,
            String hashEvidencia) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.entidad = entidad;
        this.accion = accion;
        this.fecha = fecha;
        this.ip = ip;
        this.hashEvidencia = hashEvidencia;
    }
}
