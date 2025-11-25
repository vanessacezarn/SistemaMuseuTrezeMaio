/* Modelo Logico banco: */

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
    isbn VARCHAR,
    ano_publicacao INTEGER,
    id_livro INTEGER,
    codigo_livro VARCHAR,
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
    PRIMARY KEY (id_livro, id_item_acervo),
    UNIQUE (isbn, id_livro, codigo_livro, id_item_acervo)
);

CREATE TABLE editora (
    localizacao VARCHAR,
    id_editora INTEGER PRIMARY KEY,
    nome VARCHAR
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