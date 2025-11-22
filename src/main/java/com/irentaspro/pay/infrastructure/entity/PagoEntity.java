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
    @GeneratedValue(strategy = GenerationType.UUID)
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
