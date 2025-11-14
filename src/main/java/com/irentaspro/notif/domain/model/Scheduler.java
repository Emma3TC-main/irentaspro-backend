package com.irentaspro.notif.domain.model;

import java.time.LocalDateTime;

public class Scheduler {
    public void programar(LocalDateTime fechaEjecucion, Runnable tarea) {
        
        if (fechaEjecucion == null) {
            throw new IllegalArgumentException("La fecha de ejecución no puede ser nula.");
        }
        if (tarea == null) {
            throw new IllegalArgumentException("La tarea a programar no puede ser nula.");
        }
        System.out.println("⏳ Tarea programada para: " + fechaEjecucion);
        // En una implementación real, se usaría un scheduler como Quartz o Spring Task
    }

    public void ejecutarTareaProgramada(Runnable tarea) {
        if (tarea == null) {
            throw new IllegalArgumentException("La tarea a ejecutar no puede ser nula.");
        }
        System.out.println("⚙️ Ejecutando tarea programada...");
        tarea.run();
    }
}
