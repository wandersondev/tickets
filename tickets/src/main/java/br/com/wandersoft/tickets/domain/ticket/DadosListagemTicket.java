package br.com.wandersoft.tickets.domain.ticket;

import java.time.LocalDateTime;

public record DadosListagemTicket(
	    Long id,
	    String titulo,
	    StatusTicket status,
	    String nomeSolicitante,
	    LocalDateTime dataAbertura
	) {
	    // Construtor adicional para facilitar a conversão da Entidade para o DTO
	    public DadosListagemTicket(Ticket ticket) {
	        this(
	            ticket.getId(),
	            ticket.getTitulo(),
	            ticket.getStatus(),
	            ticket.getSolicitante().getNome(), // Assumindo que sua entidade Usuario tem um getNome()
	            ticket.getDataAbertura()
	        );
	    }
	}