package dao;

import entidade.StatusPedido;
import java.sql .*;
import java.util.ArrayList;
import java.util.List;

public class StatusPedidoDAO {
    private Connection connection;

    public StatusPedidoDAO(Connection connection) {
        this.connection = connection;
    }

    public void create(StatusPedido statusPedido) throws SQLException {
        String sql = "INSERT INTO status_pedido (descricao) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, statusPedido.getDescricao());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    statusPedido.setStatusPedidoId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public StatusPedido read(int statusPedidoId) throws SQLException {
        String sql = "SELECT * FROM status_pedido WHERE status_pedido_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, statusPedidoId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToStatusPedido(rs);
            }
        }
        return null;
    }

    public List<StatusPedido> readAll() throws SQLException {
        List<StatusPedido> statusPedidos = new ArrayList<>();
        String sql = "SELECT * FROM status_pedido";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                statusPedidos.add(mapResultSetToStatusPedido(rs));
            }
        }
        return statusPedidos;
    }

    public void update(StatusPedido statusPedido) throws SQLException {
        String sql = "UPDATE status_pedido SET descricao = ? WHERE status_pedido_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, statusPedido.getDescricao());
            stmt.setInt(2, statusPedido.getStatusPedidoId());
            stmt.executeUpdate();
        }
    }

    public void delete(int statusPedidoId) throws SQLException {
        String sql = "DELETE FROM status_pedido WHERE status_pedido_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, statusPedidoId);
            stmt.executeUpdate();
        }
    }

    private StatusPedido mapResultSetToStatusPedido(ResultSet rs) throws SQLException {
        StatusPedido statusPedido = new StatusPedido();
        statusPedido.setStatusPedidoId(rs.getInt("status_pedido_id"));
        statusPedido.setDescricao(rs.getString("descricao"));
        return statusPedido;
    }
}
