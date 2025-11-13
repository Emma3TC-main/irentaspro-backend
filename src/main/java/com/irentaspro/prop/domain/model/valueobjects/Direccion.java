package com.irentaspro.prop.domain.model.valueobjects;

public class Direccion {
    private final String calle;
    private final String distrito;
    private final String provincia;

    public Direccion(String calle, String distrito, String provincia) {
        //TDD
        if (calle == null || calle.isBlank()  ) {
            throw new IllegalArgumentException("el Campo de calle no puede estar vacio o nulo");
        }

        if(distrito == null || distrito.isBlank() ){
            throw new IllegalArgumentException("El campo de Distrito no puede estar vacio o nulo");          
        }

        if(provincia == null || provincia.isBlank()){
            throw new IllegalArgumentException("El campo de provincia no pude estar vacio o nulo");
        }

        this.calle = calle;
        this.distrito = distrito;
        this.provincia = provincia;
    }

    public String getCalle() {
        return calle;
    }

    public String getDistrito() {
        return distrito;
    }

    public String getProvincia() {
        return provincia;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s", calle, distrito, provincia);
    }
}
