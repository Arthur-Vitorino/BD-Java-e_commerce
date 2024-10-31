package dao;

import entidade.Venda;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO {
    private Connection connection;

    public VendaDAO(Connection connection) {
        this.connection = connection;
    }

    public void create(Venda venda) throws SQLException {
        String sql = "INSERT INTO vendas (cliente_id, status_pedido_id, valor_total) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, venda.getClienteId());
            stmt.setInt(2, venda.getStatusPedidoId());
            stmt.setBigDecimal(3, venda.getValorTotal());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    venda.setVendaId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public Venda read(int vendaId) throws SQLException {
        String sql = "SELECT * FROM vendas WHERE venda_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, vendaId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToVenda(rs);
            }
        }
        return null;
    }

    public List<Venda> readAll() throws SQLException {
        List<Venda> vendas = new ArrayList<>();
        String sql = "SELECT * FROM vendas";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                vendas.add(mapResultSetToVenda(rs));
            }
        }
        return vendas;
    }

    public void update(Venda venda) throws SQLException {
        String sql = "UPDATE vendas SET cliente_id = ?, status_pedido_id = ?, valor_total = ? WHERE venda_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, venda.getClienteId());
            stmt.setInt(2, venda.getStatusPedidoId());
            stmt.setBigDecimal(3, venda.getValorTotal());
            stmt.setInt(4, venda.getVendaId());
            stmt.executeUpdate();
        }
    }

    public void delete(int vendaId) throws SQLException {
        String sql = "DELETE FROM vendas WHERE venda_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, vendaId);
            stmt.executeUpdate();
        }
    }

    private Venda mapResultSetToVenda(ResultSet rs) throws SQLException {
        Venda venda = new Venda();
        venda.setVendaId(rs.getInt("venda_id"));
        venda.setClienteId(rs.getInt("cliente_id"));
        venda.setStatusPedidoId(rs.getInt("status_pedido_id"));
        venda.setValorTotal(rs.getBigDecimal("valor_total"));
        venda.setDataVenda(rs.getTimestamp("data_venda").toLocalDateTime());
        return venda;
    }
}
