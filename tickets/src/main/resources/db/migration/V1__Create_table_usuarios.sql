CREATE TYPE auth_provider AS ENUM ('LOCAL', 'GOOGLE');
CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255),
    imagem_url VARCHAR(255),
    provider VARCHAR(20) NOT NULL,
    provider_id VARCHAR(255)
);
CREATE INDEX idx_usuarios_email ON usuarios(email);
