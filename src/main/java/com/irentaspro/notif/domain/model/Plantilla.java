package com.irentaspro.notif.domain.model;

public class Plantilla {
    private String nombre;
    private String contenidoHTML;

    public Plantilla(String nombre, String contenidoHTML) {
        this.nombre = nombre;
        this.contenidoHTML = contenidoHTML;
    }

    public String getNombre() {
        return nombre;
    }

    public String getContenidoHTML() {
        return contenidoHTML;
    }

    public String renderizar(String... datos) {
        String resultado = contenidoHTML;
        for (int i = 0; i < datos.length; i++) {
            resultado = resultado.replace("{{" + i + "}}", datos[i]);
        }
        return resultado;
    }
}
