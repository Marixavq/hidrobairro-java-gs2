package com.fiap.gs_hidrobairro.service;

import com.fiap.gs_hidrobairro.dto.request.UsuarioRequestDTO;
import com.fiap.gs_hidrobairro.dto.response.UsuarioResponseDTO;
import com.fiap.gs_hidrobairro.exception.AcessoNegadoException;
import com.fiap.gs_hidrobairro.exception.RecursoNaoEncontradoException;
import com.fiap.gs_hidrobairro.exception.RegraNegocioException;
import com.fiap.gs_hidrobairro.model.Usuario;
import com.fiap.gs_hidrobairro.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UsuarioResponseDTO salvar(UsuarioRequestDTO requestDTO) {
        if (usuarioRepository.existsByEmail(requestDTO.email())) {
            throw new RegraNegocioException("Email já cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(requestDTO.nome());
        usuario.setEmail(requestDTO.email());
        usuario.setSenhaHash(passwordEncoder.encode(requestDTO.senha()));
        usuario.setPapel(requestDTO.papel());

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return UsuarioResponseDTO.fromEntity(usuarioSalvo);
    }

    @Transactional(readOnly = true)
    public Page<UsuarioResponseDTO> listarTodos(Pageable pageable) {
        Page<Usuario> usuarios = usuarioRepository.findAll(pageable);
        return usuarios.map(UsuarioResponseDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com ID: " + id));
        return UsuarioResponseDTO.fromEntity(usuario);
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO buscarPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com email: " + email));
        return UsuarioResponseDTO.fromEntity(usuario);
    }

    @Transactional
    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO requestDTO) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com ID: " + id));

        if (usuarioExistente.getAtivo() != 'S') {
            throw new RegraNegocioException("Não é possível atualizar dados de um usuário desativado");
        }

        if (!usuarioExistente.getEmail().equals(requestDTO.email())
                && usuarioRepository.existsByEmail(requestDTO.email())) {
            throw new RegraNegocioException("Email já cadastrado");
        }

        usuarioExistente.setNome(requestDTO.nome());
        usuarioExistente.setEmail(requestDTO.email());
        usuarioExistente.setSenhaHash(passwordEncoder.encode(requestDTO.senha()));

        Usuario usuarioAtualizado = usuarioRepository.save(usuarioExistente);

        return UsuarioResponseDTO.fromEntity(usuarioAtualizado);
    }

    // Usuário não pode ser excluído literalmente, apenas desativado (pelo GESTOR)
    @Transactional
    public UsuarioResponseDTO desativar(Long id) {
        Usuario usuarioLogado = getUsuarioAutenticado();

        validarDesativacaoPropria(id, usuarioLogado);

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com ID: " + id));

        if (usuario.getAtivo() == 'N') {
            throw new RegraNegocioException("O usuário já está desativado");
        }

        usuario.setAtivo('N');

        return UsuarioResponseDTO.fromEntity(usuario);
    }

    // Usuário precisa ser reativado pelo GESTOR pra fazer login
    @Transactional
    public UsuarioResponseDTO reativar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com ID: " + id));

        if (usuario.getAtivo() == 'S') {
            throw new RegraNegocioException("O usuário já está ativo");
        }

        usuario.setAtivo('S');

        return UsuarioResponseDTO.fromEntity(usuario);
    }


    // Usuário autenticado pelo JWT
    private Usuario getUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));
    }

    private void validarDesativacaoPropria(Long id, Usuario usuarioLogado) {
        if (id.equals(usuarioLogado.getId())) {
            throw new AcessoNegadoException("Você não pode desativar sua própria conta");
        }
    }
}
