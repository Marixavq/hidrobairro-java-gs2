package com.fiap.gs_hidrobairro.controller;

import com.fiap.gs_hidrobairro.dto.iot.IoTLeituraRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/iot")
public class IoTController {

    private static final Logger log = LoggerFactory.getLogger(IoTController.class);

    @PostMapping("/leituras")
    public ResponseEntity<String> receberLeitura(@RequestBody IoTLeituraRequestDTO leitura) {

        log.info("Leitura recebida do ESP32: {}", leitura);

        return ResponseEntity.ok("Dados do sensor recebidos com sucesso!");
    }
}