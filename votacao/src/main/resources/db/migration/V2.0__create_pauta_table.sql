CREATE TABLE pautas (
    id SERIAL PRIMARY KEY,
    descricao VARCHAR(250) NOT NULL,
    data_criacao DATE NOT NULL,
    associado_id BIGINT NOT NULL,

    FOREIGN KEY (associado_id) REFERENCES associados (id)
)