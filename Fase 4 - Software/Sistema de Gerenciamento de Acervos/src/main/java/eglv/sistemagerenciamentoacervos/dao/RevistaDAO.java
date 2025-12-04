package eglv.sistemagerenciamentoacervos.dao;

import eglv.sistemagerenciamentoacervos.db.DbConnector;
import eglv.sistemagerenciamentoacervos.model.Assunto;
import eglv.sistemagerenciamentoacervos.model.Colaborador;
import eglv.sistemagerenciamentoacervos.model.Revista;

import java.sql.*;

public class RevistaDAO {

    public void inserir(Revista r) throws SQLException {
        final String sql = "INSERT INTO dbo.revista " +
                "(codigo_revista, periodicidade, ano, mes, volume, localizacao_acervo, numero_paginas," +
                " edicao, idioma, titulo, subtitulo, capa, fk_editora_id_editora) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, r.getCodigo_revista());
            ps.setString(2, r.getPeriodicidade());
            ps.setInt(3, r.getAno());
            ps.setInt(4, r.getMes());
            ps.setString(5, r.getVolume());
            ps.setString(6, r.getLocalizacao_acervo());
            ps.setString(7, r.getNumero_paginas());
            ps.setString(8, r.getEdicao());
            ps.setString(9, r.getIdioma());
            ps.setString(10, r.getTitulo());
            ps.setString(11, r.getSubtitulo());
            ps.setBytes(12, r.getCapa());
            ps.setInt(13, r.getEditora().getId_editora());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) r.setId_revista(rs.getInt(1));
            }
        }
    }

    public void inserirAssuntos(Revista r) throws SQLException {
        final String sql = "INSERT INTO item_assunto (fk_revista_id_revista, fk_assunto_id_assunto) VALUES (?, ?)";
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Assunto a : r.getAssuntos()) {
                ps.setInt(1, r.getId_revista());
                ps.setInt(2, a.getId_assunto());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public void inserirColaboradores(Revista r) throws SQLException {
        final String sql = "INSERT INTO item_colaborador (fk_revista_id_revista, fk_colaborador_id_colaborador) VALUES (?, ?)";
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Colaborador c : r.getColaboradores()) {
                ps.setInt(1, r.getId_revista());
                ps.setInt(2, c.getId_colaborador());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
}
