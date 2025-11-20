package com.irentaspro.common.domain.model;

import java.util.UUID;

public abstract class Entidad {
    protected UUID id = UUID.randomUUID();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}
