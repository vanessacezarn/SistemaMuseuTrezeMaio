package eglv.sistemagerenciamentoacervos.dao;

import eglv.sistemagerenciamentoacervos.db.DbConnector;
import eglv.sistemagerenciamentoacervos.model.Assunto;
import eglv.sistemagerenciamentoacervos.model.Editora;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AssuntoDAO {

    public void inserir(Assunto a) throws SQLException {
        final String sql = "INSERT INTO dbo.assunto (descricao) VALUES (?)";
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, a.getDescricao());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) a.setId(rs.getInt(1));
            }
        }
    }
    public void atualizar(Assunto a) throws SQLException {
        if (a.getId() == null) throw new SQLException("ID nulo para atualizar.");
        final String sql = "UPDATE dbo.assunto SET descricao=? WHERE id=?";
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getDescricao());
            ps.setInt(2, a.getId());
            ps.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        final String sql = "DELETE FROM dbo.assunto WHERE id=?";
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    public List<Assunto> listar() throws SQLException {
        final String sql = "SELECT id, descricao FROM dbo.assunto ORDER BY id DESC";
        List<Assunto> lista = new ArrayList<>();
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(map(rs));
        }
        return lista;
    }

    private Assunto map(ResultSet rs) throws SQLException {
        return new Assunto(
                rs.getInt("id"),
                rs.getString("descricao")
        );
    }
}
