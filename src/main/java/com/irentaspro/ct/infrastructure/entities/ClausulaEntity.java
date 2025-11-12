package com.irentaspro.ct.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "clausulas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClausulaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;
    private String descripcion;
}
