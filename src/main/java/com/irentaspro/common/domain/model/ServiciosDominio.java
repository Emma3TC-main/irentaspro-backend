package com.irentaspro.common.domain.model;

/**
 * Interfaz base para los servicios de dominio.
 * Un Servicio de Dominio encapsula lógica de negocio que
 * no pertenece naturalmente a una sola Entidad o Agregado.
 */
public interface ServiciosDominio {

    /**
     * Método genérico de ejecución. Su implementación puede variar según el
     * contexto.
     */
    void ejecutar();
}
