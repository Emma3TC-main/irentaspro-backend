package com.irentaspro.pay.application.command;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Comando de aplicación para crear un nuevo pago.
 * 
 * Forma parte de la capa de aplicación (CQRS).
 * 
 * Este comando encapsula los datos necesarios para registrar un pago,
 * sin exponer detalles del dominio interno.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrearPagoCommand {

    @NotNull(message = "El contratoId es obligatorio")
    private UUID contratoId;

    @NotNull(message = "El usuarioId es obligatorio")
    private UUID usuarioId;

    @NotNull(message = "El monto no puede ser nulo")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a cero")
    private BigDecimal monto;

    @NotBlank(message = "La moneda es obligatoria")
    private String moneda;

    @NotBlank(message = "El método de pago es obligatorio (por ejemplo, 'tarjeta', 'transferencia', etc.)")
    private String metodo;

    @NotBlank(message = "El tipo de pago es obligatorio (por ejemplo, 'contrato', 'membresia', etc.)")
    private String tipoPago;

    // Opcional: referencia externa del PSP (si el flujo inicia desde el PSP)
    private String referenciaExterna;
}
