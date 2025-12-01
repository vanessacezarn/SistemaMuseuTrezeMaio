package eglv.sistemagerenciamentoacervos.dao;

import eglv.sistemagerenciamentoacervos.db.DbConnector;
import eglv.sistemagerenciamentoacervos.model.Assunto;
import eglv.sistemagerenciamentoacervos.model.Colaborador;
import eglv.sistemagerenciamentoacervos.model.Editora;

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
                if (rs.next()) c.setId_colaborador(rs.getInt(1));
            }
        }
    }
    public void atualizar(Colaborador c) throws SQLException {
        if (c.getId_colaborador() == null) throw new SQLException("ID nulo para atualizar.");
        final String sql = "UPDATE dbo.colaborador SET nome=?, sobrenome=?, nacionalidade=?, tipo=? WHERE id_colaborador=?";
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getNome());
            ps.setString(2, c.getSobrenome());
            ps.setString(3, c.getNacionalidade());
            ps.setString(4, c.getTipo());
            ps.setInt(5, c.getId_colaborador());
            ps.executeUpdate();
        }
    }

    public void excluir(int id_colaborador) throws SQLException {
        final String sql = "DELETE FROM dbo.colaborador WHERE id_colaborador=?";
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id_colaborador);
            ps.executeUpdate();
        }
    }
    public List<Colaborador> listar() throws SQLException {
        final String sql = "SELECT id_colaborador, nome, sobrenome, nacionalidade, tipo FROM dbo.colaborador ORDER BY id_colaborador DESC";
        List<Colaborador> lista = new ArrayList<>();
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(map(rs));
        }
        return lista;
    }

   public List<Colaborador> buscarPorNome(String nome) throws SQLException {
        final String sql = "SELECT id_colaborador, nome, sobrenome, nacionalidade, tipo FROM dbo.colaborador WHERE nome LIKE ?";

        List<Colaborador> lista = new ArrayList<>();
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

    public List<Colaborador> buscarPorSobrenome(String sobrenome) throws SQLException {
        final String sql = "SELECT id_colaborador, nome, sobrenome, nacionalidade, tipo FROM dbo.colaborador WHERE sobrenome LIKE ?";

        List<Colaborador> lista = new ArrayList<>();
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + sobrenome + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(map(rs));
                }
            }
        }
        return lista;
    }

    public List<Colaborador> buscarPorTipo(String tipo) throws SQLException {
        final String sql = "SELECT id_colaborador, nome, sobrenome, nacionalidade, tipo FROM dbo.colaborador WHERE tipo LIKE ?";

        List<Colaborador> lista = new ArrayList<>();
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + tipo + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(map(rs));
                }
            }
        }
        return lista;
    }

    public List<Colaborador> buscarPorNacionalidade(String nacionalidae) throws SQLException {
        final String sql = "SELECT id_colaborador, nome, sobrenome, nacionalidade, tipo FROM dbo.colaborador WHERE nacionalidae LIKE ?";

        List<Colaborador> lista = new ArrayList<>();
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + nacionalidae + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(map(rs));
                }
            }
        }
        return lista;
    }

    private Colaborador map(ResultSet rs) throws SQLException {
        return new Colaborador(
                rs.getInt("id_colaborador"),
                rs.getString("nome"),
                rs.getString("sobrenome"),
                rs.getString("nacionalidade"),
                rs.getString("tipo")
        );
    }
}
