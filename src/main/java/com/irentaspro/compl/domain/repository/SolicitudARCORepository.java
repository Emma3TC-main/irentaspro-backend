package com.irentaspro.compl.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.irentaspro.compl.domain.model.*;

public interface SolicitudARCORepository {
    SolicitudARCO guardar(SolicitudARCO s);

    Optional<SolicitudARCO> buscarPorId(UUID id);

    List<SolicitudARCO> buscarPorUsuario(UUID usuarioId);

    List<SolicitudARCO> buscarTodos();

}