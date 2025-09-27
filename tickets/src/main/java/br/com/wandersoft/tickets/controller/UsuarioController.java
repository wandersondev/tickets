package br.com.wandersoft.tickets.controller;

import br.com.wandersoft.tickets.domain.usuario.Usuario;
import br.com.wandersoft.tickets.domain.usuario.UsuarioRepository;
import br.com.wandersoft.tickets.domain.usuario.DadosCadastroUsuario;
import br.com.wandersoft.tickets.domain.usuario.DadosDetalhamentoUsuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository; 
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroUsuario dados, UriComponentsBuilder uriBuilder) {
        if (repository.findByEmail(dados.email()).isPresent()) {

            return ResponseEntity.status(409).body("Email já cadastrado.");
        }

        var usuario = new Usuario(dados.nome(), dados.email(), passwordEncoder.encode(dados.senha()));
        repository.save(usuario);
        var uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoUsuario(usuario));
    }
}