package br.com.wandersoft.tickets.controller;

import br.com.wandersoft.tickets.domain.ticket.DadosCriacaoTicket;
import br.com.wandersoft.tickets.domain.ticket.DadosDetalhamentoTicket;
import br.com.wandersoft.tickets.domain.ticket.DadosListagemTicket;
import br.com.wandersoft.tickets.domain.usuario.Usuario;
import br.com.wandersoft.tickets.service.TicketService; // IMPORTANTE: Mudar para o serviço
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    // AGORA INJETAMOS O SERVIÇO, NÃO O REPOSITÓRIO
    @Autowired
    private TicketService ticketService;

    @PostMapping
    public ResponseEntity<DadosDetalhamentoTicket> criarTicket(@RequestBody @Valid DadosCriacaoTicket dados, Authentication authentication, UriComponentsBuilder uriBuilder) {
        Usuario solicitante = (Usuario) authentication.getPrincipal();
        var ticketCriado = ticketService.criarTicket(dados, solicitante);

        var uri = uriBuilder.path("/tickets/{id}").buildAndExpand(ticketCriado.getId()).toUri();

        // Retorna 201 Created com a URI do novo recurso e os detalhes no corpo
        return ResponseEntity.created(uri).body(new DadosDetalhamentoTicket(ticketCriado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoTicket> detalharTicket(@PathVariable Long id, Authentication authentication) {
        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
        DadosDetalhamentoTicket dados = ticketService.detalharTicket(id, usuarioLogado);
        return ResponseEntity.ok(dados);
    }

    @GetMapping
    public ResponseEntity<List<DadosListagemTicket>> listarTickets(Authentication authentication) {
        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
        List<DadosListagemTicket> listaDto = ticketService.listarTickets(usuarioLogado);
        return ResponseEntity.ok(listaDto);
    }
}