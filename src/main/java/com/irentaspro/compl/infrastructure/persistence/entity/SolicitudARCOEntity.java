package com.irentaspro.compl.infrastructure.persistence.entity;

import java.time.LocalDate;
import java.util.UUID;

import com.irentaspro.compl.domain.model.SolicitudARCO.EstadoSolicitud;
import com.irentaspro.compl.domain.model.SolicitudARCO.TipoSolicitud;
import com.irentaspro.iam.infrastructure.entity.UsuarioEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "solicitud_arco")
@Getter
@Setter
@NoArgsConstructor
public class SolicitudARCOEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_solicitud", nullable = false)
    private TipoSolicitud tipoSolicitud;

    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    private EstadoSolicitud estado;

    @Column(length = 4000)
    private String respuesta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuario;

    public UUID getUsuarioId() {
        return usuario != null ? usuario.getId() : null;
    }

}
