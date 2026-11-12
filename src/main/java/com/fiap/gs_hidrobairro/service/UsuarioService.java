package com.fiap.gs_hidrobairro.service;

import com.fiap.gs_hidrobairro.dto.request.UsuarioRequestDTO;
import com.fiap.gs_hidrobairro.dto.response.UsuarioResponseDTO;
import com.fiap.gs_hidrobairro.exception.RecursoNaoEncontradoException;
import com.fiap.gs_hidrobairro.exception.RegraNegocioException;
import com.fiap.gs_hidrobairro.model.Usuario;
import com.fiap.gs_hidrobairro.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public UsuarioResponseDTO salvar(UsuarioRequestDTO requestDTO) {
        if (usuarioRepository.existsByEmail(requestDTO.email())) {
            throw new RegraNegocioException("Email já cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(requestDTO.nome());
        usuario.setEmail(requestDTO.email());
      //  usuario.setSenhaHash(requestDTO.senha());
        usuario.setSenhaHash(passwordEncoder.encode(senha));
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
        usuarioExistente.setNome(requestDTO.nome());
        usuarioExistente.setEmail(requestDTO.email());
        usuarioExistente.setSenhaHash(requestDTO.senha());
        usuarioExistente.setPapel(requestDTO.papel());
        //usuarioExistente.setAtivo("N");

        Usuario usuarioAtualizado = usuarioRepository.save(usuarioExistente);

        return UsuarioResponseDTO.fromEntity(usuarioAtualizado);
    }

    @Transactional
    public void deletar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Usuário não encontrado com ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}
