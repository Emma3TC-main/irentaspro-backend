package com.irentaspro.pay.infrastructure.entity;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pagos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagoEntity {

    @Id
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = true)
    private UUID contratoId;

    @Column(nullable = true)
    private UUID usuarioId;

    @Column(nullable = false)
    private BigDecimal monto;

    @Column(nullable = false)
    private String moneda;

    private String metodo;
    private String tipoPago;

    @Column(nullable = false)
    private String estado;
    private String referenciaExterna;

    @OneToOne(cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "comprobante_id", referencedColumnName = "id")
    private ComprobanteFiscalEntity comprobanteFiscal;
}
