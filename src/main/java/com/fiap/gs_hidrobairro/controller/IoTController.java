package com.fiap.gs_hidrobairro.controller;

import com.fiap.gs_hidrobairro.dto.iot.IoTLeituraRequestDTO;
import com.fiap.gs_hidrobairro.dto.response.LeituraResponseDTO;
import com.fiap.gs_hidrobairro.service.IoTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/iot")
public class IoTController {

    private static final Logger log = LoggerFactory.getLogger(IoTController.class);

    private final IoTService ioTService;

    public IoTController(IoTService ioTService) {
        this.ioTService = ioTService;
    }

    @PostMapping("/leituras")
    public ResponseEntity<LeituraResponseDTO> receberLeitura(@RequestParam Long pontoId, @RequestBody IoTLeituraRequestDTO leitura) {

        log.info("Leitura recebida do ESP32: {}", leitura);

        LeituraResponseDTO resposta = ioTService.processarLeitura(pontoId, leitura);

        return ResponseEntity.ok(resposta);
    }
}
