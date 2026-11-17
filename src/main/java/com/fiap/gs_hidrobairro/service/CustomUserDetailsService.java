package com.fiap.gs_hidrobairro.service;

import com.fiap.gs_hidrobairro.model.Usuario;
import com.fiap.gs_hidrobairro.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Usuario usuario = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return new CustomUserDetails(
                usuario.getEmail(),
                usuario.getSenhaHash(),
                usuario.getPapel()
        ) {
            @Override
            public boolean isEnabled() {
                return usuario.getAtivo() == 'S';
            }
        };
    }
}