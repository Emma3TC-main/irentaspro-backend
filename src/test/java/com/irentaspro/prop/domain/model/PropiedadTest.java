package com.irentaspro.prop.domain.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.irentaspro.prop.domain.model.valueobjects.HashDocumento;
import com.irentaspro.prop.domain.model.valueobjects.Precio;
import com.irentaspro.prop.domain.model.valueobjects.Ubicacion;

class PropiedadTest {
    private UUID ownerIdValido;
    private String tituloValido;
    private String descripcionValida;
    private String direccionValida;
    private Ubicacion ubicacionValida;
    private Precio precioValido;

    private Propiedad propiedadTest;

    @BeforeEach
    void setUp() {
        ownerIdValido = UUID.randomUUID();
        tituloValido = "Hermoso Departamento";
        descripcionValida = "Vista al parque";
        direccionValida = "Av. Siempre Viva 123";
        ubicacionValida = new Ubicacion(-12.0, -77.0, "Miraflores");
        precioValido = new Precio(new BigDecimal("1500.00"), "USD");

        propiedadTest = new Propiedad(
                ownerIdValido,
                tituloValido,
                descripcionValida,
                direccionValida,
                ubicacionValida,
                precioValido);
    }

    @Test
    void lanzarExceptionSiOwnerIdEsNulo() {
        assertThrows(IllegalStateException.class, () -> {
            new Propiedad(
                    null,
                    tituloValido,
                    descripcionValida,
                    direccionValida,
                    ubicacionValida,
                    precioValido);
        });
    }

    @Test
    void lanzarExceptionSiTituloEsNuloOVacio() {
        assertThrows(IllegalStateException.class, () -> {
            new Propiedad(
                    ownerIdValido,
                    null,
                    descripcionValida,
                    direccionValida,
                    ubicacionValida,
                    precioValido);
        });

        assertThrows(IllegalStateException.class, () -> {
            new Propiedad(
                    ownerIdValido,
                    "  ",
                    descripcionValida,
                    direccionValida,
                    ubicacionValida,
                    precioValido);
        });
    }

    @Test
    void lanzarExceptionSiPrecioEsNulo() {

        assertThrows(IllegalStateException.class, () -> {
            new Propiedad(
                    ownerIdValido,
                    tituloValido,
                    descripcionValida,
                    direccionValida,
                    ubicacionValida,
                    null);
        });
    }

    @Test
    void debeCrearPropiedadConDatosValidos() {

        Propiedad propiedadTest1 = assertDoesNotThrow(() -> {
            return propiedadTest;
        });
        assertNotNull(propiedadTest1);
        assertNotNull(propiedadTest.getId());
        assertEquals(ownerIdValido, propiedadTest.getOwnerId());
        assertEquals(tituloValido, propiedadTest.getTitulo());
        assertEquals(descripcionValida, propiedadTest.getDescripcion());
        assertEquals(direccionValida, propiedadTest.getDireccion());
        assertEquals(ubicacionValida, propiedadTest.getUbicacion());
        assertEquals(precioValido, propiedadTest.getPrecio());
        assertNotNull(propiedadTest.getDocumentos());
        assertTrue(propiedadTest.getDocumentos().isEmpty());
    }

    @Test
    void debeAgregarDocumentoALaLista() {

        DocumentoPropiedad doc = new DocumentoPropiedad(
                "Foto", "http://foto.com/1.jpg", new HashDocumento("hash1", "SHA-256"));
        assertEquals(0, propiedadTest.getDocumentos().size());

        propiedadTest.agregarDocumento(doc);

        assertEquals(1, propiedadTest.getDocumentos().size());
        assertEquals(doc, propiedadTest.getDocumentos().get(0));
    }

    @Test
    void debeIgnorarSiDocumentoEsNulo() {
        assertEquals(0, propiedadTest.getDocumentos().size());

        propiedadTest.agregarDocumento(null);

        assertEquals(0, propiedadTest.getDocumentos().size());
    }

    @Test
    void debeActualizarDatosPropiedad() {

        String nuevoTitulo = "Título Actualizado";
        String nuevaDescripcion = "Descripción Nueva";
        Precio nuevoPrecio = new Precio(new BigDecimal("2000.00"), "PEN");

        propiedadTest.actualizar(nuevoTitulo, nuevaDescripcion, nuevoPrecio);

        assertEquals(nuevoTitulo, propiedadTest.getTitulo());
        assertEquals(nuevaDescripcion, propiedadTest.getDescripcion());
        assertEquals(nuevoPrecio, propiedadTest.getPrecio());
    }

    @Test
    void lanzarExceptionSiActualizaConTituloVacio() {

        assertThrows(IllegalStateException.class, () -> {
            propiedadTest.actualizar(
                    "",
                    "Nueva Desc",
                    precioValido);
        });
    }

    @Test
    void publicarNoDebeLanzarExcepcion() {

        assertDoesNotThrow(() -> {
            propiedadTest.publicar();
        });
    }
}
