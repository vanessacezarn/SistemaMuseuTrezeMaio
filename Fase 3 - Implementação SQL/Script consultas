-- 1) itens com seus colaboradores e editores
SELECT 
    l.titulo AS Livro,
    j.titulo AS Jornal,
    r.titulo AS Revista,
    CONCAT(c.nome, ' ', c.sobrenome) AS Colaborador,
    c.tipo AS Tipo_Colaborador,
    e.nome AS Editora
FROM item_colaborador ic
LEFT JOIN livro AS l ON ic.fk_livro_id_livro = l.id_livro
LEFT JOIN jornal AS j ON ic.fk_jornal_id_jornal = j.id_jornal
LEFT JOIN revista AS r ON ic.fk_revista_id_revista = r.id_revista
LEFT JOIN colaborador AS c ON ic.fk_colaborador_id_colaborador = c.id_colaborador
LEFT JOIN editora AS e ON (l.fk_editora_id_editora = e.id_editora OR j.fk_editora_id_editora = e.id_editora OR r.fk_editora_id_editora = e.id_editora)
ORDER BY Colaborador, Livro, Jornal, Revista;

-- 2) colaboradores que trabalharam em mais um tipo de item
SELECT 
    c.nome, c.sobrenome, c.tipo,
    COUNT(DISTINCT ic.fk_livro_id_livro) AS Livros,
    COUNT(DISTINCT ic.fk_jornal_id_jornal) AS Jornais,
    COUNT(DISTINCT ic.fk_revista_id_revista) AS Revistas
FROM colaborador AS c
LEFT JOIN item_colaborador AS ic ON c.id_colaborador = ic.fk_colaborador_id_colaborador
GROUP BY c.nome, c.sobrenome, c.tipo
HAVING COUNT(DISTINCT ic.fk_livro_id_livro) + COUNT(DISTINCT ic.fk_jornal_id_jornal) + COUNT(DISTINCT ic.fk_revista_id_revista) > 1
ORDER BY Livros DESC, Jornais DESC, Revistas DESC;

-- 3) revistas publicadas entre 2000 e 2010
SELECT titulo, ano, mes, volume, periodicidade
FROM revista
WHERE ano BETWEEN 2000 AND 2010
ORDER BY ano, mes;

-- 4) livros com mais de um colaborador
SELECT l.titulo, COUNT(ic.fk_colaborador_id_colaborador) AS Qtd_Colaboradores
FROM livro AS l
JOIN item_colaborador AS ic ON l.id_livro = ic.fk_livro_id_livro
GROUP BY l.titulo
HAVING COUNT(ic.fk_colaborador_id_colaborador) > 1;

-- 5) assuntos mais abordados por livros
SELECT a.descricao, COUNT(*) AS Qtd_Livros
FROM item_assunto AS ia
JOIN assunto AS a ON ia.fk_assunto_id_assunto = a.id_assunto
WHERE ia.fk_livro_id_livro IS NOT NULL
GROUP BY a.descricao
ORDER BY Qtd_Livros DESC;

-- 6) jornais no estado do RS
SELECT titulo, cidade, estado, data
FROM jornal AS J
WHERE j.estado = 'RS'
ORDER BY data;

-- 7) itens com mais de um assunto
SELECT l.titulo AS Livro, COUNT(ia.fk_assunto_id_assunto) AS Qtd_Assuntos
FROM item_assunto AS ia
JOIN livro AS l ON ia.fk_livro_id_livro = l.id_livro
GROUP BY l.titulo
HAVING COUNT(ia.fk_assunto_id_assunto) > 1
ORDER BY Qtd_Assuntos DESC;

-- 8) quantidade de livros por editora e idioma
SELECT e.nome AS Editora, l.idioma, COUNT(*) AS Qtd_Livros
FROM livro AS l
JOIN editora AS e ON l.fk_editora_id_editora = e.id_editora
GROUP BY e.nome, l.idioma
ORDER BY e.nome, Qtd_Livros DESC;

-- 9) revistas com mais de 100 paginas publicadas antes de 2005
SELECT titulo, ano, mes, numero_paginas
FROM revista
WHERE numero_paginas > 100 AND ano < 2005
ORDER BY ano, mes;

-- 10) quantidade de usuarios por tipo
SELECT tipo, COUNT(*) AS Qtd_Usuarios
FROM usuario
GROUP BY tipo;
