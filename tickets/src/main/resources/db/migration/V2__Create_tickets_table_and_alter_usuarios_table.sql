-- Migration V1: Cria a tabela de tickets e adiciona a coluna de perfil (role) aos usuários.

-- ----------------------------
-- CRIAÇÃO DA TABELA tickets
-- ----------------------------
CREATE TABLE tickets_api.tickets (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT NOT NULL,
    status VARCHAR(50) NOT NULL,
    solicitante_id BIGINT NOT NULL,
    agente_id BIGINT,
    data_abertura TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    data_ultima_atualizacao TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    data_fechamento TIMESTAMP WITHOUT TIME ZONE,
    
    CONSTRAINT fk_tickets_solicitante FOREIGN KEY (solicitante_id) REFERENCES tickets_api.usuarios(id),
    CONSTRAINT fk_tickets_agente FOREIGN KEY (agente_id) REFERENCES tickets_api.usuarios(id)
);

-- Adiciona um índice na coluna de status para otimizar buscas por status de ticket.
CREATE INDEX idx_tickets_status ON tickets_api.tickets(status);

-- Adiciona um índice na coluna de solicitante para otimizar a busca de "meus tickets".
CREATE INDEX idx_tickets_solicitante_id ON tickets_api.tickets(solicitante_id);


-- ----------------------------
-- ALTERAÇÃO DA TABELA usuarios
-- ----------------------------

-- Adiciona a coluna 'role' na tabela 'usuarios'.
ALTER TABLE tickets_api.usuarios ADD COLUMN role VARCHAR(50);

-- Define um valor padrão ('USER') para a coluna 'role' em todos os registros existentes.
-- Isso é crucial para garantir que a subsequente restrição NOT NULL não falhe.
UPDATE tickets_api.usuarios SET role = 'USER' WHERE role IS NULL;

-- Torna a coluna 'role' obrigatória (NOT NULL) após preencher os valores existentes.
ALTER TABLE tickets_api.usuarios ALTER COLUMN role SET NOT NULL;

-- Adiciona um índice na nova coluna 'role' para otimizar consultas baseadas no perfil do usuário.
CREATE INDEX idx_usuarios_role ON tickets_api.usuarios(role);