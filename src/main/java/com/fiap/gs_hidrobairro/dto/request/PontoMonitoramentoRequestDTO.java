package com.fiap.gs_hidrobairro.dto.request;

import com.fiap.gs_hidrobairro.model.Zona;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record PontoMonitoramentoRequestDTO(

        @NotBlank(message = "O nome do ponto é obrigatório")
        @Size(min = 3, max = 120, message = "O nome deve ter entre 3 e 120 caracteres")
        @Schema(
                description = "Nome do ponto de monitoramento",
                example = "Terminal Campo Limpo")
        String nome,

        @NotBlank(message = "A subprefeitura é obrigatória")
        @Size(max = 80, message = "A subprefeitura deve ter no máximo 80 caracteres")
        @Schema(
                description = "Subprefeitura onde o ponto está localizado",
                example = "Campo Limpo")
        String subprefeitura,

        @NotBlank(message = "O bairro é obrigatório")
        @Size(max = 80, message = "O bairro deve ter no máximo 80 caracteres")
        @Schema(
                description = "Bairro do ponto de monitoramento",
                example = "Campo Limpo")
        String bairro,

        @NotNull(message = "A zona é obrigatória")
        @Schema(
                description = "Zona da cidade",
                example = "SUL")
        Zona zona,

        @NotNull(message = "A latitude é obrigatória")
        @DecimalMin(value = "-90.0", message = "A latitude mínima é -90")
        @DecimalMax(value = "90.0", message = "A latitude máxima é 90")
        @Schema(
                description = "Latitude geográfica",
                example = "-23.6485")
        BigDecimal latitude,

        @NotNull(message = "A longitude é obrigatória")
        @DecimalMin(value = "-180.0", message = "A longitude mínima é -180")
        @DecimalMax(value = "180.0", message = "A longitude máxima é 180")
        @Schema(
                description = "Longitude geográfica",
                example = "-46.7652")
        BigDecimal longitude,

        @NotNull(message = "O limiar de alerta é obrigatório")
        @DecimalMin(value = "0.0", inclusive = false, message = "O limiar de alerta deve ser maior que zero")
        @Schema(
                description = "Nível de água necessário para gerar alerta, em centímetros",
                example = "50.00")
        BigDecimal limiarAlertaCm

) {
}
