package com.irentaspro.iam.domain.model.valueobject;

import org.aspectj.weaver.ast.Test;

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

}
