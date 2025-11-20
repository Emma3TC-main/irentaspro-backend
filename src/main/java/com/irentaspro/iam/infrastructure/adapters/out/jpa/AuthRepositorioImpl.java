package com.irentaspro.iam.infrastructure.adapters.out.jpa;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;

import com.irentaspro.bi.domain.model.Reporte;
import com.irentaspro.iam.domain.model.Usuario;
import com.irentaspro.iam.domain.model.valueobject.Email;
import com.irentaspro.iam.domain.model.valueobject.PasswordHash;
import com.irentaspro.iam.domain.repository.IAuthRepositorio;
import com.irentaspro.iam.infrastructure.entity.UsuarioEntity;
import com.irentaspro.iam.infrastructure.repository.UsuarioJpaRepository;

@Repository
@RequiredArgsConstructor
public class AuthRepositorioImpl implements IAuthRepositorio {

    private final UsuarioJpaRepository jpaRepository;

    @Override
    public Usuario guardar(Usuario usuario) {
        UsuarioEntity entity = UsuarioEntity.builder()
                .nombre(usuario.getNombre())
                .email(usuario.getEmail().getValor())
                .passwordHash(usuario.getPasswordHash().getValor())
                .tipoCuenta(usuario.getTipoCuenta())
                .fechaVencimiento(usuario.getFechaVencimiento())
                .build();

        jpaRepository.save(entity);
        usuario.setId(entity.getId());
        return usuario;
    }

    @Override
    public void eliminar(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return jpaRepository.findByEmail(email).map(this::toDomain);
    }

    private Usuario toDomain(UsuarioEntity entity) {
        return new Usuario(
                entity.getNombre(),
                new Email(entity.getEmail()),
                new PasswordHash(entity.getPasswordHash(), null));
    }

    @Override
    public Reporte buscarPorId(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarPorId'");
    }
}