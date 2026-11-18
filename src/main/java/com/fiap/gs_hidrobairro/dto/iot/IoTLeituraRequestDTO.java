package com.fiap.gs_hidrobairro.dto.iot;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record IoTLeituraRequestDTO(

        @NotNull(message = "A distância é obrigatória")
        @DecimalMin(value = "0.01", message = "A distância deve ser maior que zero")
        @Schema(description = "Distância entre o sensor e a superfície da água, em centímetros",
                example = "362.14")
        BigDecimal distanciaCm,

        @NotBlank(message = "A data é obrigatória")
        @Schema(description = "Data da medição no formato yyyy-MM-dd",
                example = "2026-11-18")
        String data,

        @NotBlank(message = "O horário é obrigatório")
        @Schema(description = "Horário da medição no formato HH:mm:ss",
                example = "8:20:49")
        String horario,

        @NotNull(message = "A validade da leitura deve ser informada")
        @Schema(description = "Indica se a leitura realizada pelo sensor é válida",
                example = "true")
        Boolean leituraValida
) {}

