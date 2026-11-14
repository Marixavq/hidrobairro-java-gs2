package com.fiap.gs_hidrobairro.controller;


import com.fiap.gs_hidrobairro.dto.request.PontoMonitoramentoRequestDTO;
import com.fiap.gs_hidrobairro.dto.response.PontoMonitoramentoResponseDTO;
import com.fiap.gs_hidrobairro.service.PontoMonitoramentoService;
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
@RequestMapping("/api/pontos-monitoramento")
@Tag(name = "Pontos de Monitoramento", description = "Endpoints para gerenciamento de pontos de monitoramento")
public class PontoMonitoramentoController {

    @Autowired
    private PontoMonitoramentoService pontoService;

    @PostMapping
    @PreAuthorize("hasRole('GESTOR')")
    @Operation(
            summary = "Criar ponto de monitoramento",
            description = "Cadastra um novo ponto de monitoramento."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Ponto de monitoramento criado com sucesso"
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
                    responseCode = "409",
                    description = "Operação não permitida devido a uma regra de negócio"
            ),
    })
    public ResponseEntity<PontoMonitoramentoResponseDTO> criar(@Valid @RequestBody PontoMonitoramentoRequestDTO requestDTO) {
        PontoMonitoramentoResponseDTO novoPonto = pontoService.salvar(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPonto);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('CIDADAO', 'GESTOR')")
    @Operation(
            summary = "Listar pontos de monitoramento",
            description = "Retorna todos os pontos de monitoramento cadastrados."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de pontos de monitoramento retornada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PontoMonitoramentoResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Usuário não autenticado ou token inválido"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Usuário autenticado, mas sem permissão para acessar este recurso"
            ),
    })
    public ResponseEntity<Page<PontoMonitoramentoResponseDTO>> listarTodos(
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "id",
                    direction = Sort.Direction.ASC
            ) Pageable pageable
    ) {
        Page<PontoMonitoramentoResponseDTO> pontos = pontoService.listarTodos(pageable);
        return ResponseEntity.ok(pontos);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CIDADAO', 'GESTOR')")
    @Operation(
            summary = "Buscar ponto por ID",
            description = "Retorna um ponto de monitoramento específico pelo ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ponto de monitoramento encontrado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ponto de monitoramento não encontrado"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Usuário não autenticado ou token inválido"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Usuário autenticado, mas sem permissão para acessar este recurso"
            ),
    })
    public ResponseEntity<PontoMonitoramentoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pontoService.buscarPorId(id));
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('GESTOR')")
    @Operation(
            summary = "Atualizar ponto de monitoramento",
            description = "Atualiza os dados de um ponto existente."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ponto de monitoramento atualizado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ponto de monitoramento não encontrado"
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
                    responseCode = "409",
                    description = "Operação não permitida devido a uma regra de negócio"
            )
    })
    public ResponseEntity<PontoMonitoramentoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody PontoMonitoramentoRequestDTO requestDTO) {
        return ResponseEntity.ok(pontoService.atualizar(id, requestDTO));
    }

    @PatchMapping("/{id}/desativar")
    @PreAuthorize("hasRole('GESTOR')")
    @Operation(
            summary = "Desativar ponto de monitoramento",
            description = "Desativa um ponto de monitoramento existente. Operação exclusiva para gestores."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ponto de monitoramento desativado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Usuário não autenticado ou token inválido"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ponto de monitoramento não encontrado"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Usuário autenticado, mas sem permissão para acessar este recurso"
            ),
    })
    public ResponseEntity<PontoMonitoramentoResponseDTO> desativar(@PathVariable Long id) {
        return ResponseEntity.ok(pontoService.desativar(id));
    }

    @PatchMapping("/{id}/reativar")
    @PreAuthorize("hasRole('GESTOR')")
    @Operation(
            summary = "Reativar ponto de monitoramento",
            description = "Reativa um ponto de monitoramento existente. Operação exclusiva para gestores."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ponto de monitoramento reativado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Usuário não autenticado ou token inválido"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ponto de monitoramento não encontrado"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Usuário autenticado, mas sem permissão para acessar este recurso"
            ),
    })
    public ResponseEntity<PontoMonitoramentoResponseDTO> reativar(@PathVariable Long id) {
        return ResponseEntity.ok(pontoService.reativar(id));
    }


}

