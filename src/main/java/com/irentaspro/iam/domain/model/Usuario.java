package com.irentaspro.iam.domain.model;

import com.irentaspro.common.domain.model.AggregateRoot;
import com.irentaspro.iam.domain.model.valueobject.Email;
import com.irentaspro.iam.domain.model.valueobject.PasswordHash;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Usuario extends AggregateRoot {
    private String nombre;
    private Email email;
    private PasswordHash passwordHash;
    private String tipoCuenta;
    private Date fechaVencimiento;
    private List<Rol> roles = new ArrayList<>();
    private List<Sesion> sesiones = new ArrayList<>();

    // Falta agregar inyección de dependencias y usar builder para crear instancias

    // Métodos de dominio y lógica de negocio aquí
    public boolean autenticar(String credenciales) {
        // Lógica de autenticación
        return true; // Ejemplo simplificado
    }

    public void cambiarPassword(String nuevaPassword) {
        // Lógica para cambiar la contraseña
    }

    public boolean esPremium() {
        return "premiun".equalsIgnoreCase(this.tipoCuenta);
    }

    public String generarToken() {
        return null;
        // Lógica pendiente
    }

    @Override
    public void validarInvariantes() {
        // Validaciones de invariantes del agregado Usuario
        if (nombre == null || email == null) {
            throw new IllegalStateException("El nombre y el email son obligatorios.");
        }
    }

    // Getters y Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public PasswordHash getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(PasswordHash passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    public List<Sesion> getSesiones() {
        return sesiones;
    }

    public void setSesiones(List<Sesion> sesiones) {
        this.sesiones = sesiones;
    }

}
