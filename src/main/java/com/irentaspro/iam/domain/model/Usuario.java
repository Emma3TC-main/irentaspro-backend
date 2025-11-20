package com.irentaspro.iam.domain.model;

import com.irentaspro.common.domain.model.AggregateRoot;
import com.irentaspro.iam.domain.model.valueobject.Email;
import com.irentaspro.iam.domain.model.valueobject.PasswordHash;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Usuario extends AggregateRoot {
    private String nombre;
    private Email email;
    private PasswordHash passwordHash;
    private String tipoCuenta; // "free" o "premium"
    private LocalDate fechaVencimiento;
    private List<Rol> roles = new ArrayList<>();
    private List<Sesion> sesiones = new ArrayList<>();

    public Usuario(String nombre, Email email, PasswordHash passwordHash) {
        this.nombre = nombre;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public Usuario(UUID id, String nombre, Email email, PasswordHash passwordHash, String tipoCuenta,
            LocalDate fechaVencimiento) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.passwordHash = passwordHash;
        this.tipoCuenta = tipoCuenta;
        this.fechaVencimiento = fechaVencimiento;
    }

    /**
     * 
     * Constructor protegido vacío para frameworks de persistencia
     * 
     */

    protected Usuario() {
    }

    // Métodos de dominio y lógica de negocio aquí
    public boolean autenticar(String credenciales) {
        // Lógica de autenticación
       
        //Aplicando TDD para el método -> validarCredencialesIncorrectas
        if (credenciales == null ) {
            return false;
        }
        if (credenciales.isEmpty()) {
            return false;
        }

        return true; // Ejemplo simplificado
    }


    //Aplicando TDD para el test -> cambiarPassword
    public void cambiarPassword(PasswordHash nuevaPassword) {
        // Lógica para cambiar la contraseña

        //momentandeo simple para justificar TDD
        this.passwordHash = nuevaPassword;
        if(nuevaPassword == null) {
            throw new IllegalArgumentException("La nueva contraseña no puede ser nula.");
        }
        
    public boolean autenticar(String password) {
        if (!passwordHash.verificar(password)) {
            throw new IllegalArgumentException("Contraseña incorrecta");
        }
        return true;
    }

    public void cambiarPassword(String nuevaPassword) {
        this.passwordHash = PasswordHash.crearDesdeTexto(nuevaPassword);
    }

    public void agregarRol(Rol rol) {
        if (!roles.contains(rol)) {
            roles.add(rol);
        }
    }

    public void agregarSesion(Sesion sesion) {
        this.sesiones.add(sesion);
    }

    public boolean esPremium() {
        //Aplicando TDD, correción ortografía
        // para justificar el tdd xd
        return "premium".equalsIgnoreCase(this.tipoCuenta);
    }

    //Modificación por TDD  Test -> generarToken
    public String generarToken() {
        // Lógica pendiente
        //momentandeo simple para justificar TDD, esto se debe usar con UUID
        return UUID.randomUUID().toString();
    }
        

    @Override
    public void validarInvariantes() {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalStateException("El nombre es obligatorio.");
        }
        if (email == null) {
            throw new IllegalStateException("El email es obligatorio.");
        }
        if (passwordHash == null) {
            throw new IllegalStateException("El password es obligatorio.");
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

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

}
