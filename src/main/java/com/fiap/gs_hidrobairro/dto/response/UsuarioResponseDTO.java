package com.fiap.gs_hidrobairro.dto.response;

import com.fiap.gs_hidrobairro.model.Usuario;
import com.fiap.gs_hidrobairro.model.enums.PapelUsuario;

import java.time.LocalDateTime;

public record UsuarioResponseDTO(

        Long id,
        String nome,
        String email,
        PapelUsuario papel,
        char ativo,
        LocalDateTime criadoEm
) {

    public static UsuarioResponseDTO fromEntity(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPapel(),
                usuario.getAtivo(),
                usuario.getCriadoEm()
        );
    }
}

