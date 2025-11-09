package com.irentaspro.iam.domain.repository;

import java.util.Optional;

import com.irentaspro.common.domain.model.Repositorio;
import com.irentaspro.iam.domain.model.Usuario;

public interface IAuthRepositorio extends Repositorio<Usuario> {
    Optional<Usuario> buscarPorEmail(String email);
}
