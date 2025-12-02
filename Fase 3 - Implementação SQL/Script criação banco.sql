/*UTILIZAR PK E UNIQUE FICA REPETITVO JÁ QUE QUANDO É PK ELA JÁ É UNIQUE ENTÃO QUE RETIRAR O UNIQUE E ADICONAL NOT NULL E IDENTITY
ONDE É VARCHAR TEM ESPECIFICAR O TAMANHO 
ID_ITEM_ACERVO É PRA QUE ? SE ELE TIVER UM EM CADA TABELA E FOR IDENTITY VAI GERAR UM NUMERO PARA CADA OU SEGUE OS MESMO PARA TODAS

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

CREATE TABLE Usuario (
    cpf VARCHAR,
    telefone VARCHAR,
    id_usuario INTEGER PRIMARY KEY,
    nome VARCHAR,
    email VARCHAR,
    sobrenome VARCHAR,
    tipo VARCHAR,
    UNIQUE (cpf, telefone, id_usuario, email)
);

CREATE TABLE livro (
    id_livro INTEGER NOT NULL IDENTITY,
    id_item_acervo INTEGER NOT NULL ,
    tipo VARCHAR(20) NOT NULL,
    codigo_livro VARCHAR(255) NOT NULL ,
    titulo VARCHAR(255) NOT NULL,
    subtitulo VARCHAR(255),
    isbn VARCHAR(13),
    ano_publicacao INTEGER,
    localizacao_acervo VARCHAR(255) NOT NULL,
    numero_paginas VARCHAR(10),
    edicao VARCHAR(20) NOT NULL,
    idioma VARCHAR(20) NOT NULL,
    quantidade INTEGER,
    capa BLOB,
    fk_editora_id_editora INTEGER,
    PRIMARY KEY (id_livro, id_item_acervo),
    UNIQUE (isbn, codigo_livro, id_item_acervo)
);

CREATE TABLE editora (
    id_editora INTEGER NOT NULL IDENTITY,
    nome VARCHAR(100) NOT NULL,
    localizacao VARCHAR(255),
    PRIMARY KEY (id_editora)

);
ALTER TABLE editora
ADD CONSTRAINT unq_nome_localizacao UNIQUE (nome, localizacao);


create TABLE assunto (
    id_assunto INTEGER NOT NULL IDENTITY,
    descricao VARCHAR(255) NOT NULL,
    PRIMARY KEY (id_assunto)
);

ALTER TABLE assunto
ADD CONSTRAINT UQ_assunto_descricao UNIQUE (descricao);


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


CREATE TABLE revista (
    volume VARCHAR,
    id_revista INTEGER,
    periodicidade VARCHAR,
    mes INTEGER,
    ano INTEGER,
    codigo_revista VARCHAR,
    localizacao_acervo VARCHAR,
    numero_paginas VARCHAR,
    tipo VARCHAR,
    edicao VARCHAR,
    subtitulo VARCHAR,
    id_item_acervo INTEGER,
    titulo VARCHAR,
    idioma VARCHAR,
    quantidade INTEGER,
    capa BLOB,
    fk_editora_id_editora INTEGER,
    PRIMARY KEY (id_revista, id_item_acervo),
    UNIQUE (id_revista, codigo_revista, id_item_acervo)
);

CREATE TABLE jornal (
    id_jornal INTEGER,
    pais VARCHAR,
    estado VARCHAR,
    codigo_jornal INTEGER,
    cidade VARCHAR,
    data DATE,
    localizacao_acervo VARCHAR,
    numero_paginas VARCHAR,
    tipo VARCHAR,
    edicao VARCHAR,
    subtitulo VARCHAR,
    id_item_acervo INTEGER,
    titulo VARCHAR,
    idioma VARCHAR,
    quantidade VARCHAR,
    capa BLOB,
    fk_editora_id_editora INTEGER,
    PRIMARY KEY (id_jornal, id_item_acervo),
    UNIQUE (id_jornal, codigo_jornal, id_item_acervo)
);

CREATE TABLE exemplar (
    id_exemplar INTEGER PRIMARY KEY,
    codigo VARCHAR,
    fk_jornal_id_jornal INTEGER,
    fk_jornal_id_item_acervo INTEGER,
    fk_revista_id_revista INTEGER,
    fk_revista_id_item_acervo INTEGER,
    fk_livro_id_livro INTEGER,
    fk_livro_id_item_acervo INTEGER,
    UNIQUE (id_exemplar, codigo)
);

CREATE TABLE item_colaborador (
    fk_jornal_id_jornal INTEGER,
    fk_jornal_id_item_acervo INTEGER,
    fk_revista_id_revista INTEGER,
    fk_revista_id_item_acervo INTEGER,
    fk_livro_id_livro INTEGER,
    fk_livro_id_item_acervo INTEGER,
    fk_colaborador_id_colaborador INTEGER
);

CREATE TABLE item_assunto (
    fk_jornal_id_jornal INTEGER,
    fk_jornal_id_item_acervo INTEGER,
    fk_revista_id_revista INTEGER,
    fk_revista_id_item_acervo INTEGER,
    fk_livro_id_livro INTEGER,
    fk_livro_id_item_acervo INTEGER,
    fk_assunto_id_assunto INTEGER
);
 
ALTER TABLE livro ADD CONSTRAINT FK_livro_2
    FOREIGN KEY (fk_editora_id_editora)
    REFERENCES editora (id_editora)
    ON DELETE SET NULL;
 
ALTER TABLE revista ADD CONSTRAINT FK_revista_2
    FOREIGN KEY (fk_editora_id_editora)
    REFERENCES editora (id_editora)
    ON DELETE SET NULL;
 
ALTER TABLE jornal ADD CONSTRAINT FK_jornal_2
    FOREIGN KEY (fk_editora_id_editora)
    REFERENCES editora (id_editora)
    ON DELETE SET NULL;
 
ALTER TABLE exemplar ADD CONSTRAINT FK_exemplar_2
    FOREIGN KEY (fk_jornal_id_jornal, fk_jornal_id_item_acervo)
    REFERENCES jornal (id_jornal, id_item_acervo)
    ON DELETE SET NULL;
 
ALTER TABLE exemplar ADD CONSTRAINT FK_exemplar_3
    FOREIGN KEY (fk_revista_id_revista, fk_revista_id_item_acervo)
    REFERENCES revista (id_revista, id_item_acervo)
    ON DELETE SET NULL;
 
ALTER TABLE exemplar ADD CONSTRAINT FK_exemplar_4
    FOREIGN KEY (fk_livro_id_livro, fk_livro_id_item_acervo)
    REFERENCES livro (id_livro, id_item_acervo)
    ON DELETE SET NULL;
 
ALTER TABLE item_colaborador ADD CONSTRAINT FK_item_colaborador_1
    FOREIGN KEY (fk_jornal_id_jornal, fk_jornal_id_item_acervo)
    REFERENCES jornal (id_jornal, id_item_acervo)
    ON DELETE SET NULL;
 
ALTER TABLE item_colaborador ADD CONSTRAINT FK_item_colaborador_2
    FOREIGN KEY (fk_revista_id_revista, fk_revista_id_item_acervo)
    REFERENCES revista (id_revista, id_item_acervo)
    ON DELETE SET NULL;
 
ALTER TABLE item_colaborador ADD CONSTRAINT FK_item_colaborador_3
    FOREIGN KEY (fk_livro_id_livro, fk_livro_id_item_acervo)
    REFERENCES livro (id_livro, id_item_acervo)
    ON DELETE SET NULL;
 
ALTER TABLE item_colaborador ADD CONSTRAINT FK_item_colaborador_4
    FOREIGN KEY (fk_colaborador_id_colaborador)
    REFERENCES colaborador (id_colaborador)
    ON DELETE SET NULL;
 
ALTER TABLE item_assunto ADD CONSTRAINT FK_item_assunto_1
    FOREIGN KEY (fk_jornal_id_jornal, fk_jornal_id_item_acervo)
    REFERENCES jornal (id_jornal, id_item_acervo)
    ON DELETE SET NULL;
 
ALTER TABLE item_assunto ADD CONSTRAINT FK_item_assunto_2
    FOREIGN KEY (fk_revista_id_revista, fk_revista_id_item_acervo)
    REFERENCES revista (id_revista, id_item_acervo)
    ON DELETE SET NULL;
 
ALTER TABLE item_assunto ADD CONSTRAINT FK_item_assunto_3
    FOREIGN KEY (fk_livro_id_livro, fk_livro_id_item_acervo)
    REFERENCES livro (id_livro, id_item_acervo)
    ON DELETE SET NULL;
 
ALTER TABLE item_assunto ADD CONSTRAINT FK_item_assunto_4
    FOREIGN KEY (fk_assunto_id_assunto)
    REFERENCES assunto (id_assunto)
    ON DELETE SET NULL;
