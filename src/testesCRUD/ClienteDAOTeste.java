package testesCRUD;

import dao.ClienteDAO;
import entidade.Cliente;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import conexao.ConexaoBD;

public class ClienteDAOTeste {
    public static void main(String[] args) {
        try (Connection connection = ConexaoBD.getConnection()) {
            ClienteDAO clienteDAO = new ClienteDAO(connection);

            // Teste de criação
            Cliente novoCliente = new Cliente();
            novoCliente.setNome("Novo Cliente Teste");
            novoCliente.setEmail("cliente@teste.com");
            novoCliente.setTelefone("11999999999");
            novoCliente.setDataNascimento(LocalDate.of(1990, 5, 15)); // Usando LocalDate
            novoCliente.setCpf("12345678901");
            novoCliente.setSenha("senha123");
            clienteDAO.create(novoCliente);
            System.out.println("Cliente criado: " + novoCliente);

            // Teste de leitura
            Cliente clienteLido = clienteDAO.read(novoCliente.getClienteId());
            System.out.println("Cliente lido: " + clienteLido);

            // Teste de atualização
            clienteLido.setTelefone("11988888888");
            clienteDAO.update(clienteLido);
            Cliente clienteAtualizado = clienteDAO.read(clienteLido.getClienteId());
            System.out.println("Cliente atualizado: " + clienteAtualizado);

            // Teste de exclusão
            clienteDAO.delete(clienteAtualizado.getClienteId());
            Cliente clienteDeletado = clienteDAO.read(clienteAtualizado.getClienteId());
            System.out.println("Cliente deletado: " + (clienteDeletado == null ? "sucesso" : "falha"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
