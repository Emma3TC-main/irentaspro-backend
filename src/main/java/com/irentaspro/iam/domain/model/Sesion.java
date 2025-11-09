package com.irentaspro.iam.domain.model;

import java.time.LocalDateTime;

public class Sesion {
    private String token;
    private LocalDateTime expiracion;
    private boolean activa = true;

    public Sesion(String token, LocalDateTime expiracion) {
        this.token = token;
        this.expiracion = expiracion;
        this.activa = true;
    }

    public void invalidar() {
        this.activa = false;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getExpiracion() {
        return expiracion;
    }

    public boolean isActiva() {
        return activa;
    }
}
