package com.irentaspro.ct.infrastructure.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "contratos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContratoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private UUID propiedadId;
    private UUID inquilinoId;

    private LocalDate inicio;
    private LocalDate fin;

    private Double monto;
    private String moneda;
    private String estado;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "contrato_clausulas", joinColumns = @JoinColumn(name = "contrato_id"))
    @Column(name = "descripcion")
    private List<String> clausulas;
}
