package com.irentaspro.ct.infrastructure.adapters.out.jpa;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter @Setter
public class FirmaDigitalEmbeddable {

    @Column(name = "firma_proveedor")
    private String proveedor;

    @Column(name = "firma_certificado", length = 4000)
    private String certificado;

    @Column(name = "firma_hash_valor", length = 2000)
    private String hashValor;

    @Column(name = "firma_hash_algoritmo", length = 50)
    private String hashAlgoritmo;

    @Column(name = "firma_sello_tiempo")
    private LocalDateTime selloTiempo;

    // Constructor sin-args requerido por JPA
    public FirmaDigitalEmbeddable() {
    }

    // Constructor de conveniencia
    public FirmaDigitalEmbeddable(String proveedor, String certificado, String hashValor, String hashAlgoritmo,
            LocalDateTime selloTiempo) {
        this.proveedor = proveedor;
        this.certificado = certificado;
        this.hashValor = hashValor;
        this.hashAlgoritmo = hashAlgoritmo;
        this.selloTiempo = selloTiempo;
    }
}
