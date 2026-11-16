package com.fiap.gs_hidrobairro.dto.iot;

import java.math.BigDecimal;

public record IoTLeituraRequestDTO(
        BigDecimal distanciaCm,
        String data,
        String horario,
        Boolean leituraValida
) {}