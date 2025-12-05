-- VIEW

CREATE VIEW vw_VisaoGeralLivro 
AS
SELECT 
    L.id_livro AS ID_Livro,
    L.codigo_livro AS Codigo,
    L.titulo AS Titulo,
    L.subtitulo AS Subtitulo,
    L.isbn AS ISBN,
    L.ano_publicacao AS Ano,
    L.localizacao_acervo AS Localizacao,
    L.numero_paginas AS NumeroPaginas,
    L.edicao AS Edicao,
    L.idioma AS Idioma,
    L.quantidade AS Quantidade,
    E.nome AS Editora,
    E.localizacao AS LocalizacaoEditora,
    A.descricao AS Assunto,
    CONCAT(C.nome, ' ', C.sobrenome, ' (', C.tipo, ')') AS Colaborador
FROM livro L
LEFT JOIN editora E ON L.fk_editora_id_editora = E.id_editora
LEFT JOIN item_assunto IA ON IA.fk_livro_id_livro = L.id_livro
LEFT JOIN assunto A ON A.id_assunto = IA.fk_assunto_id_assunto
LEFT JOIN item_colaborador IC ON IC.fk_livro_id_livro = L.id_livro
LEFT JOIN colaborador C ON C.id_colaborador = IC.fk_colaborador_id_colaborador;
GO;

SELECT * FROM vw_VisaoGeralLivro;
GO;

CREATE VIEW vw_VisaoGeralJornal AS
SELECT 
    J.id_jornal AS ID_Jornal,
    J.codigo_jornal AS Codigo,
    J.titulo AS Titulo,
    J.subtitulo AS Subtitulo,
    J.pais AS Pais,
    J.estado AS Estado,
    J.cidade AS Cidade,
    J.data AS DataPublicacao,
    J.localizacao_acervo AS Localizacao,
    J.numero_paginas AS NumeroPaginas,
    J.edicao AS Edicao,
    J.idioma AS Idioma,
    J.quantidade AS Quantidade,
    E.nome AS Editora,
    E.localizacao AS LocalizacaoEditora,
    A.descricao AS Assunto,
    CONCAT(C.nome, ' ', C.sobrenome, ' (', C.tipo, ')') AS Colaborador
FROM jornal J
LEFT JOIN editora E ON J.fk_editora_id_editora = E.id_editora
LEFT JOIN item_assunto IA ON IA.fk_jornal_id_jornal = J.id_jornal
LEFT JOIN assunto A ON A.id_assunto = IA.fk_assunto_id_assunto
LEFT JOIN item_colaborador IC ON IC.fk_jornal_id_jornal = J.id_jornal
LEFT JOIN colaborador C ON C.id_colaborador = IC.fk_colaborador_id_colaborador;
GO;

SELECT * FROM vw_VisaoGeralJornal;
GO;

CREATE VIEW vw_VisaoGeralRevista AS
SELECT 
    R.id_revista AS ID_Revista,
    R.codigo_revista AS Codigo,
    R.titulo AS Titulo,
    R.subtitulo AS Subtitulo,
    R.mes AS Mes,
    R.ano AS Ano,
    R.localizacao_acervo AS Localizacao,
    R.numero_paginas AS NumeroPaginas,
    R.edicao AS Edicao,
    R.idioma AS Idioma,
    R.quantidade AS Quantidade,
    E.nome AS Editora,
    E.localizacao AS LocalizacaoEditora,
    A.descricao AS Assunto,
    CONCAT(C.nome, ' ', C.sobrenome, ' (', C.tipo, ')') AS Colaborador
FROM revista R
LEFT JOIN editora E ON R.fk_editora_id_editora = E.id_editora
LEFT JOIN item_assunto IA ON IA.fk_revista_id_revista = R.id_revista
LEFT JOIN assunto A ON A.id_assunto = IA.fk_assunto_id_assunto
LEFT JOIN item_colaborador IC ON IC.fk_revista_id_revista = R.id_revista
LEFT JOIN colaborador C ON C.id_colaborador = IC.fk_colaborador_id_colaborador;
GO;

SELECT * FROM vw_VisaoGeralRevista;