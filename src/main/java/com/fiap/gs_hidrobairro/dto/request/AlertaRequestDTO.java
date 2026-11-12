package com.fiap.gs_hidrobairro.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record AlertaRequestDTO(

        @NotNull
        @Schema(
                description = "ID do ponto de monitoramento associado")
        Long pontoId,

        @Schema(
                description = "ID da leitura associada ao alerta")
        Long leituraId,

        @NotNull
        @DecimalMin("0.0")
        BigDecimal nivelCm,

        @NotBlank
        @Size(max = 20)
        String severidade,

        @NotBlank
        @Size(max = 400)
        String mensagem
) {

}
