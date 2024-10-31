package dao;

import entidade.Fornecedor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FornecedorDAO {
    private Connection connection;

    public FornecedorDAO(Connection connection) {
        this.connection = connection;
    }

    public void create(Fornecedor fornecedor) throws SQLException {
        String sql = "INSERT INTO fornecedores (nome, contato, telefone, email, endereco) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, fornecedor.getNome());
            stmt.setString(2, fornecedor.getContato());
            stmt.setString(3, fornecedor.getTelefone());
            stmt.setString(4, fornecedor.getEmail());
            stmt.setString(5, fornecedor.getEndereco());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    fornecedor.setFornecedorId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public Fornecedor read(int fornecedorId) throws SQLException {
        String sql = "SELECT * FROM fornecedores WHERE fornecedor_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, fornecedorId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToFornecedor(rs);
            }
        }
        return null;
    }

    public List<Fornecedor> readAll() throws SQLException {
        List<Fornecedor> fornecedores = new ArrayList<>();
        String sql = "SELECT * FROM fornecedores";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                fornecedores.add(mapResultSetToFornecedor(rs));
            }
        }
        return fornecedores;
    }

    public void update(Fornecedor fornecedor) throws SQLException {
        String sql = "UPDATE fornecedores SET nome = ?, contato = ?, telefone = ?, email = ?, endereco = ? WHERE fornecedor_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, fornecedor.getNome());
            stmt.setString(2, fornecedor.getContato());
            stmt.setString(3, fornecedor.getTelefone());
            stmt.setString(4, fornecedor.getEmail());
            stmt.setString(5, fornecedor.getEndereco());
            stmt.setInt(6, fornecedor.getFornecedorId());
            stmt.executeUpdate();
        }
    }

    public void delete(int fornecedorId) throws SQLException {
        String sql = "DELETE FROM fornecedores WHERE fornecedor_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, fornecedorId);
            stmt.executeUpdate();
        }
    }

    private Fornecedor mapResultSetToFornecedor(ResultSet rs) throws SQLException {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setFornecedorId(rs.getInt("fornecedor_id"));
        fornecedor.setNome(rs.getString("nome"));
        fornecedor.setContato(rs.getString("contato"));
        fornecedor.setTelefone(rs.getString("telefone"));
        fornecedor.setEmail(rs.getString("email"));
        fornecedor.setEndereco(rs.getString("endereco"));
        return fornecedor;
    }
}
