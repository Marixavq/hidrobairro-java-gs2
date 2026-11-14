package com.fiap.gs_hidrobairro.controller;

import com.fiap.gs_hidrobairro.dto.request.LeituraRequestDTO;
import com.fiap.gs_hidrobairro.dto.response.LeituraResponseDTO;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/leituras")
@Tag(name = "Leituras", description = "Endpoints para gerenciamento de leituras")
public class LeituraController {

    @Autowired
    private LeituraService leituraService;

    @PostMapping
    //ainda vamos integrar com o IoT
    @Operation(
            summary = "Criar leitura",
            description = "Registra uma nova leitura de um ponto de monitoramento."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Leitura criada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Usuário não autenticado ou token inválido"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Usuário autenticado, mas sem permissão para acessar este recurso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Item não encontrado"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Operação não permitida devido a uma regra de negócio"
            ),
    })
    public ResponseEntity<LeituraResponseDTO> criar(@Valid @RequestBody LeituraRequestDTO requestDTO) {
        LeituraResponseDTO novaLeitura = leituraService.salvar(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaLeitura);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('CIDADAO', 'GESTOR')")
    @Operation(
            summary = "Listar leituras",
            description = "Retorna as leituras cadastradas no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de leituras retornada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LeituraResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Usuário não autenticado ou token inválido"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Usuário autenticado, mas sem permissão para acessar este recurso"
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
    @PreAuthorize("hasAnyRole('CIDADAO', 'GESTOR')")
    @Operation(
            summary = "Buscar leitura por ID",
            description = "Retorna uma leitura específica pelo ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Leitura encontrada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Usuário não autenticado ou token inválido"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Leitura não encontrada"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Usuário autenticado, mas sem permissão para acessar este recurso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Item não encontrado"
            )

    })
    public ResponseEntity<LeituraResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(leituraService.buscarPorId(id));
    }

}
