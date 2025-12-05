package eglv.sistemagerenciamentoacervos.dao;

import eglv.sistemagerenciamentoacervos.db.DbConnector;
import eglv.sistemagerenciamentoacervos.model.Assunto;
import eglv.sistemagerenciamentoacervos.model.Colaborador;
import eglv.sistemagerenciamentoacervos.model.Livro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {

    // ==========================
    // INSERIR
    // ==========================
    public void inserir(Livro l) throws SQLException {
        final String sql = "INSERT INTO livro " +
                "(codigo_livro, titulo, subtitulo, isbn, ano_publicacao, localizacao_acervo, " +
                "numero_paginas, edicao, idioma, quantidade, capa, fk_editora_id_editora) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, l.getCodigo_livro());
            ps.setString(2, l.getTitulo());
            ps.setString(3, l.getSubtitulo());
            ps.setString(4, l.getIsbn());

            if (l.getAno_publicacao() != null)
                ps.setInt(5, l.getAno_publicacao());
            else
                ps.setNull(5, Types.INTEGER);

            ps.setString(6, l.getLocalizacao_acervo());
            ps.setString(7, l.getNumero_paginas());
            ps.setString(8, l.getEdicao());
            ps.setString(9, l.getIdioma());

            if (l.getQuantidade() != null)
                ps.setInt(10, l.getQuantidade());
            else
                ps.setNull(10, Types.INTEGER);

            ps.setBytes(11, l.getCapa());

            if (l.getEditora() != null)
                ps.setInt(12, l.getEditora().getId_editora());
            else
                ps.setNull(12, Types.INTEGER);

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) l.setId_livro(rs.getInt(1));
            }
        }
    }

    // ==========================
    // INSERIR RELACIONAMENTOS
    // ==========================
    public void inserirAssuntos(Livro l) throws SQLException {
        final String sql = "INSERT INTO item_assunto (fk_livro_id_livro, fk_assunto_id_assunto) VALUES (?, ?)";
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Assunto a : l.getAssuntos()) {
                ps.setInt(1, l.getId_livro());
                ps.setInt(2, a.getId_assunto());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public void inserirColaboradores(Livro l) throws SQLException {
        final String sql = "INSERT INTO item_colaborador (fk_livro_id_livro, fk_colaborador_id_colaborador) VALUES (?, ?)";
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Colaborador c : l.getColaboradores()) {
                ps.setInt(1, l.getId_livro());
                ps.setInt(2, c.getId_colaborador());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    // ==========================
    // UPDATE
    // ==========================
    public void update(Livro l) throws SQLException {
        final String sql = "UPDATE livro SET " +
                "codigo_livro=?, titulo=?, subtitulo=?, isbn=?, ano_publicacao=?, " +
                "localizacao_acervo=?, numero_paginas=?, edicao=?, idioma=?, quantidade=?, capa=?, " +
                "fk_editora_id_editora=? " +
                "WHERE id_livro=?";

        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, l.getCodigo_livro());
            ps.setString(2, l.getTitulo());
            ps.setString(3, l.getSubtitulo());
            ps.setString(4, l.getIsbn());

            if (l.getAno_publicacao() != null)
                ps.setInt(5, l.getAno_publicacao());
            else
                ps.setNull(5, Types.INTEGER);

            ps.setString(6, l.getLocalizacao_acervo());
            ps.setString(7, l.getNumero_paginas());
            ps.setString(8, l.getEdicao());
            ps.setString(9, l.getIdioma());

            if (l.getQuantidade() != null)
                ps.setInt(10, l.getQuantidade());
            else
                ps.setNull(10, Types.INTEGER);

            ps.setBytes(11, l.getCapa());

            if (l.getEditora() != null)
                ps.setInt(12, l.getEditora().getId_editora());
            else
                ps.setNull(12, Types.INTEGER);

            ps.setInt(13, l.getId_livro());

            ps.executeUpdate();
        }
    }

    // ==========================
    // DELETE
    // ==========================
    public void delete(int id) throws SQLException {
        final String sql = "DELETE FROM livro WHERE id_livro=?";
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // ==========================
    // BUSCAS ESPECÍFICAS
    // ==========================
    public Livro buscarPorId(int id) throws SQLException {
        final String sql = "SELECT * FROM livro WHERE id_livro=?";
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapResultSetToLivro(rs);
            }
        }
        return null;
    }

    public Livro buscarPorCodigo(String codigo) throws SQLException {
        final String sql = "SELECT * FROM livro WHERE codigo_livro=?";
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapResultSetToLivro(rs);
            }
        }
        return null;
    }

    public Livro buscarPorTitulo(String titulo) throws SQLException {
        final String sql = "SELECT * FROM livro WHERE titulo=?";
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, titulo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapResultSetToLivro(rs);
            }
        }
        return null;
    }

    public Livro buscarPorISBN(String isbn) throws SQLException {
        final String sql = "SELECT * FROM livro WHERE isbn=?";
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, isbn);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapResultSetToLivro(rs);
            }
        }
        return null;
    }

    // ==========================
    // LISTAR TODOS
    // ==========================
    public List<Livro> listarTodos() throws SQLException {
        final String sql = "SELECT * FROM livro";
        List<Livro> list = new ArrayList<>();
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSetToLivro(rs));
            }
        }
        return list;
    }

    // ==========================
    // AUXILIAR
    // ==========================
    private Livro mapResultSetToLivro(ResultSet rs) throws SQLException {
        Livro l = new Livro();
        l.setId_livro(rs.getInt("id_livro"));
        l.setCodigo_livro(rs.getString("codigo_livro"));
        l.setTitulo(rs.getString("titulo"));
        l.setSubtitulo(rs.getString("subtitulo"));
        l.setIsbn(rs.getString("isbn"));
        l.setAno_publicacao((Integer) rs.getObject("ano_publicacao"));
        l.setLocalizacao_acervo(rs.getString("localizacao_acervo"));
        l.setNumero_paginas(rs.getString("numero_paginas"));
        l.setEdicao(rs.getString("edicao"));
        l.setIdioma(rs.getString("idioma"));
        l.setQuantidade((Integer) rs.getObject("quantidade"));
        l.setCapa(rs.getBytes("capa"));
        return l;
    }
}
