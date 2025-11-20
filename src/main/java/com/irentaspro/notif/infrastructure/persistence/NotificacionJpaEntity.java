package com.irentaspro.notif.infrastructure.persistence;

import com.irentaspro.notif.domain.model.Notificacion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "notificaciones")
@Getter
@Setter
public class NotificacionJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String destinatario;
    private String asunto;
    private String mensaje;
    private String tipo;
    private String estado;

}