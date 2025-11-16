package com.irentaspro.notif.domain.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SchedulerTest {

    private Scheduler scheduler;

    @BeforeEach
    void setUp() {
        scheduler = new Scheduler();
    }
    @Test
    void lanzaExceptionSiTareaEsNula() {
        LocalDateTime fechaValida = LocalDateTime.now().plusDays(1);
        Runnable tareaNula = null;

        assertThrows(IllegalArgumentException.class, () -> {
            scheduler.programar(fechaValida, tareaNula);
        });
    }
    
    
    @Test
    void ejecutarLanzaExceptionSiTareaEsNula() {
        Runnable tareaNula = null;

        assertThrows(IllegalArgumentException.class, () -> {
            scheduler.ejecutarTareaProgramada(tareaNula);
        });
    }

    @Test
    void programarLanzaExceptionSiFechaEsNula() {
        LocalDateTime fechaNula = null;
        Runnable tareaValida = () -> {};

        assertThrows(IllegalArgumentException.class, () -> {
            scheduler.programar(fechaNula, tareaValida);
        });
    }

    @Test
    void debeProgramarTareaCorrectamente() {
        LocalDateTime fechaValida = LocalDateTime.now().plusDays(1);
        Runnable tareaValida = () -> { }; 
        assertDoesNotThrow(() -> {
            scheduler.programar(fechaValida, tareaValida);
        });
    }

    @Test
    void debeEjecutarLaTareaRunnable() {
        final AtomicBoolean tareaFueEjecutada = new AtomicBoolean(false);

        Runnable miTarea = () -> {
            tareaFueEjecutada.set(true);
        };

        assertFalse(tareaFueEjecutada.get(), "La bandera debe ser 'false' antes de actuar");

        scheduler.ejecutarTareaProgramada(miTarea);

        assertTrue(tareaFueEjecutada.get(), "El método debió ejecutar la tarea y cambiar la bandera");
    }
}
