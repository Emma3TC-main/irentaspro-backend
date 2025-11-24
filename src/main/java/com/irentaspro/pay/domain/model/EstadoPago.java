package com.irentaspro.pay.domain.model;

public enum EstadoPago {
    PENDIENTE, // creado pero no iniciado en PSP (checkout aún no completado)
    REGISTRADO, // creado en el sistema local (pre-checkout)
    CONFIRMADO, // PSP confirmó el pago (ej. webhook / capture)
    CONCILIADO, // referencia comprobada y conciliada con transacción PSP
    CANCELADO
}
