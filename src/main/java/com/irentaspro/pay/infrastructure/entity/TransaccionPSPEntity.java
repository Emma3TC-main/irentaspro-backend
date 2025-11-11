package com.irentaspro.pay.infrastructure.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transacciones_psp")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransaccionPSPEntity {
    @Id
    @GeneratedValue
    private UUID id;

    private String provider;
    private String ref;

    @Lob
    private String payload; // Se puede serializar JSON aqui
}
