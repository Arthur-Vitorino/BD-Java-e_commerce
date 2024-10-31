package testesCRUD;

import dao.FornecedorDAO;
import entidade.Fornecedor;
import java.sql.Connection;
import java.sql.SQLException;

import conexao.ConexaoBD;

public class FornecedorDAOTeste {
    public static void main(String[] args) {
        try (Connection connection = ConexaoBD.getConnection()) {
            FornecedorDAO fornecedorDAO = new FornecedorDAO(connection);

            // Teste de criação
            Fornecedor novoFornecedor = new Fornecedor();
            novoFornecedor.setNome("Fornecedor Teste");
            novoFornecedor.setContato("Contato Teste");
            novoFornecedor.setTelefone("11999999999");
            novoFornecedor.setEmail("fornecedor@teste.com");
            novoFornecedor.setEndereco("Rua Teste, 123");
            fornecedorDAO.create(novoFornecedor);
            System.out.println("Fornecedor criado: " + novoFornecedor);

            // Teste de leitura
            Fornecedor fornecedorLido = fornecedorDAO.read(novoFornecedor.getFornecedorId());
            System.out.println("Fornecedor lido: " + fornecedorLido);

            // Teste de atualização
            fornecedorLido.setContato("Novo Contato");
            fornecedorDAO.update(fornecedorLido);
            Fornecedor fornecedorAtualizado = fornecedorDAO.read(fornecedorLido.getFornecedorId());
            System.out.println("Fornecedor atualizado: " + fornecedorAtualizado);

            // Teste de exclusão
            fornecedorDAO.delete(fornecedorAtualizado.getFornecedorId());
            Fornecedor fornecedorDeletado = fornecedorDAO.read(fornecedorAtualizado.getFornecedorId());
            System.out.println("Fornecedor deletado: " + (fornecedorDeletado == null ? "sucesso" : "falha"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
