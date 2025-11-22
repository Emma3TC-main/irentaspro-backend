package com.irentaspro.prop.application.dto;

import lombok.Data;

@Data
public class DocumentoRequest {
    private String tipo;
    private String hashValor;
    private String hashAlgoritmo;
}