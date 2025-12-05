package eglv.sistemagerenciamentoacervos.dao;

import eglv.sistemagerenciamentoacervos.db.DbConnector;
import eglv.sistemagerenciamentoacervos.model.Assunto;
import eglv.sistemagerenciamentoacervos.model.Colaborador;
import eglv.sistemagerenciamentoacervos.model.Editora;
import eglv.sistemagerenciamentoacervos.model.Jornal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JornalDAO {

    public void inserir(Jornal j) throws SQLException {
        final String sql = "INSERT INTO dbo.jornal " +
                "(codigo_jornal, pais, estado, cidade, data, localizacao_acervo, numero_paginas," +
                " edicao, idioma, titulo, subtitulo, capa, fk_editora_id_editora) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, j.getCodigo_jornal());
            ps.setString(2, j.getPais());
            ps.setString(3, j.getEstado());
            ps.setString(4, j.getCidade());
            ps.setDate(5, (Date) j.getData());
            ps.setString(6, j.getLocalizacao_acervo());
            ps.setString(7, j.getNumero_paginas());
            ps.setString(8, j.getEdicao());
            ps.setString(9, j.getIdioma());
            ps.setString(10, j.getTitulo());
            ps.setString(11, j.getSubtitulo());
            ps.setBytes(12, j.getCapa());
            ps.setInt(13, j.getEditora().getId_editora());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) j.setId_jornal(rs.getInt(1));
            }
        }
    }

    public void inserirAssuntos(Jornal j) throws SQLException {
        final String sql = "INSERT INTO item_assunto (fk_jornal_id_jornal, fk_assunto_id_assunto) VALUES (?, ?)";
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Assunto a : j.getAssuntos()) {
                ps.setInt(1, j.getId_jornal());
                ps.setInt(2, a.getId_assunto());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public void inserirColaboradores(Jornal j) throws SQLException {
        final String sql = "INSERT INTO item_colaborador (fk_jornal_id_jornal, fk_colaborador_id_colaborador) VALUES (?, ?)";
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Colaborador c : j.getColaboradores()) {
                ps.setInt(1, j.getId_jornal());
                ps.setInt(2, c.getId_colaborador());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public void atualizar(Jornal j) throws SQLException {
        if (j.getId_jornal() == null) throw new SQLException("ID nulo");

        final String sql = "UPDATE dbo.jornal SET codigo_jornal=?, pais=?, estado=?, cidade=?, data=?, " +
                "localizacao_acervo=?, numero_paginas=?, edicao=?, idioma=?, titulo=?, subtitulo=?, capa=?, fk_editora_id_editora=? " +
                "WHERE id_jornal=?";
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, j.getCodigo_jornal());
            ps.setString(2, j.getPais());
            ps.setString(3, j.getEstado());
            ps.setString(4, j.getCidade());
            ps.setDate(5, new java.sql.Date(j.getData().getTime())); // converter Date
            ps.setString(6, j.getLocalizacao_acervo());
            ps.setString(7, j.getNumero_paginas());
            ps.setString(8, j.getEdicao());
            ps.setString(9, j.getIdioma());
            ps.setString(10, j.getTitulo());
            ps.setString(11, j.getSubtitulo());
            ps.setBytes(12, j.getCapa());
            ps.setInt(13, j.getEditora().getId_editora());
            ps.setInt(14, j.getId_jornal());

            ps.executeUpdate();
        }

        final String sqlDeleteAssuntos = "DELETE FROM item_assunto WHERE fk_jornal_id_jornal=?";
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlDeleteAssuntos)) {
            ps.setInt(1, j.getId_jornal());
            ps.executeUpdate();
        }
        inserirAssuntos(j);

        final String sqlDeleteColaboradores = "DELETE FROM item_colaborador WHERE fk_jornal_id_jornal=?";
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlDeleteColaboradores)) {
            ps.setInt(1, j.getId_jornal());
            ps.executeUpdate();
        }
        inserirColaboradores(j);
    }

    public void excluir(Jornal j) throws SQLException {
        if (j.getId_jornal() == null) throw new SQLException("ID nulo");

        final String sqlDeleteAssuntos = "DELETE FROM item_assunto WHERE fk_jornal_id_jornal=?";
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlDeleteAssuntos)) {
            ps.setInt(1, j.getId_jornal());
            ps.executeUpdate();
        }

        final String sqlDeleteColaboradores = "DELETE FROM item_colaborador WHERE fk_jornal_id_jornal=?";
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlDeleteColaboradores)) {
            ps.setInt(1, j.getId_jornal());
            ps.executeUpdate();
        }

        final String sqlDeleteJornal = "DELETE FROM dbo.jornal WHERE id_jornal=?";
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlDeleteJornal)) {
            ps.setInt(1, j.getId_jornal());
            ps.executeUpdate();
        }
    }

    public List<Jornal> listar() throws SQLException{
        final String sql = "SELECT id_jornal, codigo_jornal, pais, estado, cidade, data, localizacao_acervo," +
                " numero_paginas, edicao, idioma, titulo, subtitulo, quantidade, capa, fk_editora_id_editora" +
                " FROM dbo.jornal ORDER BY id_jornal DESC";

        List<Jornal> lista = new ArrayList<>();
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(map(rs));
        }
        return lista;
    }

    public List<Jornal> buscarPorCodigo(String codigo) throws SQLException {
        final String sql = "SELECT id_jornal, codigo_jornal, pais, estado, cidade, data, localizacao_acervo," +
                " numero_paginas, edicao, idioma, titulo, subtitulo, quantidade, capa, fk_editora_id_editora" +
                " FROM dbo.jornal WHERE codigo_jornal LIKE ?";

        List<Jornal> lista = new ArrayList<>();

        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + codigo + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(map(rs));
                }
            }
        }
        return lista;
    }

    public List<Jornal> buscarPorTitulo(String titulo) throws SQLException {
        final String sql = "SELECT id_jornal, codigo_jornal, pais, estado, cidade, data, localizacao_acervo," +
                " numero_paginas, edicao, idioma, titulo, subtitulo, quantidade, capa, fk_editora_id_editora" +
                " FROM dbo.jornal WHERE titulo LIKE ?";

        List<Jornal> lista = new ArrayList<>();

        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + titulo + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(map(rs));
                }
            }
        }
        return lista;
    }

    public List<Jornal> buscarPorData(String data) throws SQLException {
        final String sql = "SELECT id_jornal, codigo_jornal, pais, estado, cidade, data, localizacao_acervo," +
                " numero_paginas, edicao, idioma, titulo, subtitulo, quantidade, capa, fk_editora_id_editora" +
                " FROM dbo.jornal WHERE data LIKE ?";

        List<Jornal> lista = new ArrayList<>();

        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + data + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(map(rs));
                }
            }
        }
        return lista;
    }

    public List<Jornal> buscarPorCidade(String cidade) throws SQLException {
        final String sql = "SELECT id_jornal, codigo_jornal, pais, estado, cidade, data, localizacao_acervo," +
                " numero_paginas, edicao, idioma, titulo, subtitulo, quantidade, capa, fk_editora_id_editora" +
                " FROM dbo.jornal WHERE cidade LIKE ?";

        List<Jornal> lista = new ArrayList<>();

        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + cidade + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(map(rs));
                }
            }
        }
        return lista;
    }

    public List<Jornal> buscarPorEditora(String editora) throws SQLException {
        final String sql = "SELECT id_jornal, codigo_jornal, pais, estado, cidade, data, localizacao_acervo," +
                " numero_paginas, edicao, idioma, titulo, subtitulo, quantidade, capa, fk_editora_id_editora" +
                " FROM dbo.jornal WHERE editora LIKE ?";

        List<Jornal> lista = new ArrayList<>();

        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + editora + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(map(rs));
                }
            }
        }
        return lista;
    }

    private Jornal map(ResultSet rs) throws SQLException {
        int idEditora = rs.getInt("fk_editora_id_editora");

        EditoraDAO editoraDAO = new EditoraDAO();
        Editora editora = editoraDAO.buscarPorId(idEditora);

        Jornal jornal = new Jornal(
                rs.getInt("id_jornal"),
                rs.getString("codigo_jornal"),
                rs.getString("pais"),
                rs.getString("estado"),
                rs.getString("cidade"),
                rs.getDate("data"),
                rs.getString("localizacao_acervo"),
                rs.getString("numero_paginas"),
                rs.getString("edicao"),
                rs.getString("idioma"),
                rs.getString("titulo"),
                rs.getString("subtitulo"),
                rs.getInt("quantidade"),
                rs.getBytes("capa"),
                editora
        );

        return jornal;
    }
}
