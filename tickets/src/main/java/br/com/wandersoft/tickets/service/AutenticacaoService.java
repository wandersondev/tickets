package br.com.wandersoft.tickets.service;

import br.com.wandersoft.tickets.domain.usuario.Usuario;
import br.com.wandersoft.tickets.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuario = repository.findByEmail(username);

        return usuario.orElseThrow(() -> 
            new UsernameNotFoundException("Usuário não encontrado ou senha inválida para o e-mail: " + username)
        );
    }
}