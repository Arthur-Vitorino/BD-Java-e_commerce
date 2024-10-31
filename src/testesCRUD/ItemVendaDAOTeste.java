package testesCRUD;

import dao.ItemVendaDAO;
import entidade.ItemVenda;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

import conexao.ConexaoBD;

public class ItemVendaDAOTeste {
    public static void main(String[] args) {
        try (Connection connection = ConexaoBD.getConnection()) {
            ItemVendaDAO itemVendaDAO = new ItemVendaDAO(connection);

            // Teste de criação
            ItemVenda novoItem = new ItemVenda();
            novoItem.setVendaId(1);
            novoItem.setProdutoId(1);
            novoItem.setQuantidade(2);
            novoItem.setPrecoUnitario(BigDecimal.valueOf(1250.00));
            itemVendaDAO.create(novoItem);
            System.out.println("Item de venda criado: " + novoItem);

            // Teste de leitura
            ItemVenda itemLido = itemVendaDAO.read(novoItem.getItemVendaId());
            System.out.println("Item de venda lido: " + itemLido);

            // Teste de atualização
            itemLido.setQuantidade(3);
            itemVendaDAO.update(itemLido);
            ItemVenda itemAtualizado = itemVendaDAO.read(itemLido.getItemVendaId());
            System.out.println("Item de venda atualizado: " + itemAtualizado);

            // Teste de exclusão
            itemVendaDAO.delete(itemAtualizado.getItemVendaId());
            ItemVenda itemDeletado = itemVendaDAO.read(itemAtualizado.getItemVendaId());
            System.out.println("Item de venda deletado: " + (itemDeletado == null ? "sucesso" : "falha"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
