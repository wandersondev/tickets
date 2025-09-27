package br.com.wandersoft.tickets.domain.usuario;

public record DadosDetalhamentoUsuario(
    Long id,
    String nome,
    String email,
    AuthProvider provider,
    String imagemUrl
) {
    // Construtor auxiliar para facilitar a conversão da Entidade para o DTO
    public DadosDetalhamentoUsuario(Usuario usuario) {
        this(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getProvider(),
            usuario.getImagemUrl()
        );
    }
}