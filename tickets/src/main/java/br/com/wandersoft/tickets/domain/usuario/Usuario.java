package br.com.wandersoft.tickets.domain.usuario;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Usuario")
@Table(name = "usuarios") 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails{

    /**
	 * 
	 */
	private static final long serialVersionUID = 6980282283130536662L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    private String senha;

    @Column(name = "imagem_url")
    private String imagemUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AuthProvider provider;

    @Column(name = "provider_id")
    private String providerId;

    // Construtor útil para o cadastro local
    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha; // A senha será criptografada no Service
        this.provider = AuthProvider.LOCAL;
    }
    
    //Métodos obrigatórios de UserDetail obrigatório
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Aqui você define os papéis (roles) do usuário.
        // Por enquanto, vamos retornar um papel padrão "ROLE_USER".
        // Futuramente, você pode ter uma entidade Role e uma relação ManyToMany.
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        // O "username" para o Spring Security será o email.
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        // Para simplificar, vamos retornar true.
        // Você pode adicionar uma lógica para contas que expiram.
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Para simplificar, vamos retornar true.
        // Você pode adicionar uma lógica para bloquear contas.
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Para simplificar, vamos retornar true.
        // Você pode adicionar uma lógica para senhas que expiram.
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Para simplificar, vamos retornar true.
        // Você pode adicionar uma lógica para desativar usuários.
        return true;
    }
}