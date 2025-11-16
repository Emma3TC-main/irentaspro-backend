package com.irentaspro.iam.domain.model.valueobject;

import org.aspectj.weaver.ast.Test;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordHash {
    private final String valor;
    private final String algoritmo;

    public PasswordHash(String valor, String algoritmo) {
        
    //Codígo adicional para que pase el test
    //Test 1
    if (valor == null ) {
        throw new IllegalArgumentException("El valor del hash no puede ser nulo");
    }
    //Test 2
    if (algoritmo == null ) {
        throw new IllegalArgumentException("El algoritmo no puede ser nulo");
    }
    //Test 3
    if (valor.isEmpty() ) {
        throw new IllegalArgumentException("El valor del hash no puede estar vacío");
    }
    
        this.valor = valor;
        this.algoritmo = algoritmo;
    }
    

    public String getValor() {
        return valor;
    }

    public String getAlgoritmo() {
        return algoritmo;
    }

    // Método para crear un PasswordHash desde texto plano
    public static PasswordHash crearDesdeTexto(String textoPlano) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(textoPlano.getBytes());
            String hashBase64 = Base64.getEncoder().encodeToString(hashBytes);
            return new PasswordHash(hashBase64, "SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al crear el hash de la contraseña", e);
        }
    }

    // Método para verificar si un texto plano coincide con el hash
    public boolean verificar(String textoPlano) {
        PasswordHash otroHash = crearDesdeTexto(textoPlano);
        return this.valor.equals(otroHash.getValor());
    }
}
