/*UTILIZAR PK E UNIQUE FICA REPETITVO JÁ QUE QUANDO É PK ELA JÁ É UNIQUE ENTÃO QUE RETIRAR O UNIQUE E ADICONAL NOT NULL E IDENTITY
ONDE É VARCHAR TEM ESPECIFICAR O TAMANHO 

EXEMPLO DE UMA TABELA FEITO PELO HERYSSON TO ME BASEADO NELA
CREATE TABLE [Autor] (
  [id] int not null identity,
  [nome] varchar(100) not null,
  [nacionalidade] varchar(50),
  PRIMARY KEY ([id])
);
*/

IF NOT EXISTS (SELECT 1 FROM sys.databases WHERE name = N'ACERVO_TREZE_MAIO')
BEGIN
    CREATE DATABASE [ACERVO_TREZE_MAIO];
END
GO

USE ACERVO_TREZE_MAIO
GO

/* CRIAÇÃO DE TODAS AS TABALEAS DO BANCO */ 

-- TABELA USUARIO
CREATE TABLE Usuario (
    id_usuario INTEGER NOT NULL IDENTITY PRIMARY KEY,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    nome VARCHAR(50) NOT NULL,
    sobrenome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    telefone VARCHAR(20),
    tipo VARCHAR(20) NOT NULL,
    -- senha ?
);

-- TABELA EDITORA
CREATE TABLE editora (
    id_editora INTEGER NOT NULL IDENTITY,
    nome VARCHAR(100) NOT NULL,
    localizacao VARCHAR(255),
    PRIMARY KEY (id_editora)
);

ALTER TABLE editora
ADD CONSTRAINT unq_nome_localizacao UNIQUE (nome, localizacao);

-- TABELA ASSUNTO
create TABLE assunto (
    id_assunto INTEGER NOT NULL IDENTITY,
    descricao VARCHAR(255) NOT NULL,
    PRIMARY KEY (id_assunto)
);

ALTER TABLE assunto
ADD CONSTRAINT UQ_assunto_descricao UNIQUE (descricao);

-- TABELA COLABORADOR
CREATE TABLE colaborador (
    id_colaborador INTEGER NOT NULL IDENTITY,
    nome VARCHAR (100) NOT NULL,
    sobrenome VARCHAR (100) NOT NULL,
    nacionalidade VARCHAR (50),
    tipo VARCHAR (50) NOT NULL,
    PRIMARY KEY (id_colaborador)
);

ALTER TABLE colaborador
ADD CONSTRAINT unq_nomeCompleto_tipo UNIQUE (nome,sobrenome, tipo)

-- TABELA LIVRO
CREATE TABLE livro (
    id_livro INTEGER NOT NULL IDENTITY PRIMARY KEY,
    -- tIpo VARCHAR(20) NOT NULL, (remover ? todos na tabela são do tipo livro)
    codigo_livro VARCHAR(255) NOT NULL UNIQUE,
    titulo VARCHAR(255) NOT NULL,
    subtitulo VARCHAR(255),
    isbn VARCHAR(13) UNIQUE,
    ano_publicacao INTEGER,
    localizacao_acervo VARCHAR(255) NOT NULL,
    numero_paginas VARCHAR(10),
    edicao VARCHAR(20) NOT NULL,
    idioma VARCHAR(20) NOT NULL,
    quantidade INTEGER,
    capa BLOB,
    fk_editora_id_editora INTEGER,
);

ALTER TABLE livro ADD CONSTRAINT FK_livro_2
    FOREIGN KEY (fk_editora_id_editora)
    REFERENCES editora (id_editora)
    ON DELETE SET NULL;

-- TABELA REVISTA
CREATE TABLE revista (
    id_revista INTEGER NOT NULL IDENTITY PRIMARY KEY,
    codigo_revista VARCHAR(255) NOT NULL UNIQUE,
    volume VARCHAR(10),
    periodicidade VARCHAR(10),
    mes INTEGER,
    ano INTEGER,
    localizacao_acervo VARCHAR(255) NOT NULL,
    numero_paginas VARCHAR(10),
    -- tipo VARCHAR,
    edicao VARCHAR(20) NOT NULL,
    idioma VARCHAR(20) NOT NULL,
    titulo VARCHAR(255) NOT NULL,
    subtitulo VARCHAR(255),
    quantidade INTEGER,
    capa BLOB,
    fk_editora_id_editora INTEGER
);

ALTER TABLE revista ADD CONSTRAINT FK_revista_2
    FOREIGN KEY (fk_editora_id_editora)
    REFERENCES editora (id_editora)
    ON DELETE SET NULL;

-- TABELA JORNAL
CREATE TABLE jornal (
    id_jornal INTEGER NOT NULL IDENTITY PRIMARY KEY,
    codigo_jornal VARCHAR(255) NOT NULL UNIQUE,
    pais VARCHAR(100),
    estado VARCHAR(100),
    cidade VARCHAR(100),
    data DATE,
    localizacao_acervo VARCHAR(255) NOT NULL,
    numero_paginas VARCHAR(10),
    --tipo VARCHAR,
    edicao VARCHAR(20) NOT NULL,
    idioma VARCHAR(20) NOT NULL,
    titulo VARCHAR(255) NOT NULL,
    subtitulo VARCHAR(255),
    quantidade INTEGER,
    capa BLOB,
    fk_editora_id_editora INTEGER
);

ALTER TABLE jornal ADD CONSTRAINT FK_jornal_2
    FOREIGN KEY (fk_editora_id_editora)
    REFERENCES editora (id_editora)
    ON DELETE SET NULL;

-- TABELA EXEMPLAR
CREATE TABLE exemplar (
    id_exemplar INTEGER NOT NULL IDENTITY PRIMARY KEY,
    codigo VARCHAR(255) NOT NULL UNIQUE,
    fk_jornal_id_jornal INTEGER,
    fk_revista_id_revista INTEGER,
    fk_livro_id_livro INTEGER,
);

ALTER TABLE exemplar ADD CONSTRAINT FK_exemplar_2
    FOREIGN KEY (fk_jornal_id_jornal)
    REFERENCES jornal (id_jornal)
    ON DELETE SET NULL;
 
ALTER TABLE exemplar ADD CONSTRAINT FK_exemplar_3
    FOREIGN KEY (fk_revista_id_revista)
    REFERENCES revista (id_revista)
    ON DELETE SET NULL;
 
ALTER TABLE exemplar ADD CONSTRAINT FK_exemplar_4
    FOREIGN KEY (fk_livro_id_livro)
    REFERENCES livro (id_livro)
    ON DELETE SET NULL;

-- TABELA ITEM_COLABORADOR (sem chave primaria?) 
CREATE TABLE item_colaborador (
    fk_jornal_id_jornal INTEGER,
    fk_revista_id_revista INTEGER,
    fk_livro_id_livro INTEGER,
    fk_colaborador_id_colaborador INTEGER
);

ALTER TABLE item_colaborador ADD CONSTRAINT FK_item_colaborador_1
    FOREIGN KEY (fk_jornal_id_jornal)
    REFERENCES jornal (id_jornal)
    ON DELETE SET NULL;

ALTER TABLE item_colaborador ADD CONSTRAINT FK_item_colaborador_2
    FOREIGN KEY (fk_revista_id_revista)
    REFERENCES revista (id_revista)
    ON DELETE SET NULL;

ALTER TABLE item_colaborador ADD CONSTRAINT FK_item_colaborador_3
    FOREIGN KEY (fk_livro_id_livro)
    REFERENCES livro (id_livro)
    ON DELETE SET NULL;

ALTER TABLE item_colaborador ADD CONSTRAINT FK_item_colaborador_4
    FOREIGN KEY (fk_colaborador_id_colaborador)
    REFERENCES colaborador (id_colaborador)
    ON DELETE SET NULL;

-- TABELA ITEM_ASSUNTO (sem chave primaria?) 
CREATE TABLE item_assunto (
    fk_jornal_id_jornal INTEGER,
    fk_revista_id_revista INTEGER,
    fk_livro_id_livro INTEGER,
    fk_assunto_id_assunto INTEGER
);

ALTER TABLE item_assunto ADD CONSTRAINT FK_item_assunto_1
    FOREIGN KEY (fk_jornal_id_jornal)
    REFERENCES jornal (id_jornal)
    ON DELETE SET NULL;

ALTER TABLE item_assunto ADD CONSTRAINT FK_item_assunto_2
    FOREIGN KEY (fk_revista_id_revista)
    REFERENCES revista (id_revista)
    ON DELETE SET NULL;

ALTER TABLE item_assunto ADD CONSTRAINT FK_item_assunto_3
    FOREIGN KEY (fk_livro_id_livro)
    REFERENCES livro (id_livro)
    ON DELETE SET NULL;

ALTER TABLE item_assunto ADD CONSTRAINT FK_item_assunto_4
    FOREIGN KEY (fk_assunto_id_assunto)
    REFERENCES assunto (id_assunto)
    ON DELETE SET NULL;
