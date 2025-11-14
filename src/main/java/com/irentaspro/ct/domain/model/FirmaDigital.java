package com.irentaspro.ct.domain.model;

import java.time.LocalDateTime;

import com.irentaspro.common.domain.model.valueobjects.HashDocumento;



public class FirmaDigital {
    private final String proveedor;
    private final String certificado;
    private final HashDocumento hashDocumento;
    private final LocalDateTime selloTiempo;

    public FirmaDigital(String proveedor, String certificado, HashDocumento hashDocumento, LocalDateTime selloTiempo) {
        if (proveedor == null || proveedor.isBlank()) {
            throw new IllegalArgumentException("El 'proveedor' de la firma no puede ser nulo o vacío.");
        }
        if (certificado == null || certificado.isEmpty()) { // Un certificado puede ser " ", pero no ""
            throw new IllegalArgumentException("El 'certificado' de la firma no puede ser nulo o vacío.");
        }
        if (hashDocumento == null) {
            throw new IllegalArgumentException("El 'hashDocumento' de la firma no puede ser nulo.");
        }
        if (selloTiempo == null) {
            throw new IllegalArgumentException("El 'selloTiempo' (timestamp) de la firma no puede ser nulo.");
        }
        
        this.proveedor = proveedor;
        this.certificado = certificado;
        this.hashDocumento = hashDocumento;
        this.selloTiempo = selloTiempo;
    }

    public String getProveedor() {
        return proveedor;
    }

    public String getCertificado() {
        return certificado;
    }

    public HashDocumento getHashDocumento() {
        return hashDocumento;
    }

    public LocalDateTime getSelloTiempo() {
        return selloTiempo;
    }

}
