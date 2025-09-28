package br.com.wandersoft.tickets.service;

import br.com.wandersoft.tickets.domain.ticket.*;
import br.com.wandersoft.tickets.domain.usuario.UserRole;
import br.com.wandersoft.tickets.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    // --- LÓGICA DE CRIAÇÃO ---
    @Transactional
    public Ticket criarTicket(DadosCriacaoTicket dados, Usuario solicitante) {
        Ticket novoTicket = new Ticket();
        novoTicket.setTitulo(dados.titulo());
        novoTicket.setDescricao(dados.descricao());
        novoTicket.setSolicitante(solicitante);
        novoTicket.setDataAbertura(LocalDateTime.now());
        novoTicket.setStatus(StatusTicket.ABERTO);
        
        return ticketRepository.save(novoTicket);
    }

    // --- LÓGICA DE DETALHAMENTO (Resolve o erro do LOB) ---
    @Transactional(readOnly = true)
    public DadosDetalhamentoTicket detalharTicket(Long id, Usuario usuarioLogado) {
        // A transação começa aqui.
        Ticket ticket = ticketRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket não encontrado"));

        // Validação de segurança
        boolean isOwner = ticket.getSolicitante().getId().equals(usuarioLogado.getId());
        boolean isAdminOrAgent = usuarioLogado.getRole() == UserRole.ADMIN || usuarioLogado.getRole() == UserRole.AGENT;

        if (!isOwner && !isAdminOrAgent) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado");
        }

        // A criação do DTO (que acessa a 'descricao') acontece dentro da transação.
        return new DadosDetalhamentoTicket(ticket);
        // A transação termina aqui.
    }

    // --- LÓGICA DE LISTAGEM ---
    @Transactional(readOnly = true)
    public List<DadosListagemTicket> listarTickets(Usuario usuarioLogado) {
        List<Ticket> tickets;

        if (usuarioLogado.getRole() == UserRole.ADMIN || usuarioLogado.getRole() == UserRole.AGENT) {
            tickets = ticketRepository.findAll();
        } else {
            tickets = ticketRepository.findAllBySolicitanteId(usuarioLogado.getId());
        }

        return tickets.stream()
                .map(DadosListagemTicket::new)
                .collect(Collectors.toList());
    }
}
