package com.irentaspro.notif.domain.model;

import java.time.LocalDateTime;

public class Scheduler {
    public void programar(LocalDateTime fechaEjecucion, Runnable tarea) {
        System.out.println("⏳ Tarea programada para: " + fechaEjecucion);
        // En una implementación real, se usaría un scheduler como Quartz o Spring Task
    }

    public void ejecutarTareaProgramada(Runnable tarea) {
        System.out.println("⚙️ Ejecutando tarea programada...");
        tarea.run();
    }
}
