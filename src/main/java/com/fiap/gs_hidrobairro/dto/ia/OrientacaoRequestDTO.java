package com.fiap.gs_hidrobairro.dto.ia;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record OrientacaoRequestDTO(

        @NotBlank(message = "A pergunta é obrigatória")
        @Size(max = 500, message = "A pergunta deve ter no máximo 500 caracteres")
        String pergunta

) {}
