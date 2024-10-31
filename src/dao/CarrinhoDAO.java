package dao;

import entidade.Carrinho;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarrinhoDAO {
    private Connection connection;

    public CarrinhoDAO(Connection connection) {
        this.connection = connection;
    }

    public void create(Carrinho carrinho) throws SQLException {
        String sql = "INSERT INTO carrinho (cliente_id, produto_id, quantidade) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, carrinho.getClienteId());
            stmt.setInt(2, carrinho.getProdutoId());
            stmt.setInt(3, carrinho.getQuantidade());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    carrinho.setCarrinhoId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public Carrinho read(int carrinhoId) throws SQLException {
        String sql = "SELECT * FROM carrinho WHERE carrinho_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, carrinhoId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToCarrinho(rs);
            }
        }
        return null;
    }

    public List<Carrinho> readAll() throws SQLException {
        List<Carrinho> carrinhos = new ArrayList<>();
        String sql = "SELECT * FROM carrinho";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                carrinhos.add(mapResultSetToCarrinho(rs));
            }
        }
        return carrinhos;
    }

    public void update(Carrinho carrinho) throws SQLException {
        String sql = "UPDATE carrinho SET cliente_id = ?, produto_id = ?, quantidade = ? WHERE carrinho_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, carrinho.getClienteId());
            stmt.setInt(2, carrinho.getProdutoId());
            stmt.setInt(3, carrinho.getQuantidade());
            stmt.setInt(4, carrinho.getCarrinhoId());
            stmt.executeUpdate();
        }
    }

    public void delete(int carrinhoId) throws SQLException {
        String sql = "DELETE FROM carrinho WHERE carrinho_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, carrinhoId);
            stmt.executeUpdate();
        }
    }

    private Carrinho mapResultSetToCarrinho(ResultSet rs) throws SQLException {
        Carrinho carrinho = new Carrinho();
        carrinho.setCarrinhoId(rs.getInt("carrinho_id"));
        carrinho.setClienteId(rs.getInt("cliente_id"));
        carrinho.setProdutoId(rs.getInt("produto_id"));
        carrinho.setQuantidade(rs.getInt("quantidade"));
        return carrinho;
    }
}
