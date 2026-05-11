CREATE TABLE enderecos (
    id BIGSERIAL PRIMARY KEY,
    logradouro VARCHAR(255),
    numero     VARCHAR(20),
    bairro     VARCHAR(100),
    cidade     VARCHAR(100),
    cep        VARCHAR(255),

    cliente_id BIGINT NOT NULL, -- Todo endereço precisa ser de um cliente

    -- Cria a relação entre endereço e cliente
    CONSTRAINT fk_endereco_cliente
       FOREIGN KEY(cliente_id)
       REFERENCES clientes(id)
       ON DELETE CASCADE -- Se apagar um cliente, apaga todos os endereços relacionados a ele
);