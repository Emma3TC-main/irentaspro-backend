package com.irentaspro.bi.domain.model;

public class MaterializedViewsDW {
    private boolean actualizado;

    public void MaterializedViewsDW() {
        this.actualizado = false;
    }

    public void actualizar() {
        // Simulación de actualización de vistas materializadas del DW
        System.out.println("🔄 Actualizando vistas materializadas del Data Warehouse...");
        this.actualizado = true;
    }

    public boolean isActualizado() {
        return actualizado;
    }
}
