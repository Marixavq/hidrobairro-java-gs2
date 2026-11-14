package com.fiap.gs_hidrobairro.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email inválido")
        @Schema(
                description = "Email do usuário",
                example = "joao@email.com"
        )
        String email,

        @NotBlank(message = "Senha é obrigatória")
        @Schema(
                description = "Senha do usuário",
                example = "123456"
        )
        String senha

) {
}