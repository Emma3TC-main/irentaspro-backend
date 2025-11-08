package com.irentaspro.iam.domain.model;

import java.util.Date;

import com.irentaspro.common.domain.model.Entidad;

public class Sesion extends Entidad {
    private String token;
    private Date expiracion;
    private boolean activa;

    // Getters y Setters
    public void invalidar() {
        this.activa = false;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiracion() {
        return expiracion;
    }

    public void setExpiracion(Date expiracion) {
        this.expiracion = expiracion;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }
}
