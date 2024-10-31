package dao;

import entidade.Pagamento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PagamentoDAO {
    private Connection connection;

    public PagamentoDAO(Connection connection) {
        this.connection = connection;
    }

    public void create(Pagamento pagamento) throws SQLException {
        String sql = "INSERT INTO pagamentos (venda_id, valor, metodo_pagamento, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, pagamento.getVendaId());
            stmt.setBigDecimal(2, pagamento.getValor());
            stmt.setString(3, pagamento.getMetodoPagamento());
            stmt.setString(4, pagamento.getStatus());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    pagamento.setPagamentoId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public Pagamento read(int pagamentoId) throws SQLException {
        String sql = "SELECT * FROM pagamentos WHERE pagamento_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, pagamentoId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToPagamento(rs);
            }
        }
        return null;
    }

    public List<Pagamento> readAll() throws SQLException {
        List<Pagamento> pagamentos = new ArrayList<>();
        String sql = "SELECT * FROM pagamentos";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                pagamentos.add(mapResultSetToPagamento(rs));
            }
        }
        return pagamentos;
    }

    public void update(Pagamento pagamento) throws SQLException {
        String sql = "UPDATE pagamentos SET venda_id = ?, valor = ?, metodo_pagamento = ?, status = ? WHERE pagamento_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, pagamento.getVendaId());
            stmt.setBigDecimal(2, pagamento.getValor());
            stmt.setString(3, pagamento.getMetodoPagamento());
            stmt.setString(4, pagamento.getStatus());
            stmt.setInt(5, pagamento.getPagamentoId());
            stmt.executeUpdate();
        }
    }

    public void delete(int pagamentoId) throws SQLException {
        String sql = "DELETE FROM pagamentos WHERE pagamento_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, pagamentoId);
            stmt.executeUpdate();
        }
    }

    private Pagamento mapResultSetToPagamento(ResultSet rs) throws SQLException {
        Pagamento pagamento = new Pagamento();
        pagamento.setPagamentoId(rs.getInt("pagamento_id"));
        pagamento.setVendaId(rs.getInt("venda_id"));
        pagamento.setValor(rs.getBigDecimal("valor"));
        pagamento.setMetodoPagamento(rs.getString("metodo_pagamento"));
        pagamento.setStatus(rs.getString("status"));
        return pagamento;
    }
}
