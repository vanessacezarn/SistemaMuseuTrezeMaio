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
