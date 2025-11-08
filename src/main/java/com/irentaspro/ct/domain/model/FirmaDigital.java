package com.irentaspro.ct.domain.model;

import java.time.LocalDateTime;

import com.irentaspro.common.domain.model.valueobjects.HashDocumento;



public class FirmaDigital {
    private final String proveedor;
    private final String certificado;
    private final HashDocumento hashDocumento;
    private final LocalDateTime selloTiempo;

    public FirmaDigital(String proveedor, String certificado, HashDocumento hashDocumento, LocalDateTime selloTiempo) {
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
