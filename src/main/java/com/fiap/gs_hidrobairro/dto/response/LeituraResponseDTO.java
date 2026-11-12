package com.fiap.gs_hidrobairro.dto.response;

import com.fiap.gs_hidrobairro.model.Leitura;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record LeituraResponseDTO(

        Long id,
        Long pontoId,
        String pontoNome,
        BigDecimal nivelCm,
        LocalDateTime coletadoEm,
        String origem
) {

    public static LeituraResponseDTO fromEntity(Leitura leitura) {
        return new LeituraResponseDTO(
                leitura.getId(),
                leitura.getPonto().getId(),
                leitura.getPonto().getNome(),
                leitura.getNivelCm(),
                leitura.getColetadoEm(),
                leitura.getOrigem()
        );
    }
}