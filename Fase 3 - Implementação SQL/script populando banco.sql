/*alguns ids não estão iniciando do 1 pois já havia testando antes com outros exemplos*/
INSERT INTO ASSUNTO (descricao)
VALUES ('Cultura Afro-Brasileira'),
       ('Cultura Africana'),
       ('Movimento Negro Brasil'),
       ('Teatro'),
       ('Escravidão'),
       ('História'),
       ('Sociologia'),
       ('Literatura Negra'),
       ('Juventude Negra');

INSERT INTO editora(nome,localizacao)
VALUES ('Objetiva','São Paulo,SP, BR'),
       ('Companhia das Letras','São Paulo,SP, BR'),
       ('Rocco','Rio de Janeiro,RJ, BR'),
       ('Intrínseca','Rio de Janeiro,RJ, BR'),
       ('Moderna','São Paulo,SP, BR'),
       ('Editora Vozes', 'Petrópolis,RJ, BR');

INSERT INTO colaborador (nome,sobrenome,nacionalidade,tipo)
VALUES ('Lélia','Gonzalez','Brasileiro','Autor'),
       ('Abdias','do Nascimento','Brasileiro','Autor'),
       ('Beatriz','Nascimento','Brasileiro','Autor'),
       ('Carolina','Maria de Jesus','Brasileiro','Autor'),
       ('Heloisa','Buarque','Brasileiro','Organizador'),
       ('Kadir','Nelson','Americano','Organizador'),
       ('Lawrence','Bottmann','Espanhol','Ilustradores'),
       ('Robin','Myers','Americano','Tradutor')

INSERT INTO Usuario(cpf,nome,sobrenome,email,telefone,tipo,senha)
VALUES ('00000000000','vanessa','cezar','vanessacezar@ufn.edu.br','55999336688','administrador','1234'),
       ('00000000011','thiago','nascimento','thiagonascimento@ufn.edu.br','55999881133','leitor','1546'),
       ('00000001111','guilherme','frazzon','guifrazzon@ufn.edu.br','55999446655','administrador','7894'),
       ('00000110000','luiza','karlec','luizaka@ufn.edu.br','55998836688','leitor','9874'),
       ('00011000000','eric','martins','eric martins@ufn.edu.br','55981554466','leitor','6541');

INSERT INTO livro(codigo_livro, titulo, subtitulo, isbn, ano_publicacao, localizacao_acervo, numero_paginas, edicao, idioma, quantidade,fk_editora_id_editora)
VALUES ('LN19821', 'Lugar de Negro', NULL, '9788571108008', 1982, 'EA1', '128', '1ª', 'Português', 3, 28),
       ('FPB1987', 'Festas Populares no Brasil', NULL, '9788535906376', 1987, 'EA1', '144', '1ª', 'Português', 2, 29),
       ('GNB1978', 'O Genocídio do Negro Brasileiro', 'Processo de um Racismo Mascarado', '9788571649877', 1978, 'EA2', '250', '2ª', 'Português', 4, 30),
       ('SMN1951', 'Sortilégio', 'Mistério Negro', '9788571641123', 1951, 'EA2', '98', '1ª', 'Português', 2, 30),
       ('BN2021', 'Beatriz Nascimento: O Negro e o Feminino', NULL, '9786559210387', 2021, 'EA3', '320', '1ª', 'Português', 3, 31),
       ('QIPE2018', 'Quilombola e Intelectual', 'Possibilidades nos Estudos da História', '9788582171583', 2018, 'EA3', '256', '1ª', 'Português', 2, 27),
       ('QD14960', 'Quarto de Despejo', 'Diário de uma Favelada', '9788535909551', 1960, 'EB1', '200', '10ª', 'Português', 5, 28),
       ('CADE1961', 'Casa de Alvenaria', 'Diário de uma Ex-Favelada', '9788535924035', 1961, 'EB1', '224', '1ª', 'Português', 4,27);


INSERT INTO jornal (codigo_jornal,pais,estado,cidade,data,localizacao_acervo,numero_paginas,edicao,idioma,titulo,subtitulo, quantidade,fk_editora_id_editora)
VALUES('JB75', 'BR', 'RJ', 'Rio de Janeiro', '1975-05-12', 'AJ1', '32', '14532', 'Português', 'Jornal do Brasil', 'Política e Sociedade', 3, 27),
      ('FSP87', 'BR', 'SP', 'São Paulo', '1985-03-20', 'AJ2', '40', '15987', 'Português', 'Folha de S.Paulo', 'Economia e Atualidades', 4, 28),
      ('TNYT23', 'EUA', 'NY', 'New York', '1990-10-01', 'AJ3', '48', ' 23015', 'Inglês', 'The New York Times', 'World News Highlights', 2, 29),
      ('TGW01', 'RU', NULL, 'London', '2001-04-15', 'AJ4', '56', 'International', 'Inglês', 'The Guardian Weekly', 'Global Reports & Features', 2, 30),
      ('CP98', 'BR', 'RS', 'Porto Alegre', '1978-09-08', 'AJ5', '28', '9870', 'Português', 'Correio do Povo', 'Caderno de Cultura e Sociedade', 3, 31);

INSERT INTO revista (codigo_revista, volume, periodicidade, mes,ano,localizacao_acervo,numero_paginas,edicao,idioma,titulo,subtitulo,quantidade,fk_editora_id_editora)
VALUES ('RB02', '12', 'Mensal', 5, 2002, 'ER1', '80', 'Edição 12', 'Português', 'Raça Brasil', 'Histórias e Cultura Afro-Brasileira', 4, 27),
       ('NG99', '50', 'Mensal', 8, 1999, 'ER2', '120', 'Volume 50', 'Inglês', 'National Geographic', 'África: História, Povos e Territórios', 3, 28),
       ('EB87', '22', 'Mensal', 11, 1987, 'ER3', '96', 'Issue 22', 'Inglês','Ebony', 'Black Culture & Lifestyle', 2, 29),
       ('EAA95', '15', 'Trimestral', 3, 1995, 'ER4', '110', 'Volume 15', 'Português', 'Estudos Afro-Asiáticos', 'Pesquisas sobre África e Diáspora', 3, 30),
       ('HV08', '9', 'Bimestral', 10, 2008, 'ER5', '88', 'Edição 9', 'Português', 'História Viva', 'Especial África e Cultura Negra', 5, 31);

INSERT INTO exemplar (codigo, fk_jornal_id_jornal, fk_revista_id_revista, fk_livro_id_livro)
VALUES
    -- livros
    ('EXP001', NULL, NULL, 2),
    ('EXP002', NULL, NULL, 4),
    ('EXP003', NULL, NULL, 6), 
    ('EXP004', NULL, NULL, 8), 
    -- Exemplares de jornais
    ('EXP005', 2, NULL, NULL),   
    ('EXP006', 7, NULL, NULL),   
    ('EXP007', 9, NULL, NULL),   
    ('EXP008', 11, NULL, NULL),  
    -- Exemplares de revistas
    ('EXP009', NULL, 1, NULL),
    ('EXP010', NULL, 5, NULL);  


INSERT INTO item_colaborador (fk_jornal_id_jornal, fk_revista_id_revista,fk_livro_id_livro,fk_colaborador_id_colaborador)
VALUES 
       -- livros
       (NULL, NULL, 1, 16),
       (NULL, NULL, 1, 20),
       (NULL, NULL, 1, 22),
       (NULL, NULL, 2, 16),
       (NULL, NULL, 2, 21),
       (NULL, NULL, 2, 22),
       (NULL, NULL, 3, 17),
       (NULL, NULL, 3, 20),
       (NULL, NULL, 3, 22),
       (NULL, NULL, 4, 17),
       (NULL, NULL, 4, 21),
       (NULL, NULL, 4, 22),
       (NULL, NULL, 5, 17),
       (NULL, NULL, 5, 20),
       (NULL, NULL, 5, 22),
       (NULL, NULL, 6, 18),
       (NULL, NULL, 6, 21),
       (NULL, NULL, 6, 23),
       (NULL, NULL, 7, 18),
       (NULL, NULL, 7, 20),
       (NULL, NULL, 7, 22),
       (NULL, NULL, 8, 19),
       (NULL, NULL, 8, 20),
       (NULL, NULL, 8, 23),
       -- revista
        (NULL, 1, NULL, 20),
        (NULL, 1, NULL, 22),
        (NULL, 2, NULL, 21),
        (NULL, 2, NULL, 22),
        (NULL, 3, NULL, 21),
        (NULL, 3, NULL, 22),
        (NULL, 4, NULL, 20),
        (NULL, 4, NULL, 22),
        (NULL, 5, NULL, 20),
        (NULL, 5, NULL, 22),
        -- jornal
        (1, NULL, NULL, 20),
        (1, NULL, NULL, 23),
        (2, NULL, NULL, 20),
        (2, NULL, NULL, 23),
        (7, NULL, NULL, 20),
        (7, NULL, NULL, 23),
        (8, NULL, NULL, 20),
        (8, NULL, NULL, 23),
        (9, NULL, NULL, 21),
        (9, NULL, NULL, 23),
        (10, NULL, NULL, 21),
        (10, NULL, NULL, 23),
        (11, NULL, NULL, 20),
        (11, NULL, NULL, 23);

        -- somente livros
INSERT INTO item_assunto (fk_livro_id_livro, fk_assunto_id_assunto)
VALUES (1, 20),
       (1, 23),
       (1, 27),
       (1, 26),
       (2, 19),
       (2, 24),
       (2, 21),
       (2, 25),
       (3, 11),
       (3, 18),
       (3, 3),
       (3, 22);

       --somente jornais
INSERT INTO item_assunto (fk_jornal_id_jornal, fk_assunto_id_assunto)
VALUES (1, 20),
       (1, 24),
       (1, 27),
       (2, 19),
       (2, 21), 
       (2, 23); 

       -- somente revistas

INSERT INTO item_assunto (fk_revista_id_revista, fk_assunto_id_assunto)
VALUES (1, 26),
       (1, 25),
       (1, 22),
       (2, 17),
       (2, 12),
       (2, 18);




