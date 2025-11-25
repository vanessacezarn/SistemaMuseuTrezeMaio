/* Modelo_Logico: */

CREATE TABLE Usuario (
    cpf VARCHAR,
    telefone VARCHAR,
    id_usuario INTEGER PRIMARY KEY,
    nome VARCHAR,
    email VARCHAR,
    sobrenome VARCHAR,
    tipo VARCHAR,
    UNIQUE (cpf, email, telefone, id_usuario)
);

CREATE TABLE livro (
    isbn VARCHAR,
    ano_publicacao INTEGER,
    id_livro INTEGER PRIMARY KEY,
    codigo_livro VARCHAR,
    localizacao_acervo VARCHAR,
    numero_paginas VARCHAR,
    tipo VARCHAR,
    edicao INTEGER,
    subtitulo VARCHAR,
    titulo VARCHAR,
    idioma VARCHAR,
    quantidade INTEGER,
    capa BLOB,
    fk_editora_id_editora INTEGER,
    UNIQUE (codigo_livro, id_livro, isbn)
);

CREATE TABLE editora (
    localizacao VARCHAR,
    id_editora INTEGER PRIMARY KEY,
    nome VARCHAR,
    UNIQUE (nome, id_editora)
);

CREATE TABLE assunto (
    id_assunto INTEGER PRIMARY KEY UNIQUE,
    descricao VARCHAR
);

CREATE TABLE colaborador (
    nome VARCHAR,
    sobrenome VARCHAR,
    nacionalidade VARCHAR,
    tipo VARCHAR,
    id_colaborador INTEGER PRIMARY KEY UNIQUE
);

CREATE TABLE revista (
    volume INTEGER,
    id_revista INTEGER PRIMARY KEY,
    periodicidade VARCHAR,
    mes INTEGER,
    ano INTEGER,
    codigo_revista VARCHAR,
    localizacao_acervo VARCHAR,
    numero_paginas VARCHAR,
    tipo VARCHAR,
    edicao INTEGER,
    subtitulo VARCHAR,
    titulo VARCHAR,
    idioma VARCHAR,
    quantidade INTEGER,
    capa BLOB,
    fk_editora_id_editora INTEGER,
    UNIQUE (id_revista, codigo_revista)
);

CREATE TABLE jornal (
    id_jornal INTEGER PRIMARY KEY,
    pais VARCHAR,
    estado VARCHAR,
    codigo_jornal VARCHAR,
    cidade VARCHAR,
    data DATE,
    localizacao_acervo VARCHAR,
    numero_paginas VARCHAR,
    tipo VARCHAR,
    edicao INTEGER,
    subtitulo VARCHAR,
    titulo VARCHAR,
    idioma VARCHAR,
    quantidade INTEGER,
    capa BLOB,
    fk_editora_id_editora INTEGER,
    UNIQUE (id_jornal, codigo_jornal)
);

CREATE TABLE exemplar (
    id_exemplar INTEGER PRIMARY KEY,
    codigo VARCHAR,
    fk_jornal_id_jornal INTEGER,
    fk_revista_id_revista INTEGER,
    fk_livro_id_livro INTEGER,
    UNIQUE (id_exemplar, codigo)
);

CREATE TABLE item_colaborador (
    fk_jornal_id_jornal INTEGER,
    fk_revista_id_revista INTEGER,
    fk_livro_id_livro INTEGER,
    fk_colaborador_id_colaborador INTEGER
);

CREATE TABLE item_assunto (
    fk_jornal_id_jornal INTEGER,
    fk_revista_id_revista INTEGER,
    fk_livro_id_livro INTEGER,
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
    FOREIGN KEY (fk_jornal_id_jornal, fk_jornal_Atributo_1???)
    REFERENCES jornal (id_jornal, ???)
    ON DELETE SET NULL;
 
ALTER TABLE exemplar ADD CONSTRAINT FK_exemplar_3
    FOREIGN KEY (fk_revista_id_revista, fk_revista_Atributo_1???)
    REFERENCES revista (id_revista, ???)
    ON DELETE SET NULL;
 
ALTER TABLE exemplar ADD CONSTRAINT FK_exemplar_4
    FOREIGN KEY (fk_livro_id_livro, fk_livro_Atributo_1???)
    REFERENCES livro (id_livro, ???)
    ON DELETE SET NULL;
 
ALTER TABLE item_colaborador ADD CONSTRAINT FK_item_colaborador_1
    FOREIGN KEY (fk_jornal_id_jornal, fk_jornal_Atributo_1???)
    REFERENCES jornal (id_jornal, ???)
    ON DELETE SET NULL;
 
ALTER TABLE item_colaborador ADD CONSTRAINT FK_item_colaborador_2
    FOREIGN KEY (fk_revista_id_revista, fk_revista_Atributo_1???)
    REFERENCES revista (id_revista, ???)
    ON DELETE SET NULL;
 
ALTER TABLE item_colaborador ADD CONSTRAINT FK_item_colaborador_3
    FOREIGN KEY (fk_livro_id_livro, fk_livro_Atributo_1???)
    REFERENCES livro (id_livro, ???)
    ON DELETE SET NULL;
 
ALTER TABLE item_colaborador ADD CONSTRAINT FK_item_colaborador_4
    FOREIGN KEY (fk_colaborador_id_colaborador)
    REFERENCES colaborador (id_colaborador)
    ON DELETE SET NULL;
 
ALTER TABLE item_assunto ADD CONSTRAINT FK_item_assunto_1
    FOREIGN KEY (fk_jornal_id_jornal, fk_jornal_Atributo_1???)
    REFERENCES jornal (id_jornal, ???)
    ON DELETE SET NULL;
 
ALTER TABLE item_assunto ADD CONSTRAINT FK_item_assunto_2
    FOREIGN KEY (fk_revista_id_revista, fk_revista_Atributo_1???)
    REFERENCES revista (id_revista, ???)
    ON DELETE SET NULL;
 
ALTER TABLE item_assunto ADD CONSTRAINT FK_item_assunto_3
    FOREIGN KEY (fk_livro_id_livro, fk_livro_Atributo_1???)
    REFERENCES livro (id_livro, ???)
    ON DELETE SET NULL;
 
ALTER TABLE item_assunto ADD CONSTRAINT FK_item_assunto_4
    FOREIGN KEY (fk_assunto_id_assunto)
    REFERENCES assunto (id_assunto)
    ON DELETE SET NULL;