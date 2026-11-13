package com.fiap.gs_hidrobairro.dto.response;

import com.fiap.gs_hidrobairro.model.Alerta;
import com.fiap.gs_hidrobairro.model.SeveridadeAlerta;

import java.math.BigDecimal;

public record AlertaResponseDTO(

        Long id,
        Long pontoId,
        Long leituraId,
        BigDecimal nivelCm,
        SeveridadeAlerta severidade,
        String mensagem

) {

    public static AlertaResponseDTO fromEntity(Alerta alerta) {
        return new AlertaResponseDTO(
                alerta.getId(),
                alerta.getPonto().getId(),
                alerta.getLeitura().getId(),
                alerta.getNivelCm(),
                alerta.getSeveridade(),
                alerta.getMensagem()
        );
    }
}