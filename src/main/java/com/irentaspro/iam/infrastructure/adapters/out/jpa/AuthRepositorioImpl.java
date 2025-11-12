package com.irentaspro.iam.infrastructure.adapters.out.jpa;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import com.irentaspro.iam.domain.model.Usuario;
import com.irentaspro.iam.domain.model.valueobject.Email;
import com.irentaspro.iam.domain.model.valueobject.PasswordHash;
import com.irentaspro.iam.domain.repository.IAuthRepositorio;
import com.irentaspro.iam.infrastructure.entity.UsuarioEntity;
import com.irentaspro.iam.infrastructure.repository.UsuarioJpaRepository;

@Repository
@Transactional
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
        return jpaRepository.findByEmail(email.trim().toLowerCase()) // 👈 normaliza email
                .map(this::toDomain);
    }

    private Usuario toDomain(UsuarioEntity entity) {
        Usuario usuario = new Usuario(
                entity.getNombre(),
                new Email(entity.getEmail()),
                new PasswordHash(entity.getPasswordHash(), null));

        usuario.setId(entity.getId());
        usuario.setTipoCuenta(entity.getTipoCuenta());
        usuario.setFechaVencimiento(entity.getFechaVencimiento());

        return usuario;
    }

    @Override
    public Optional<Usuario> buscarPorId(UUID id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Usuario> buscarTodos() {
        return jpaRepository.findAll()
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

}