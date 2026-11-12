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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pontos-monitoramento")
@Tag(name = "Pontos de Monitoramento", description = "Endpoints para gerenciamento de pontos de monitoramento")
public class PontoMonitoramentoController {

    @Autowired
    private PontoMonitoramentoService pontoService;

    @PostMapping
    @Operation(
            summary = "Criar ponto de monitoramento",
            description = "Cria um novo usuário no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuário criado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos"
            )
    })
    public ResponseEntity<PontoMonitoramentoResponseDTO> criar(@Valid @RequestBody PontoMonitoramentoRequestDTO requestDTO) {
        PontoMonitoramentoResponseDTO novoPonto = pontoService.salvar(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPonto);
    }

    @GetMapping
    //@PreAuthorize
    @Operation(
            summary = "Listar usuários",
            description = "Retorna uma lista completa de todos os usuários cadastrados."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de usuários retornada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PontoMonitoramentoResponseDTO.class)
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
    //@PreAuthorize
    @Operation(
            summary = "Buscar usuário por ID",
            description = "Retorna um usuário específico baseado no ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário encontrado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Usuário não autenticado ou token inválido"
            )
    })
    public ResponseEntity<PontoMonitoramentoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pontoService.buscarPorId(id));
    }
    @PutMapping("/{id}")
    //@PreAuthorize
    @Operation(
            summary = "Atualizar usuário",
            description = "Atualiza os dados de um usuário existente."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário atualizado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado"
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
                    responseCode = "409",
                    description = "Operação não permitida devido a uma regra de negócio"
            )
    })
    public ResponseEntity<PontoMonitoramentoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody PontoMonitoramentoRequestDTO requestDTO) {
        return ResponseEntity.ok(pontoService.atualizar(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deletar usuário",
            description = "Remove um usuário do sistema baseado no ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Usuário removido com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado"
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
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pontoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}

