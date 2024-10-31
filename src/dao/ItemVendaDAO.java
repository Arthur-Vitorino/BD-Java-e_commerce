package dao;

import entidade.ItemVenda;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemVendaDAO {
    private Connection connection;

    public ItemVendaDAO(Connection connection) {
        this.connection = connection;
    }

    public void create(ItemVenda itemVenda) throws SQLException {
        String sql = "INSERT INTO itens_venda (venda_id, produto_id, quantidade, preco_unitario) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, itemVenda.getVendaId());
            stmt.setInt(2, itemVenda.getProdutoId());
            stmt.setInt(3, itemVenda.getQuantidade());
            stmt.setBigDecimal(4, itemVenda.getPrecoUnitario());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    itemVenda.setItemVendaId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public ItemVenda read(int itemVendaId) throws SQLException {
        String sql = "SELECT * FROM itens_venda WHERE item_venda_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, itemVendaId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToItemVenda(rs);
            }
        }
        return null;
    }

    public List<ItemVenda> readAll() throws SQLException {
        List<ItemVenda> itensVenda = new ArrayList<>();
        String sql = "SELECT * FROM itens_venda";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                itensVenda.add(mapResultSetToItemVenda(rs));
            }
        }
        return itensVenda;
    }

    public void update(ItemVenda itemVenda) throws SQLException {
        String sql = "UPDATE itens_venda SET venda_id = ?, produto_id = ?, quantidade = ?, preco_unitario = ? WHERE item_venda_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, itemVenda.getVendaId());
            stmt.setInt(2, itemVenda.getProdutoId());
            stmt.setInt(3, itemVenda.getQuantidade());
            stmt.setBigDecimal(4, itemVenda.getPrecoUnitario());
            stmt.setInt(5, itemVenda.getItemVendaId());
            stmt.executeUpdate();
        }
    }

    public void delete(int itemVendaId) throws SQLException {
        String sql = "DELETE FROM itens_venda WHERE item_venda_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, itemVendaId);
            stmt.executeUpdate();
        }
    }

    private ItemVenda mapResultSetToItemVenda(ResultSet rs) throws SQLException {
        ItemVenda itemVenda = new ItemVenda();
        itemVenda.setItemVendaId(rs.getInt("item_venda_id"));
        itemVenda.setVendaId(rs.getInt("venda_id"));
        itemVenda.setProdutoId(rs.getInt("produto_id"));
        itemVenda.setQuantidade(rs.getInt("quantidade"));
        itemVenda.setPrecoUnitario(rs.getBigDecimal("preco_unitario"));
        return itemVenda;
    }
}
