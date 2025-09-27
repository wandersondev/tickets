// Crie este novo arquivo em br.com.wandersoft.tickets.controller
package br.com.wandersoft.tickets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.wandersoft.tickets.domain.usuario.DadosAutenticacao;
import br.com.wandersoft.tickets.domain.usuario.Usuario;
import br.com.wandersoft.tickets.infra.security.DadosTokenJWT;
import br.com.wandersoft.tickets.infra.security.TokenService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/login") 
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager; 

    @Autowired
    private TokenService tokenService; 

    @PostMapping 
    public ResponseEntity<DadosTokenJWT> efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        System.out.println("DadosAutenticacao: "+ dados);		
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
        Authentication authentication = manager.authenticate(authenticationToken);
        var usuario = (Usuario) authentication.getPrincipal();
        String tokenJWT = tokenService.gerarToken(usuario);
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}