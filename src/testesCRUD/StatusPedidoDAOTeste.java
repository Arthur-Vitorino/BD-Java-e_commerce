package testesCRUD;

import dao.StatusPedidoDAO;
import entidade.StatusPedido;
import java.sql.Connection;
import java.sql.SQLException;

import conexao.ConexaoBD;

public class StatusPedidoDAOTeste {
    public static void main(String[] args) {
        try (Connection connection = ConexaoBD.getConnection()) {
            StatusPedidoDAO statusPedidoDAO = new StatusPedidoDAO(connection);

            // Teste de criação
            StatusPedido novoStatus = new StatusPedido();
            novoStatus.setDescricao("Enviado");
            statusPedidoDAO.create(novoStatus);
            System.out.println("Status do pedido criado: " + novoStatus);

            // Teste de leitura
            StatusPedido statusLido = statusPedidoDAO.read(novoStatus.getStatusPedidoId());
            System.out.println("Status do pedido lido: " + statusLido);

            // Teste de atualização
            statusLido.setDescricao("Entregue");
            statusPedidoDAO.update(statusLido);
            StatusPedido statusAtualizado = statusPedidoDAO.read(statusLido.getStatusPedidoId());
            System.out.println("Status do pedido atualizado: " + statusAtualizado);

            // Teste de exclusão
            statusPedidoDAO.delete(statusAtualizado.getStatusPedidoId());
            StatusPedido statusDeletado = statusPedidoDAO.read(statusAtualizado.getStatusPedidoId());
            System.out.println("Status do pedido deletado: " + (statusDeletado == null ? "sucesso" : "falha"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
