package com.irentaspro.iam.domain.repository;

import com.irentaspro.common.domain.model.Repositorio;
import com.irentaspro.iam.domain.model.Usuario;

public interface IAuthRepositorio extends Repositorio<Usuario> {
    Usuario buscarPorEmail(String email);
}
