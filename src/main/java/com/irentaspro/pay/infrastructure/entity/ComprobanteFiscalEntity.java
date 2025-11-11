package com.irentaspro.pay.infrastructure.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comprobantes_fiscales")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComprobanteFiscalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String tipo;
    @Lob
    private String xml;
    private String ticketSUNAT;
}
