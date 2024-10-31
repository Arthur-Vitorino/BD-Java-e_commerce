package testesCRUD;

import dao.ProdutoDAO;
import entidade.Produto;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

import conexao.ConexaoBD;

public class ProdutoDAOTeste {
    public static void main(String[] args) {
        try (Connection connection = ConexaoBD.getConnection()) {
            ProdutoDAO produtoDAO = new ProdutoDAO(connection);

            // Teste de criação
            Produto novoProduto = new Produto();
            novoProduto.setNome("Produto Teste");
            novoProduto.setDescricao("Descrição do Produto Teste");
            novoProduto.setPreco(BigDecimal.valueOf(199.99));
            novoProduto.setEstoque(100);
            novoProduto.setCategoriaId(1);
            novoProduto.setFornecedorId(1);
            produtoDAO.create(novoProduto);
            System.out.println("Produto criado: " + novoProduto);

            // Teste de leitura
            Produto produtoLido = produtoDAO.read(novoProduto.getProdutoId());
            System.out.println("Produto lido: " + produtoLido);

            // Teste de atualização
            produtoLido.setPreco(BigDecimal.valueOf(249.99));
            produtoDAO.update(produtoLido);
            Produto produtoAtualizado = produtoDAO.read(produtoLido.getProdutoId());
            System.out.println("Produto atualizado: " + produtoAtualizado);

            // Teste de exclusão
            produtoDAO.delete(produtoAtualizado.getProdutoId());
            Produto produtoDeletado = produtoDAO.read(produtoAtualizado.getProdutoId());
            System.out.println("Produto deletado: " + (produtoDeletado == null ? "sucesso" : "falha"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
