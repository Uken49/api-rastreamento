CREATE DATABASE fastlog;

CREATE TABLE status (
    id UUID PRIMARY KEY NOT NULL,
    descricao VARCHAR(256) NOT NULL,
    pais VARCHAR(256) NOT NULL,
    cidade VARCHAR(256) NOT NULL,
    data_criacao TIMESTAMP NOT NULL
);

CREATE TABLE encomenda (
    id UUID PRIMARY KEY NOT NULL,
    nome VARCHAR(256) NOT NULL,
    status_fk UUID REFERENCES status(id) NOT NULL,
    origem VARCHAR(256) NOT NULL,
    destino VARCHAR(256) NOT NULL
);

