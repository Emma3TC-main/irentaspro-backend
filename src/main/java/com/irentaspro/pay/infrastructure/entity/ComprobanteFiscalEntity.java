package com.irentaspro.pay.infrastructure.entity;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comprobantes_fiscales")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComprobanteFiscalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String tipo;

    @Lob
    private String xml;

    private String ticketSUNAT;
}
