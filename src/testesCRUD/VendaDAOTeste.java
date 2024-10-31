package testesCRUD;

import dao.VendaDAO;
import entidade.Venda;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import conexao.ConexaoBD;

public class VendaDAOTeste {
    public static void main(String[] args) {
        try (Connection connection = ConexaoBD.getConnection()) {
            VendaDAO vendaDAO = new VendaDAO(connection);

            // Teste de criação
            Venda novaVenda = new Venda();
            novaVenda.setClienteId(1);
            novaVenda.setStatusPedidoId(1);
            novaVenda.setValorTotal(BigDecimal.valueOf(2550.00));
            vendaDAO.create(novaVenda);
            System.out.println("Venda criada: " + novaVenda);

            // Teste de leitura
            Venda venda = vendaDAO.read(novaVenda.getVendaId());
            System.out.println("Venda lida: " + venda);

            // Teste de atualização
            venda.setValorTotal(BigDecimal.valueOf(2600.00));
            vendaDAO.update(venda);
            Venda vendaAtualizada = vendaDAO.read(venda.getVendaId());
            System.out.println("Venda atualizada: " + vendaAtualizada);

            // Teste de exclusão
            vendaDAO.delete(vendaAtualizada.getVendaId());
            Venda vendaDeletada = vendaDAO.read(vendaAtualizada.getVendaId());
            System.out.println("Venda deletada: " + (vendaDeletada == null ? "sucesso" : "falha"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
