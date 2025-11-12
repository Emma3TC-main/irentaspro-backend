package com.irentaspro.ct.domain.model;

import java.time.LocalDateTime;
import com.irentaspro.common.domain.model.valueobjects.HashDocumento;

public class FirmaDigital {
    private final String proveedor;
    private final String certificado;
    private final HashDocumento hashDocumento;
    private final LocalDateTime selloTiempo;

    public FirmaDigital(String proveedor, String certificado, HashDocumento hashDocumento, LocalDateTime selloTiempo) {
        if (proveedor == null || proveedor.isBlank())
            throw new IllegalArgumentException("El proveedor de firma es obligatorio.");
        if (certificado == null || certificado.isBlank())
            throw new IllegalArgumentException("El certificado digital no puede estar vacío.");
        this.proveedor = proveedor;
        this.certificado = certificado;
        this.hashDocumento = hashDocumento;
        this.selloTiempo = selloTiempo != null ? selloTiempo : LocalDateTime.now();
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
