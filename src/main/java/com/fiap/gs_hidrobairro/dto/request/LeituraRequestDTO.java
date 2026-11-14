package com.fiap.gs_hidrobairro.dto.request;

import com.fiap.gs_hidrobairro.model.OrigemLeitura;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record LeituraRequestDTO(

        @NotNull(message = "O ponto de monitoramento é obrigatório")
        @Schema(
                description = "ID do ponto de monitoramento associado à leitura",
                example = "1")
        Long pontoId,

        @NotNull(message = "O nível da água é obrigatório")
        @DecimalMin(value = "0.0", message = "O nível da água não pode ser negativo")
        @Schema(
                description = "Nível da água em centímetros",
                example = "45.50")
        BigDecimal nivelCm,

        @NotNull(message = "A origem da leitura é obrigatória")
        @Schema(
                description = "Origem da leitura: IOT ou SIMULADO",
                example = "SIMULADO")
        OrigemLeitura origem
) {
}
