CREATE TABLE sessoes (
    id SERIAL PRIMARY KEY,
    data_inicio DATETIME NOT NULL,
    data_fim DATETIME NOT NULL,
    pauta_id BIGINT NOT NULL,

    FOREIGN KEY (pauta_id) REFERENCES pautas (id)
);

CREATE TABLE votos_sessao (
    decisao VARCHAR(80) CHECK (decisao IN ('SIM', 'NAO')),
    associado_id BIGINT NOT NULL,
    sessao_id BIGINT NOT NULL,

    PRIMARY KEY (associado_id, sessao_id),
    FOREIGN KEY (associado_id) REFERENCES associados (id),
    FOREIGN KEY (sessao_id) REFERENCES sessoes (id)
)