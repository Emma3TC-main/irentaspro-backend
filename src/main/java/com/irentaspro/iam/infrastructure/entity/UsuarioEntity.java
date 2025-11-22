package com.irentaspro.iam.infrastructure.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.irentaspro.compl.infrastructure.persistence.entity.ConsentimientoEntity;
import com.irentaspro.compl.infrastructure.persistence.entity.SolicitudARCOEntity;
import com.irentaspro.prop.infrastructure.persistence.entity.PropiedadEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nombre;

    @Column(unique = true, nullable = false)
    private String email;

    private String passwordHash;
    private String tipoCuenta;
    private LocalDate fechaVencimiento;

    @OneToMany(mappedBy = "usuario")
    private List<ConsentimientoEntity> consentimientos;

    @OneToMany(mappedBy = "usuario")
    private List<SolicitudARCOEntity> solicitudes;

    @OneToMany(mappedBy = "propietario", fetch = FetchType.LAZY)
    private List<PropiedadEntity> propiedades;
}
