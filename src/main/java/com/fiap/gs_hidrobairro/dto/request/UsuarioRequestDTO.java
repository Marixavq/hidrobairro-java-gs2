package com.fiap.gs_hidrobairro.dto.request;

import com.fiap.gs_hidrobairro.model.PapelUsuario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDTO(


        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 3, max = 120, message = "O nome deve ter entre 3 e 120 caracteres")
        @Schema(
                description = "Nome do usuário",
                example = "João Silva"
        )
        String nome,

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email inválido")
        @Size(max = 180, message = "O email deve ter no máximo 180 caracteres")
        @Schema(
                description = "Email do usuário",
                example = "joao@email.com"
        )
        String email,

        @NotBlank(message = "Senha obrigatória")
        @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
        @Schema(
                description = "Senha do usuário",
                example = "123456"
        )
        String senha,

        @NotNull(message = "Papel do usuário é obrigatório")
        @Schema(
                description = "Papel do usuário",
                example = "CIDADAO"
        )
        PapelUsuario papel
) {
}



