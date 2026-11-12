package com.fiap.gs_hidrobairro.dto.response;

import com.fiap.gs_hidrobairro.model.PontoMonitoramento;
import com.fiap.gs_hidrobairro.model.Zona;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PontoMonitoramentoResponseDTO(

        Long id,
        String nome,
        String subprefeitura,
        String bairro,
        Zona zona,
        BigDecimal latitude,
        BigDecimal longitude,
        BigDecimal limiarAlertaCm,
        char ativo,
        LocalDateTime criadoEm

) {

    public static PontoMonitoramentoResponseDTO fromEntity(PontoMonitoramento ponto) {
        return new PontoMonitoramentoResponseDTO(
                ponto.getId(),
                ponto.getNome(),
                ponto.getSubprefeitura(),
                ponto.getBairro(),
                ponto.getZona(),
                ponto.getLatitude(),
                ponto.getLongitude(),
                ponto.getLimiarAlertaCm(),
                ponto.getAtivo(),
                ponto.getCriadoEm()
        );
    }
}



