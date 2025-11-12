package com.irentaspro.pay.infrastructure.entity;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pagos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagoEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID contratoId;

    @Column(nullable = false)
    private UUID usuarioId;

    private BigDecimal monto;
    private String moneda;
    private String metodo;
    private String tipoPago;
    private String estado;
    private String referenciaExterna;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "comprobante_id", referencedColumnName = "id")
    private ComprobanteFiscalEntity comprobanteFiscal;
}
