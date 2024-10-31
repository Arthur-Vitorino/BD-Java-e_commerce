package dao;


import entidade.Endereco;
import java.sql.*;
import java.util.ArrayList;

import java.util.List;

public class EnderecoDAO {
    private Connection connection;
 
    public EnderecoDAO(Connection connection) {
        this.connection = connection;
    }

    public void create(Endereco endereco) throws SQLException {
        String sql = "INSERT INTO enderecos (cliente_id, rua, numero, complemento, bairro, cidade, estado, cep) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, endereco.getClienteId());
            stmt.setString(2, endereco.getRua());
            stmt.setString(3, endereco.getNumero());
            stmt.setString(4, endereco.getComplemento());
            stmt.setString(5, endereco.getBairro());
            stmt.setString(6, endereco.getCidade());
            stmt.setString(7, endereco.getEstado());
            stmt.setString(8, endereco.getCep());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    endereco.setEnderecoId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public Endereco read(int enderecoId) throws SQLException {
        String sql = "SELECT * FROM enderecos WHERE endereco_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, enderecoId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToEndereco(rs);
            }
        }
        return null;
    }

    public List<Endereco> readByClienteId(int clienteId) throws SQLException {
        List<Endereco> enderecos = new ArrayList<>();
        String sql = "SELECT * FROM enderecos WHERE cliente_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, clienteId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    enderecos.add(mapResultSetToEndereco(rs));
                }
            }
        }
        return enderecos;
    }

    public void update(Endereco endereco) throws SQLException {
        String sql = "UPDATE enderecos SET cliente_id = ?, rua = ?, numero = ?, complemento = ?, bairro = ?, cidade = ?, estado = ?, cep = ? WHERE endereco_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, endereco.getClienteId());
            stmt.setString(2, endereco.getRua());
            stmt.setString(3, endereco.getNumero());
            stmt.setString(4, endereco.getComplemento());
            stmt.setString(5, endereco.getBairro());
            stmt.setString(6, endereco.getCidade());
            stmt.setString(7, endereco.getEstado());
            stmt.setString(8, endereco.getCep());
            stmt.setInt(9, endereco.getEnderecoId());
            stmt.executeUpdate();
        }
    }

    public void delete(int enderecoId) throws SQLException {
        String sql = "DELETE FROM enderecos WHERE endereco_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, enderecoId);
            stmt.executeUpdate();
        }
    }

    private Endereco mapResultSetToEndereco(ResultSet rs) throws SQLException {
        Endereco endereco = new Endereco();
        endereco.setEnderecoId(rs.getInt("endereco_id"));
        endereco.setClienteId(rs.getInt("cliente_id"));
        endereco.setRua(rs.getString("rua"));
        endereco.setNumero(rs.getString("numero"));
        endereco.setComplemento(rs.getString("complemento"));
        endereco.setBairro(rs.getString("bairro"));
        endereco.setCidade(rs.getString("cidade"));
        endereco.setEstado(rs.getString("estado"));
        endereco.setCep(rs.getString("cep"));
        return endereco;
    }
}
