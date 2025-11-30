package eglv.sistemagerenciamentoacervos.dao;

import eglv.sistemagerenciamentoacervos.db.DbConnector;
import eglv.sistemagerenciamentoacervos.model.Assunto;
import eglv.sistemagerenciamentoacervos.model.Colaborador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ColaboradorDAO {

    public void inserir(Colaborador c) throws SQLException {
        final String sql = "INSERT INTO dbo.colaborador (nome, sobrenome, nacionalidade, tipo) VALUES (?,?,?,?)";
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, c.getNome());
            ps.setString(2, c.getSobrenome());
            ps.setString(3, c.getNacionalidade());
            ps.setString(4, c.getTipo());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) c.setId(rs.getInt(1));
            }
        }
    }
    public void atualizar(Colaborador c) throws SQLException {
        if (c.getId() == null) throw new SQLException("ID nulo para atualizar.");
        final String sql = "UPDATE dbo.colaborador SET nome=?, sobrenome=?, nacionalidade=?, tipo=? WHERE id=?";
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getNome());
            ps.setString(2, c.getSobrenome());
            ps.setString(3, c.getNacionalidade());
            ps.setString(4, c.getTipo());
            ps.setInt(5, c.getId());
            ps.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        final String sql = "DELETE FROM dbo.colaborador WHERE id=?";
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    public List<Colaborador> listar() throws SQLException {
        final String sql = "SELECT id, nome, sobrenome, nacionalidade, tipo FROM dbo.colaborador ORDER BY id DESC";
        List<Colaborador> lista = new ArrayList<>();
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(map(rs));
        }
        return lista;
    }

    private Colaborador map(ResultSet rs) throws SQLException {
        return new Colaborador(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("sobrenome"),
                rs.getString("nacionalidade"),
                rs.getString("tipo")
        );
    }
}
