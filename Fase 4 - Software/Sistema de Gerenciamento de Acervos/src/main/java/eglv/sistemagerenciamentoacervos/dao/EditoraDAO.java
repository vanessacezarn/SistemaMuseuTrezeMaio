package eglv.sistemagerenciamentoacervos.dao;

import eglv.sistemagerenciamentoacervos.db.DbConnector;
import eglv.sistemagerenciamentoacervos.model.Editora;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EditoraDAO {
    public void inserir(Editora e) throws SQLException {
        final String sql = "INSERT INTO dbo.editora (nome, localizacao) VALUES (?, ?)";
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, e.getNome());
            ps.setString(2, e.getLocalizacao());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) e.setId(rs.getInt(1));
            }
        }
    }
    public void atualizar(Editora e) throws SQLException {
        if (e.getId() == null) throw new SQLException("ID nulo para atualizar.");
        final String sql = "UPDATE dbo.editora SET nome=?, localizacao=? WHERE id=?";
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, e.getNome());
            ps.setString(2, e.getLocalizacao());
            ps.setInt(3, e.getId());
            ps.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        final String sql = "DELETE FROM editora WHERE id=?";
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public Editora buscarPorId(int id) throws SQLException {
        final String sql = "SELECT id, nome, localizacao FROM editora WHERE id=?";
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }
    public List<Editora> listar() throws SQLException {
        final String sql = "SELECT id, nome, localizao FROM editora ORDER BY id DESC";
        List<Editora> lista = new ArrayList<>();
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(map(rs));
        }
        return lista;
    }

    private Editora map(ResultSet rs) throws SQLException {
        return new Editora(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("localizacao")
        );
    }


}
