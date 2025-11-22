package com.irentaspro.compl.infrastructure.persistence.entity;

import java.time.LocalDate;
import java.util.UUID;

import com.irentaspro.compl.domain.model.SolicitudARCO.EstadoSolicitud;
import com.irentaspro.compl.domain.model.SolicitudARCO.TipoSolicitud;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "solicitud_arco")
@NoArgsConstructor
public class SolicitudARCOEntity {
    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name = "usuario_id", columnDefinition = "uuid", nullable = false)
    private UUID usuarioId;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_solicitud", nullable = false)
    private TipoSolicitud tipoSolicitud; // ACCESO, RECTIFICACION, ...

    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    private EstadoSolicitud estado;

    @Column(length = 4000)
    private String respuesta;

    public SolicitudARCOEntity(UUID id, UUID usuarioId, TipoSolicitud tipoSolicitud, LocalDate fecha,
            EstadoSolicitud estado, String respuesta) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.tipoSolicitud = tipoSolicitud;
        this.fecha = fecha;
        this.estado = estado;
        this.respuesta = respuesta;
    }

}
