package com.fiap.gs_hidrobairro.controller;


import com.fiap.gs_hidrobairro.dto.response.AlertaResponseDTO;
import com.fiap.gs_hidrobairro.service.AlertaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alertas")
@Tag(name = "Alertas", description = "Endpoints para gerenciamento de alertas")
public class AlertaController {

    @Autowired
    private AlertaService alertaService;

    @GetMapping
    @PreAuthorize("hasAnyRole('CIDADAO', 'GESTOR')")
    @Operation(
            summary = "Listar alertas",
            description = "Retorna uma lista paginada de todos os alertas cadastrados."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de alertas retornada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AlertaResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Usuário não autenticado ou token inválido"
            ),

    })
    public ResponseEntity<Page<AlertaResponseDTO>> listarTodos(
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "id",
                    direction = Sort.Direction.ASC
            ) Pageable pageable
    ) {
        Page<AlertaResponseDTO> alertas = alertaService.listarTodos(pageable);
        return ResponseEntity.ok(alertas);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CIDADAO', 'GESTOR')")
    @Operation(
            summary = "Buscar alerta por ID",
            description = "Retorna um alerta específico com base no ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Alerta encontrado com sucesso"
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
                    description = "Alerta não encontrado"
            )
    })
    public ResponseEntity<AlertaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(alertaService.buscarPorId(id));
    }

    @GetMapping("/ativos")
    @PreAuthorize("hasAnyRole('CIDADAO', 'GESTOR')")
    @Operation(
            summary = "Listar alertas ativos",
            description = "Retorna uma lista paginada de todos os alertas ativos."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de alertas ativos retornada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AlertaResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Usuário não autenticado ou token inválido"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Usuário não possui permissão para acessar este recurso"
            )
    })
    public ResponseEntity<Page<AlertaResponseDTO>> listarAtivos(
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "id",
                    direction = Sort.Direction.ASC
            ) Pageable pageable
    ) {
        Page<AlertaResponseDTO> alertas = alertaService.listarAtivos(pageable);
        return ResponseEntity.ok(alertas);
    }

}
