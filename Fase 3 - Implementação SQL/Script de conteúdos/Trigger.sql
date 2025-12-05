-- TRIGGER AUMENTAR QUANTIDADE

CREATE TRIGGER trg_aumenta_qtd_jornal
ON exemplar 
AFTER INSERT
AS
BEGIN
    IF EXISTS (SELECT 1
                FROM jornal as j
                INNER JOIN inserted i ON j.id_jornal = i.fk_jornal_id_jornal
                WHERE j.quantidade IS NULL)
        BEGIN
            UPDATE j
            SET j.quantidade = 1
            FROM jornal as j
            INNER JOIN inserted as i
                ON j.id_jornal = i.fk_jornal_id_jornal
            WHERE j.quantidade IS NULL;
        END
    ELSE
    BEGIN
        UPDATE j
        SET j.quantidade = j.quantidade + 1
        FROM jornal as j
        INNER JOIN inserted as i
            ON j.id_jornal = i.fk_jornal_id_jornal;
    END;
END;
GO;

CREATE TRIGGER trg_aumenta_qtd_revista
ON exemplar 
AFTER INSERT
AS
BEGIN
    IF EXISTS (SELECT 1
                FROM revista r
                INNER JOIN inserted i ON r.id_revista = i.fk_revista_id_revista
                WHERE r.quantidade IS NULL)
        BEGIN
            UPDATE r
            SET r.quantidade = 1
            FROM revista as r
            INNER JOIN inserted as i
                ON r.id_revista = i.fk_revista_id_revista
            WHERE r.quantidade IS NULL;
        END
    ELSE
    BEGIN
        UPDATE r
        SET r.quantidade = r.quantidade + 1
        FROM revista as r
        INNER JOIN inserted as i
            ON r.id_revista = i.fk_revista_id_revista;
    END;
END;
GO;

CREATE TRIGGER trg_aumenta_qtd_livro
ON exemplar 
AFTER INSERT
AS
BEGIN
    IF EXISTS (SELECT 1
               FROM livro l
               INNER JOIN inserted i 
                   ON l.id_livro = i.fk_livro_id_livro
               WHERE l.quantidade IS NULL)
    BEGIN
        UPDATE l
        SET l.quantidade = 1
        FROM livro AS l
        INNER JOIN inserted AS i
            ON l.id_livro = i.fk_livro_id_livro
        WHERE l.quantidade IS NULL;
    END
    ELSE
    BEGIN
        UPDATE l
        SET l.quantidade = l.quantidade + 1
        FROM livro AS l
        INNER JOIN inserted AS i
            ON l.id_livro = i.fk_livro_id_livro;
    END;
END;
GO;

-- TRIGGER DIMINUIR QUANTIDADE
CREATE TRIGGER trg_diminui_qtd_jornal
ON exemplar 
AFTER DELETE
AS
BEGIN
    IF EXISTS (SELECT 1
                FROM jornal j
                INNER JOIN deleted d 
                    ON j.id_jornal = d.fk_jornal_id_jornal
                WHERE j.quantidade IS NULL)
    BEGIN
        UPDATE j
        SET j.quantidade = 0
        FROM jornal AS j
        INNER JOIN deleted AS d
            ON j.id_jornal = d.fk_jornal_id_jornal
        WHERE j.quantidade IS NULL;
    END
    ELSE
    BEGIN
        UPDATE j
        SET j.quantidade = j.quantidade - 1
        FROM jornal AS j
        INNER JOIN deleted AS d
            ON j.id_jornal = d.fk_jornal_id_jornal;
    END;
END;
GO;

CREATE TRIGGER trg_diminui_qtd_revista
ON exemplar 
AFTER DELETE
AS
BEGIN
    IF EXISTS (SELECT 1
                FROM revista r
                INNER JOIN deleted d 
                    ON r.id_revista = d.fk_revista_id_revista
                WHERE r.quantidade IS NULL)
    BEGIN
        UPDATE r
        SET r.quantidade = 0
        FROM revista AS r
        INNER JOIN deleted AS d
            ON r.id_revista = d.fk_revista_id_revista
        WHERE r.quantidade IS NULL;
    END
    ELSE
    BEGIN
        UPDATE r
        SET r.quantidade = r.quantidade - 1
        FROM revista AS r
        INNER JOIN deleted AS d
            ON r.id_revista = d.fk_revista_id_revista;
    END;
END;
GO;

CREATE TRIGGER trg_diminui_qtd_livro
ON exemplar 
AFTER DELETE
AS
BEGIN
    IF EXISTS (SELECT 1
               FROM livro l
               INNER JOIN deleted d 
                   ON l.id_livro = d.fk_livro_id_livro
               WHERE l.quantidade IS NULL)
    BEGIN
        UPDATE l
        SET l.quantidade = 0
        FROM livro AS l
        INNER JOIN deleted AS d
            ON l.id_livro = d.fk_livro_id_livro
        WHERE l.quantidade IS NULL;
    END
    ELSE
    BEGIN
        UPDATE l
        SET l.quantidade = l.quantidade - 1
        FROM livro AS l
        INNER JOIN deleted AS d
            ON l.id_livro = d.fk_livro_id_livro;
    END;
END;