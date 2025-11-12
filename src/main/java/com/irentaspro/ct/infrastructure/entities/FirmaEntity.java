package com.irentaspro.ct.infrastructure.entities;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "firmas_digitales")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FirmaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String proveedor;
    private String certificado;
    private String hashValor;
    private String algoritmo;
    private LocalDateTime fecha;
}
