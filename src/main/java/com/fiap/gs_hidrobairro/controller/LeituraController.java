package com.fiap.gs_hidrobairro.controller;

import com.fiap.gs_hidrobairro.dto.request.LeituraRequestDTO;
import com.fiap.gs_hidrobairro.dto.response.LeituraResponseDTO;
import com.fiap.gs_hidrobairro.dto.response.UsuarioResponseDTO;
import com.fiap.gs_hidrobairro.service.LeituraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/leituras")
@Tag(name = "Leituras", description = "Endpoints para gerenciamento de leituras")
public class LeituraController {

    @Autowired
    private LeituraService leituraService;

    @PostMapping
    @Operation(
            summary = "Criar leitura",
            description = "Cria uma nova leitura no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Leitura criada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos"
            )
    })
    public ResponseEntity<LeituraResponseDTO> criar(@Valid @RequestBody LeituraRequestDTO requestDTO) {
        LeituraResponseDTO novaLeitura = leituraService.salvar(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaLeitura);
    }

    @GetMapping
    //@PreAuthorize
    @Operation(
            summary = "Listar leituras",
            description = "Retorna uma lista completa de todos as leituras cadastradas."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de leituras retornada com sucesso",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Usuário não autenticado ou token inválido"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Operação não permitida devido a uma regra de negócio"
            )
    })
    public ResponseEntity<Page<LeituraResponseDTO>> listarTodos(
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "id",
                    direction = Sort.Direction.ASC
            ) Pageable pageable
    ) {
        Page<LeituraResponseDTO> leituras = leituraService.listarTodos(pageable);
        return ResponseEntity.ok(leituras);
    }

    @GetMapping("/{id}")
    //@PreAuthorizeuario
    @Operation(
            summary = "Buscar leitura por ID",
            description = "Retorna uma leitura específico baseado no ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Leitura encontrado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Leitura não encontrado"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Usuário não autenticado ou token inválido"
            )
    })
    public ResponseEntity<LeituraResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(leituraService.buscarPorId(id));
    }

}
