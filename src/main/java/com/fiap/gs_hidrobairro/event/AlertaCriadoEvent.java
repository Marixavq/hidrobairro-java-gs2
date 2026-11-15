package com.fiap.gs_hidrobairro.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AlertaCriadoEvent(
        Long alertaId,
        Long pontoId,
        String nomePonto,
        String bairro,
        String subprefeitura,
        BigDecimal nivelCm,
        String severidade,
        String mensagem,
        LocalDateTime criadoEm
) {
}
