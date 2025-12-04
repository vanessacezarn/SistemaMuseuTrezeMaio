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
                if (rs.next()) e.setId_editora(rs.getInt(1));
            }
        }
    }
    public void atualizar(Editora e) throws SQLException {
        if (e.getId_editora() == null) throw new SQLException("ID nulo para atualizar.");
        final String sql = "UPDATE dbo.editora SET nome=?, localizacao=? WHERE id_editora=?";
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, e.getNome());
            ps.setString(2, e.getLocalizacao());
            ps.setInt(3, e.getId_editora());
            ps.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        final String sql = "DELETE FROM dbo.editora WHERE id_editora=?";
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public Editora buscarPorId(int id) throws SQLException {
        final String sql = "SELECT id_editora, nome, localizacao FROM dbo.editora WHERE id_editora=?";
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }

    public List<Editora> buscarPorNome(String nome) throws SQLException {
        final String sql = "SELECT id_editora, nome, localizacao FROM dbo.editora WHERE nome LIKE ?";

        List<Editora> lista = new ArrayList<>();

        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + nome + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(map(rs));
                }
            }
        }

        return lista;
    }

    public List<Editora> buscarPorLocalizacao(String localizacao) throws SQLException {
        final String sql = "SELECT id_editora, nome, localizacao FROM dbo.editora WHERE localizacao LIKE ?";

        List<Editora> lista = new ArrayList<>();

        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + localizacao + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(map(rs));
                }
            }
        }

        return lista;
    }

    public List<Editora> listar() throws SQLException {
        final String sql = "SELECT id_editora, nome, localizacao FROM dbo.editora ORDER BY id_editora DESC";
        List<Editora> lista = new ArrayList<>();
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(map(rs));
        }
        return lista;
    }

    private Editora map(ResultSet rs) throws SQLException {
            Editora e = new Editora();
            // so le se existir no SELECT
            try {
                e.setId_editora(rs.getInt("id_editora"));
            } catch (SQLException ignored) {}

            try {
                e.setLocalizacao(rs.getString("localizacao"));
            } catch (SQLException ignored) {}

            e.setNome(rs.getString("nome")); // essa SEMPRE existe

            return e;
    }
}
