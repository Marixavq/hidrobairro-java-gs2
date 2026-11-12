package com.fiap.gs_hidrobairro.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record LeituraRequestDTO(

        @NotNull(message = "O ponto de monitoramento é obrigatório")
        @Schema(
                description = "ID do ponto de monitoramento associado à leitura")
        Long pontoId,

        @NotNull(message = "O nível da água é obrigatório")
        @DecimalMin(value = "0.0", message = "O nível da água não pode ser negativo")
        @Schema(
                description = "Nível da água em centímetros",
                example = "45.50")
        BigDecimal nivelCm,

        @NotBlank(message = "A origem da leitura é obrigatória")
        @Size(max = 20, message = "A origem deve ter no máximo 20 caracteres")
        @Schema(
                description = "Origem da leitura: IOT ou SIMULADO", example = "IOT")
        String origem

) {
}
