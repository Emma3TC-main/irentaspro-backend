package com.irentaspro.compl.infrastructure.persistence.jpa;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.irentaspro.compl.infrastructure.persistence.entity.SolicitudARCOEntity;

public interface JpaSolicitudARCORepository extends JpaRepository<SolicitudARCOEntity, UUID> {
    List<SolicitudARCOEntity> findByUsuario_Id(UUID usuarioId);
}
