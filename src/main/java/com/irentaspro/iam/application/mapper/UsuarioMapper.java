package com.irentaspro.iam.application.mapper;

import org.springframework.stereotype.Component;

import com.irentaspro.iam.application.dto.UsuarioDTO;
import com.irentaspro.iam.domain.model.Usuario;
import com.irentaspro.iam.domain.model.valueobject.Email;

@Component
public class UsuarioMapper {
    public UsuarioDTO toDto(Usuario usuario) {
        return UsuarioDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail().getValor())
                .tipoCuenta(usuario.getTipoCuenta())
                .build();
    }

    public Usuario toDomain(UsuarioDTO dto) {
        return new Usuario(dto.getNombre(), new Email(dto.getEmail()), null);
    }
}
