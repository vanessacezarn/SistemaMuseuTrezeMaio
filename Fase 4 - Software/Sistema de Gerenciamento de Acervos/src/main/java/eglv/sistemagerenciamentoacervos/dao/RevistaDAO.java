package eglv.sistemagerenciamentoacervos.dao;

import eglv.sistemagerenciamentoacervos.db.DbConnector;
import eglv.sistemagerenciamentoacervos.model.Assunto;
import eglv.sistemagerenciamentoacervos.model.Colaborador;
import eglv.sistemagerenciamentoacervos.model.Revista;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public void atualizar(Revista r) throws SQLException {
        final String sql = "UPDATE revista SET " +
                "codigo_revista=?, titulo=?, subtitulo=?, volume=?, ano=?, " +
                "mes=?, periodicidade=?, numero_paginas=?, edicao=?, idioma=?, localizacao_acervo=?, capa=? " +
                "WHERE id_revista=?";

        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, r.getCodigo_revista());
            ps.setString(2, r.getTitulo());
            ps.setString(3, r.getSubtitulo());
            ps.setString(4, r.getVolume());

            if (r.getAno() != null)
                ps.setInt(5, r.getAno());
            else
                ps.setNull(5, Types.INTEGER);

            if (r.getMes() != null)
                ps.setInt(6, r.getMes());
            else
                ps.setNull(6, Types.INTEGER);

            ps.setString(7, r.getPeriodicidade());
            ps.setString(8, r.getNumero_paginas());
            ps.setString(9, r.getEdicao());
            ps.setString(10, r.getIdioma());
            ps.setString(11, r.getLocalizacao_acervo());

            ps.setBytes(12, r.getCapa());

            ps.setInt(13, r.getId_revista());

            ps.executeUpdate();
        }
    }

    public List<Revista> listarTodos() throws SQLException {
        final String sql = "SELECT * FROM revista";
        List<Revista> list = new ArrayList<>();
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSetToRevista(rs));
            }
        }
        return list;
    }

    private Revista mapResultSetToRevista(ResultSet rs) throws SQLException {
        Revista r = new Revista();

        r.setId_revista(rs.getInt("id_revista"));
        r.setCodigo_revista(rs.getString("codigo_revista"));
        r.setTitulo(rs.getString("titulo"));
        r.setSubtitulo(rs.getString("subtitulo"));
        r.setPeriodicidade(rs.getString("periodicidade"));
        r.setAno((Integer) rs.getObject("ano"));
        r.setMes((Integer) rs.getObject("mes"));
        r.setVolume(rs.getString("volume"));
        r.setLocalizacao_acervo(rs.getString("localizacao_acervo"));
        r.setNumero_paginas(rs.getString("numero_paginas"));
        r.setEdicao(rs.getString("edicao"));
        r.setIdioma(rs.getString("idioma"));
        r.setCapa(rs.getBytes("capa"));

        return r;
    }

    public Revista buscarPorCodigo(String codigo) throws SQLException {
        final String sql = "SELECT * FROM revista WHERE codigo_revista=?";
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapResultSetToRevista(rs);
            }
        }
        return null;
    }

    public Revista buscarPorTitulo(String titulo) throws SQLException {
        final String sql = "SELECT * FROM revista WHERE titulo=?";
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, titulo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapResultSetToRevista(rs);
            }
        }
        return null;
    }

    public void delete(int idRevista) throws SQLException {
        final String sql = "DELETE FROM revista WHERE id_revista=?";
        try (Connection conn = DbConnector.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idRevista);
            ps.executeUpdate();
        }
    }

    public List<Revista> listarTodosComEditora() throws SQLException {
        final String sql = "SELECT r.*, e.id_editora, e.nome AS nome_editora " +
                "FROM revista r " +
                "LEFT JOIN editora e ON r.fk_editora_id_editora = e.id_editora";
        List<Revista> list = new ArrayList<>();
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Revista r = mapResultSetToRevista(rs);

                if (rs.getInt("id_editora") != 0) {
                    eglv.sistemagerenciamentoacervos.model.Editora ed = new eglv.sistemagerenciamentoacervos.model.Editora();
                    ed.setId_editora(rs.getInt("id_editora"));
                    ed.setNome(rs.getString("nome_editora"));
                    r.setEditora(ed);
                }

                list.add(r);
            }
        }
        return list;
    }

    public List<Revista> listarTodosComAssuntos() throws SQLException {
        List<Revista> revistas = listarTodos(); // usa o método básico
        try (Connection conn = DbConnector.getConnection()) {
            for (Revista r : revistas) {
                final String sql = "SELECT a.* FROM assunto a " +
                        "JOIN item_assunto ia ON ia.fk_assunto_id_assunto = a.id_assunto " +
                        "WHERE ia.fk_revista_id_revista = ?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, r.getId_revista());
                    try (ResultSet rs = ps.executeQuery()) {
                        List<Assunto> assuntos = new ArrayList<>();
                        while (rs.next()) {
                            Assunto a = new Assunto();
                            a.setId_assunto(rs.getInt("id_assunto"));
                            a.setDescricao(rs.getString("descricao"));
                            assuntos.add(a);
                        }
                        r.setAssuntos(assuntos);
                    }
                }
            }
        }
        return revistas;
    }

    public List<Revista> listarTodosComColaboradores() throws SQLException {
        List<Revista> revistas = listarTodos(); // usa o método básico
        try (Connection conn = DbConnector.getConnection()) {
            for (Revista r : revistas) {
                final String sql = "SELECT c.* FROM colaborador c " +
                        "JOIN item_colaborador ic ON ic.fk_colaborador_id_colaborador = c.id_colaborador " +
                        "WHERE ic.fk_revista_id_revista = ?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, r.getId_revista());
                    try (ResultSet rs = ps.executeQuery()) {
                        List<Colaborador> colaboradores = new ArrayList<>();
                        while (rs.next()) {
                            Colaborador c = new Colaborador();
                            c.setId_colaborador(rs.getInt("id_colaborador"));
                            c.setNome(rs.getString("nome"));
                            c.setSobrenome(rs.getString("sobrenome"));
                            colaboradores.add(c);
                        }
                        r.setColaboradores(colaboradores);
                    }
                }
            }
        }
        return revistas;
    }

    public List<Revista> listarTodosCompleto() throws SQLException {
        List<Revista> revistas = listarTodosComEditora();
        try (Connection conn = DbConnector.getConnection()) {
            for (Revista r : revistas) {
                // Assuntos
                final String sqlAssunto = "SELECT a.* FROM assunto a " +
                        "JOIN item_assunto ia ON ia.fk_assunto_id_assunto = a.id_assunto " +
                        "WHERE ia.fk_revista_id_revista = ?";
                try (PreparedStatement ps = conn.prepareStatement(sqlAssunto)) {
                    ps.setInt(1, r.getId_revista());
                    try (ResultSet rs = ps.executeQuery()) {
                        List<Assunto> assuntos = new ArrayList<>();
                        while (rs.next()) {
                            Assunto a = new Assunto();
                            a.setId_assunto(rs.getInt("id_assunto"));
                            a.setDescricao(rs.getString("descricao"));
                            assuntos.add(a);
                        }
                        r.setAssuntos(assuntos);
                    }
                }

                // Colaboradores
                final String sqlColab = "SELECT c.* FROM colaborador c " +
                        "JOIN item_colaborador ic ON ic.fk_colaborador_id_colaborador = c.id_colaborador " +
                        "WHERE ic.fk_revista_id_revista = ?";
                try (PreparedStatement ps = conn.prepareStatement(sqlColab)) {
                    ps.setInt(1, r.getId_revista());
                    try (ResultSet rs = ps.executeQuery()) {
                        List<Colaborador> colaboradores = new ArrayList<>();
                        while (rs.next()) {
                            Colaborador c = new Colaborador();
                            c.setId_colaborador(rs.getInt("id_colaborador"));
                            c.setNome(rs.getString("nome"));
                            colaboradores.add(c);
                        }
                        r.setColaboradores(colaboradores);
                    }
                }
            }
        }
        return revistas;
    }

}

