package com.irentaspro.compl.infrastructure.persistence.entity;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "consentimiento")
@NoArgsConstructor
public class ConsentimientoEntity {
    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(length = 4000, nullable = false)
    private String texto;

    @Column(nullable = false)
    private String version;

    @Column(name = "usuario_id", columnDefinition = "uuid", nullable = false)
    private UUID usuarioId;

    private LocalDate fechaAceptacion;

    private boolean aceptado;

    public ConsentimientoEntity(UUID id, String texto, String version, UUID usuarioId, LocalDate fechaAceptacion,
            boolean aceptado) {
        this.id = id;
        this.texto = texto;
        this.version = version;
        this.usuarioId = usuarioId;
        this.fechaAceptacion = fechaAceptacion;
        this.aceptado = aceptado;
    }

}
