package com.irentaspro.compl.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.irentaspro.compl.domain.model.*;

public interface ConsentimientoRepository {
    Consentimiento guardar(Consentimiento c);

    Optional<Consentimiento> buscarPorId(UUID id);

    Optional<Consentimiento> buscarPorUsuario(UUID usuarioId);
}