package testesCRUD;

import dao.CarrinhoDAO;
import entidade.Carrinho;
import java.sql.Connection;
import java.sql.SQLException;

import conexao.ConexaoBD;

public class CarrinhoDAOTeste {
    public static void main(String[] args) {
        try (Connection connection = ConexaoBD.getConnection()) {
            CarrinhoDAO carrinhoDAO = new CarrinhoDAO(connection);

            // Teste de criação
            Carrinho novoItemCarrinho = new Carrinho();
            novoItemCarrinho.setClienteId(1);
            novoItemCarrinho.setProdutoId(1);
            novoItemCarrinho.setQuantidade(3);
            carrinhoDAO.create(novoItemCarrinho);
            System.out.println("Item de carrinho criado: " + novoItemCarrinho);

            // Teste de leitura
            Carrinho itemCarrinhoLido = carrinhoDAO.read(novoItemCarrinho.getCarrinhoId());
            System.out.println("Item de carrinho lido: " + itemCarrinhoLido);

            // Teste de atualização
            itemCarrinhoLido.setQuantidade(5);
            carrinhoDAO.update(itemCarrinhoLido);
            Carrinho itemCarrinhoAtualizado = carrinhoDAO.read(itemCarrinhoLido.getCarrinhoId());
            System.out.println("Item de carrinho atualizado: " + itemCarrinhoAtualizado);

            // Teste de exclusão
            carrinhoDAO.delete(itemCarrinhoAtualizado.getCarrinhoId());
            Carrinho itemCarrinhoDeletado = carrinhoDAO.read(itemCarrinhoAtualizado.getCarrinhoId());
            System.out.println("Item de carrinho deletado: " + (itemCarrinhoDeletado == null ? "sucesso" : "falha"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}