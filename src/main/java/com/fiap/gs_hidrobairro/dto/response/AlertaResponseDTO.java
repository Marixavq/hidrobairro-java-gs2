package com.fiap.gs_hidrobairro.dto.response;

import com.fiap.gs_hidrobairro.model.Alerta;
import com.fiap.gs_hidrobairro.model.SeveridadeAlerta;
import com.fiap.gs_hidrobairro.model.StatusAlerta;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AlertaResponseDTO(

        Long id,
        Long pontoId,
        String nomePonto,
        Long leituraId,
        BigDecimal nivelCm,
        SeveridadeAlerta severidade,
        String mensagem,
        StatusAlerta status,
        LocalDateTime criadoEm,
        LocalDateTime encerradoEm

) {

    public static AlertaResponseDTO fromEntity(Alerta alerta) {
        return new AlertaResponseDTO(
                alerta.getId(),
                alerta.getPonto().getId(),
                alerta.getPonto().getNome(),
                alerta.getLeitura() != null ? alerta.getLeitura().getId() : null,
                alerta.getNivelCm(),
                alerta.getSeveridade(),
                alerta.getMensagem(),
                alerta.getStatus(),
                alerta.getCriadoEm(),
                alerta.getEncerradoEm()
        );
    }
}