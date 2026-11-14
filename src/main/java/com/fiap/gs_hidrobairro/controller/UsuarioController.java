package com.fiap.gs_hidrobairro.controller;

import com.fiap.gs_hidrobairro.dto.request.UsuarioRequestDTO;
import com.fiap.gs_hidrobairro.dto.response.UsuarioResponseDTO;
import com.fiap.gs_hidrobairro.service.UsuarioService;
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
@RequestMapping("/api/usuarios")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    @Operation(
            summary = "Criar usuário",
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
    public ResponseEntity<UsuarioResponseDTO> criar(@Valid @RequestBody UsuarioRequestDTO requestDTO) {
        UsuarioResponseDTO novoUsuario = usuarioService.salvar(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('GESTOR')")
    @Operation(
            summary = "Listar usuários",
            description = "Retorna uma lista completa de todos os usuários cadastrados."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de usuários retornada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioResponseDTO.class)
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
    public ResponseEntity<Page<UsuarioResponseDTO>> listarTodos(
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "id",
                    direction = Sort.Direction.ASC
            ) Pageable pageable
    ) {
        Page<UsuarioResponseDTO> usuarios = usuarioService.listarTodos(pageable);
        return ResponseEntity.ok(usuarios);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CIDADAO', 'GESTOR')")
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
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Usuário autenticado, mas sem permissão para acessar este recurso"
            ),
    })
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasAnyRole('GESTOR')")
    @Operation(
            summary = "Buscar usuário por email",
            description = "Retorna um usuário específico baseado no email."
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
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Usuário autenticado, mas sem permissão para acessar este recurso"
            ),
    })
    public ResponseEntity<UsuarioResponseDTO> buscarPorEmail(@PathVariable String email) {
        return ResponseEntity.ok(usuarioService.buscarPorEmail(email));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CIDADAO', 'GESTOR')")
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
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Usuário autenticado, mas sem permissão para acessar este recurso"
            ),
    })
    public ResponseEntity<UsuarioResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioRequestDTO requestDTO) {
        return ResponseEntity.ok(usuarioService.atualizar(id, requestDTO));
    }



    @PatchMapping("/{id}/desativar")
    @PreAuthorize("hasRole('GESTOR')")
    @Operation(
            summary = "Desativar usuário",
            description = "Desativa um usuário existente. Operação exclusiva para gestores."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário desativado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Usuário não autenticado ou token inválido"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Usuário autenticado, mas sem permissão para acessar este recurso"
            ),
    })
    public ResponseEntity<UsuarioResponseDTO> desativar(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.desativar(id));
    }

    @PatchMapping("/{id}/reativar")
    @PreAuthorize("hasRole('GESTOR')")
    @Operation(
            summary = "Reativar usuário",
            description = "Reativa um usuário existente. Operação exclusiva para gestores."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário reativado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Usuário não autenticado ou token inválido"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Usuário autenticado, mas sem permissão para acessar este recurso"
            ),
    })
    public ResponseEntity<UsuarioResponseDTO> reativar(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.reativar(id));
    }

}

