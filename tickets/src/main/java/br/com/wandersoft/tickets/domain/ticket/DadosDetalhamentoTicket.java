package br.com.wandersoft.tickets.domain.ticket;

import java.time.LocalDateTime;

public record DadosDetalhamentoTicket(
 Long id,
 String titulo,
 String descricao,
 StatusTicket status,
 String nomeSolicitante,
 String nomeAgente, // Pode ser nulo
 LocalDateTime dataAbertura,
 LocalDateTime dataUltimaAtualizacao,
 LocalDateTime dataFechamento // Pode ser nulo
) {
 public DadosDetalhamentoTicket(Ticket ticket) {
     this(
         ticket.getId(),
         ticket.getTitulo(),
         ticket.getDescricao(),
         ticket.getStatus(),
         ticket.getSolicitante().getNome(),//não existe nome em Solicitante
         ticket.getAgente() != null ? ticket.getAgente().getNome() : null,
         ticket.getDataAbertura(),
         ticket.getDataUltimaAtualizacao(),
         ticket.getDataFechamento()
     );
 }
}