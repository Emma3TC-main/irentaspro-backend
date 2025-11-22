package com.irentaspro.compl.infrastructure.persistence.entity;

import java.time.LocalDate;
import java.util.UUID;

import com.irentaspro.iam.infrastructure.entity.UsuarioEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "consentimiento")
@Getter
@Setter
@NoArgsConstructor
public class ConsentimientoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 4000, nullable = false)
    private String texto;

    @Column(nullable = false)
    private String version;

    private LocalDate fechaAceptacion;

    private boolean aceptado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuario;

    public UUID getUsuarioId() {
        return usuario != null ? usuario.getId() : null;
    }

}
