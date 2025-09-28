package br.com.wandersoft.tickets.domain.ticket;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    // Spring Data JPA vai criar a query automaticamente a partir do nome do método!
    List<Ticket> findAllBySolicitanteId(Long solicitanteId);
    
    /**
     * Análise da Query (JPQL):
     *	SELECT t FROM Ticket t: Seleciona a entidade Ticket.
     *	JOIN FETCH t.solicitante: A mágica está aqui. JOIN FETCH instrui o JPA a não apenas fazer um JOIN na tabela de usuários, mas a carregar e inicializar completamente o objeto solicitante na mesma consulta SQL.
     *	LEFT JOIN FETCH t.agente: Usamos LEFT JOIN para o agente porque ele pode ser nulo. Se usássemos JOIN FETCH, tickets sem agente não seriam retornados.
     *
     */
    @Query("""
            SELECT t FROM Ticket t 
            JOIN FETCH t.solicitante 
            LEFT JOIN FETCH t.agente 
            WHERE t.id = :id
        """)
    Optional<Ticket> findByIdWithDetails(@Param("id") Long id);
}
