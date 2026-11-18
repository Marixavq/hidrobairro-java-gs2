package com.fiap.gs_hidrobairro.controller;

import com.fiap.gs_hidrobairro.dto.iot.IoTLeituraRequestDTO;
import com.fiap.gs_hidrobairro.dto.response.LeituraResponseDTO;
import com.fiap.gs_hidrobairro.service.IoTService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/iot")
@Tag(name = "IoT", description = "Recebe e processa leituras dos sensores ESP32")
public class IoTController {

    private static final Logger log = LoggerFactory.getLogger(IoTController.class);

    private final IoTService ioTService;

    public IoTController(IoTService ioTService) {
        this.ioTService = ioTService;
    }

    @PostMapping("/leituras")
    @Operation(
            summary = "Receber leitura do sensor",
            description = "Recebe uma leitura enviada pelo ESP32 e encaminha para processamento."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Leitura recebida e processada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados da leitura inválidos"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ponto de monitoramento não encontrado"
            )
    })
    public ResponseEntity<LeituraResponseDTO> receberLeitura(@Valid @RequestParam Long pontoId, @RequestBody IoTLeituraRequestDTO leitura) {

        log.info("Leitura recebida do ESP32: {}", leitura);

        LeituraResponseDTO resposta = ioTService.processarLeitura(pontoId, leitura);

        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }
}
