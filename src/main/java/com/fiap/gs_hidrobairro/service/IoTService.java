package com.fiap.gs_hidrobairro.service;


import com.fiap.gs_hidrobairro.dto.iot.IoTLeituraRequestDTO;
import com.fiap.gs_hidrobairro.dto.request.LeituraRequestDTO;
import com.fiap.gs_hidrobairro.dto.response.LeituraResponseDTO;
import com.fiap.gs_hidrobairro.exception.RegraNegocioException;
import com.fiap.gs_hidrobairro.model.enums.OrigemLeitura;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class IoTService {

    private static final BigDecimal ALTURA_REFERENCIA_CM = new BigDecimal("400.00");

    private final LeituraService leituraService;

    public IoTService(LeituraService leituraService) {
        this.leituraService = leituraService;
    }

    public LeituraResponseDTO processarLeitura( Long pontoId, IoTLeituraRequestDTO leitura) {
        if (leitura.leituraValida() == null || !leitura.leituraValida()) {
            throw new RegraNegocioException("Leitura do sensor inválida");
        }

        if (leitura.distanciaCm() == null || leitura.distanciaCm().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraNegocioException("Distância inválida");
        }

        BigDecimal nivelCm = ALTURA_REFERENCIA_CM.subtract(leitura.distanciaCm());

        if (nivelCm.compareTo(BigDecimal.ZERO) < 0) {
            throw new RegraNegocioException("Distância maior que a altura de referência");
        }

        LeituraRequestDTO request = new LeituraRequestDTO(
                pontoId,
                nivelCm,
                OrigemLeitura.IOT
        );

        return leituraService.salvar(request);
    }
}
