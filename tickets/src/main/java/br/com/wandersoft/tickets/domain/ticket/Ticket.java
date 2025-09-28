// Pacote: br.com.wandersoft.tickets.domain.ticket
package br.com.wandersoft.tickets.domain.ticket;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp; 
import org.hibernate.annotations.UpdateTimestamp;    

import java.time.LocalDateTime;

import br.com.wandersoft.tickets.domain.usuario.Usuario;

@Entity(name = "Ticket")
@Table(name = "tickets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Lob
    private String descricao;

    @Enumerated(EnumType.STRING)
    private StatusTicket status;

    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "solicitante_id")
    private Usuario solicitante;

    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "agente_id")
    private Usuario agente;

    @CreationTimestamp 
    @Column(nullable = false, updatable = false) 
    private LocalDateTime dataAbertura;
    
    @UpdateTimestamp 
    @Column(nullable = false) 
    private LocalDateTime dataUltimaAtualizacao;

    private LocalDateTime dataFechamento; 
}