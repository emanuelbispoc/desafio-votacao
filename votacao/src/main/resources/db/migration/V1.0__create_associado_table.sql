CREATE TABLE associados (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(80) NOT NULL,
    cpf VARCHAR(11) UNIQUE NOT NULL
)