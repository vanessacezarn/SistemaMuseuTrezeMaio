package eglv.sistemagerenciamentoacervos.dao;

import eglv.sistemagerenciamentoacervos.db.DbConnector;
import eglv.sistemagerenciamentoacervos.model.Assunto;
import eglv.sistemagerenciamentoacervos.model.Colaborador;
import eglv.sistemagerenciamentoacervos.model.Jornal;

import java.sql.*;

public class JornalDAO {
    public void inserir(Jornal j) throws SQLException {
        final String sql = "INSERT INTO dbo.jornal " +
                "(codigo_jornal, pais, estado, cidade, data, localizacao_acervo, numero_paginas," +
                " edicao, idioma, titulo, subtitulo, capa, fk_editora_id_editora) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, j.getCodigo_jornal());
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
        final String sql = "INSERT INTO item_assunto (fk_jornal_id, fk_assunto_id) VALUES (?, ?)";
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
        final String sql = "INSERT INTO item_colaborador (fk_jornal_id, fk_colaborador_id) VALUES (?, ?)";
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
}
