package testesCRUD;

import dao.CategoriaDAO;
import entidade.Categoria;
import java.sql.Connection;
import java.sql.SQLException;

import conexao.ConexaoBD;

public class CategoriaDAOTeste {
    public static void main(String[] args) {
        try (Connection connection = ConexaoBD.getConnection()) {
            CategoriaDAO categoriaDAO = new CategoriaDAO(connection);

            // Teste de criação
            Categoria novaCategoria = new Categoria();
            novaCategoria.setNome("Categoria Teste");
            novaCategoria.setDescricao("Descrição da Categoria Teste");
            categoriaDAO.create(novaCategoria);
            System.out.println("Categoria criada: " + novaCategoria);

            // Teste de leitura
            Categoria categoriaLida = categoriaDAO.read(novaCategoria.getCategoriaId());
            System.out.println("Categoria lida: " + categoriaLida);

            // Teste de atualização
            categoriaLida.setDescricao("Nova descrição da Categoria Teste");
            categoriaDAO.update(categoriaLida);
            Categoria categoriaAtualizada = categoriaDAO.read(categoriaLida.getCategoriaId());
            System.out.println("Categoria atualizada: " + categoriaAtualizada);

            // Teste de exclusão
            categoriaDAO.delete(categoriaAtualizada.getCategoriaId());
            Categoria categoriaDeletada = categoriaDAO.read(categoriaAtualizada.getCategoriaId());
            System.out.println("Categoria deletada: " + (categoriaDeletada == null ? "sucesso" : "falha"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
