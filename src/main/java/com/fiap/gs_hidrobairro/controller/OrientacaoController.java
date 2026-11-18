
package com.fiap.gs_hidrobairro.controller;

import com.fiap.gs_hidrobairro.dto.ia.OrientacaoRequestDTO;
import com.fiap.gs_hidrobairro.dto.ia.OrientacaoResponseDTO;
import com.fiap.gs_hidrobairro.service.OrientacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ia/orientacoes")
@Tag(name = "Orientações", description = "Gera orientações aos usuários com base nos dados armazenados")
public class OrientacaoController {

    private final OrientacaoService orientacaoService;

    public OrientacaoController(OrientacaoService orientacaoService) {
        this.orientacaoService = orientacaoService;
    }

    @PostMapping
    @Operation(
            summary = "Gerar orientação preventiva",
            description = "Gera uma orientação com auxílio de IA, utilizando os dados de monitoramento armazenados no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Orientação gerada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Pergunta inválida"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro ao gerar orientação"
            )
    })
    public ResponseEntity<OrientacaoResponseDTO> gerarOrientacao(
            @Valid @RequestBody OrientacaoRequestDTO requestDTO) {

        String orientacao = orientacaoService.gerarOrientacao(requestDTO.pergunta());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new OrientacaoResponseDTO(orientacao));
    }
}
