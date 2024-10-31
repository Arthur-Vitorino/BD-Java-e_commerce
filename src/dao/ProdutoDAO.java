package dao;

import entidade.Produto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    private Connection connection;

    public ProdutoDAO(Connection connection) {
        this.connection = connection;
    }

    public void create(Produto produto) throws SQLException {
        String sql = "INSERT INTO produtos (nome, descricao, preco, estoque, categoria_id, fornecedor_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setDouble(3, produto.getPreco());
            stmt.setInt(4, produto.getEstoque());
            stmt.setInt(5, produto.getCategoriaId());
            stmt.setInt(6, produto.getFornecedorId());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    produto.setProdutoId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public Produto read(int produtoId) throws SQLException {
        String sql = "SELECT * FROM produtos WHERE produto_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, produtoId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Produto produto = new Produto();
                produto.setProdutoId(rs.getInt("produto_id"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setPreco(rs.getDouble("preco"));
                produto.setEstoque(rs.getInt("estoque"));
                produto.setCategoriaId(rs.getInt("categoria_id"));
                produto.setFornecedorId(rs.getInt("fornecedor_id"));
                return produto;
            }
        }
        return null;
    }

    public List<Produto> readAll() throws SQLException {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produtos";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Produto produto = new Produto();
                produto.setProdutoId(rs.getInt("produto_id"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setPreco(rs.getDouble("preco"));
                produto.setEstoque(rs.getInt("estoque"));
                produto.setCategoriaId(rs.getInt("categoria_id"));
                produto.setFornecedorId(rs.getInt("fornecedor_id"));
                produtos.add(produto);
            }
        }
        return produtos;
    }

    public void update(Produto produto) throws SQLException {
        String sql = "UPDATE produtos SET nome = ?, descricao = ?, preco = ?, estoque = ?, categoria_id = ?, fornecedor_id = ? WHERE produto_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setDouble(3, produto.getPreco());
            stmt.setInt(4, produto.getEstoque());
            stmt.setInt(5, produto.getCategoriaId());
            stmt.setInt(6, produto.getFornecedorId());
            stmt.setInt(7, produto.getProdutoId());
            stmt.executeUpdate();
        }
    }

    public void delete(int produtoId) throws SQLException {
        String sql = "DELETE FROM produtos WHERE produto_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, produtoId);
            stmt.executeUpdate();
        }
    }
}
