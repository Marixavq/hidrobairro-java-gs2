
package com.fiap.gs_hidrobairro.controller;

import com.fiap.gs_hidrobairro.dto.ia.OrientacaoRequestDTO;
import com.fiap.gs_hidrobairro.dto.ia.OrientacaoResponseDTO;
import com.fiap.gs_hidrobairro.service.OrientacaoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ia/orientacoes")
public class OrientacaoController {

    private final OrientacaoService orientacaoService;

    public OrientacaoController(OrientacaoService orientacaoService) {
        this.orientacaoService = orientacaoService;
    }

    @PostMapping
    public ResponseEntity<OrientacaoResponseDTO> gerarOrientacao(
            @Valid @RequestBody OrientacaoRequestDTO request) {

        String orientacao = orientacaoService.gerarOrientacao(
                request.pergunta()
        );

        return ResponseEntity.ok(
                new OrientacaoResponseDTO(orientacao)
        );
    }
}
