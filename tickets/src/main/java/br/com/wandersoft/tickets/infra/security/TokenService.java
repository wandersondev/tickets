// Crie este novo arquivo em br.com.wandersoft.tickets.infra.security
package br.com.wandersoft.tickets.infra.security;

import br.com.wandersoft.tickets.domain.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    private static final String ISSUER = "API WanderSoft Tickets";

    /**
     * Gera um token JWT para o usuário autenticado.
     */
    public String gerarToken(Usuario usuario) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer(ISSUER) // Emissor do token
                    .withSubject(usuario.getEmail())
                    .withExpiresAt(dataExpiracao()) 
                    // .withClaim("id", usuario.getId()) // Você pode adicionar outras informações se precisar
                    .sign(algoritmo); // Assina o token
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    /**
     * Valida um token JWT e retorna o subject (email do usuário) se for válido.
     */
    public String getSubject(String tokenJWT) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);

            return JWT.require(algoritmo)
                    .withIssuer(ISSUER) 
                    .build()
                    .verify(tokenJWT) 
                    .getSubject(); 
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    /**
     * Calcula o momento de expiração do token.
     * Exemplo: 2 horas a partir do momento atual.
     */
    private Instant dataExpiracao() {
        // Define o fuso horário de Brasília
        return LocalDateTime.now()
                .plusHours(2)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}