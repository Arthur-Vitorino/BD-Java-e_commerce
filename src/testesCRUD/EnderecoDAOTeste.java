package testesCRUD;

import dao.EnderecoDAO;
import entidade.Endereco;
import java.sql.Connection;
import java.sql.SQLException;

import conexao.ConexaoBD;

public class EnderecoDAOTeste {
    public static void main(String[] args) {
        try (Connection connection = ConexaoBD.getConnection()) {
            EnderecoDAO enderecoDAO = new EnderecoDAO(connection);

            // Teste de criação
            Endereco novoEndereco = new Endereco();
            novoEndereco.setClienteId(1);
            novoEndereco.setRua("Rua Teste");
            novoEndereco.setNumero("123");
            novoEndereco.setComplemento("Apt 1");
            novoEndereco.setBairro("Centro");
            novoEndereco.setCidade("São Paulo");
            novoEndereco.setEstado("SP");
            novoEndereco.setCep("01000-000");
            enderecoDAO.create(novoEndereco);
            System.out.println("Endereço criado: " + novoEndereco);

            // Teste de leitura
            Endereco enderecoLido = enderecoDAO.read(novoEndereco.getEnderecoId());
            System.out.println("Endereço lido: " + enderecoLido);

            // Teste de atualização
            enderecoLido.setNumero("321");
            enderecoDAO.update(enderecoLido);
            Endereco enderecoAtualizado = enderecoDAO.read(enderecoLido.getEnderecoId());
            System.out.println("Endereço atualizado: " + enderecoAtualizado);

            // Teste de exclusão
            enderecoDAO.delete(enderecoAtualizado.getEnderecoId());
            Endereco enderecoDeletado = enderecoDAO.read(enderecoAtualizado.getEnderecoId());
            System.out.println("Endereço deletado: " + (enderecoDeletado == null ? "sucesso" : "falha"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
