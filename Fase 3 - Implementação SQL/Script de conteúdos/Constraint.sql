-- CONSTRAINT UNIQUE

ALTER TABLE editora
ADD CONSTRAINT unq_nome_localizacao UNIQUE (nome, localizacao);

ALTER TABLE assunto
ADD CONSTRAINT UQ_assunto_descricao UNIQUE (descricao);

ALTER TABLE colaborador
ADD CONSTRAINT unq_nomeCompleto_tipo UNIQUE (nome,sobrenome, tipo)