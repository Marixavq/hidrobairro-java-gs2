package com.fiap.gs_hidrobairro.dto.ia;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record OrientacaoRequestDTO(

        @NotBlank(message = "A pergunta é obrigatória")
        @Size(max = 500, message = "A pergunta deve ter no máximo 500 caracteres")
        @Schema(
                description = "Pergunta do usuário a IA",
                example = "Posso ir ao Terminal Campo Limpo?")
        String pergunta

) {}
