package dao;

import entidade.Produto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A classe ProdutoDAO é responsável por realizar operações de acesso ao banco de dados
 * relacionadas à entidade Produto. Permite criar, ler, atualizar e excluir registros
 * de produtos.
 */
public class ProdutoDAO {
    private Connection connection; // Conexão com o banco de dados

    /**
     * Construtor da classe ProdutoDAO.
     * Inicializa a conexão com o banco de dados.
     *
     * @param connection A conexão com o banco de dados.
     */
    public ProdutoDAO(Connection connection) {
        this.connection = connection; // Armazena a conexão para uso posterior
    }

    /**
     * Cria um novo registro de produto no banco de dados.
     * Insere um novo produto com os dados de nome, descrição, preço, estoque, categoria_id e fornecedor_id.
     *
     * @param produto O objeto Produto a ser inserido.
     * @throws SQLException se houver um erro ao tentar inserir.
     */
    public void create(Produto produto) throws SQLException {
        // SQL para inserir um novo produto
        String sql = "INSERT INTO produtos (nome, descricao, preco, estoque, categoria_id, fornecedor_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Define os valores dos parâmetros do SQL
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setDouble(3, produto.getPreco());
            stmt.setInt(4, produto.getEstoque());
            stmt.setInt(5, produto.getCategoriaId());
            stmt.setInt(6, produto.getFornecedorId());
            stmt.executeUpdate(); // Executa a inserção

            // Obtém o ID gerado do produto inserido
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    produto.setProdutoId(generatedKeys.getInt(1)); // Define o ID gerado para o produto
                }
            }
        }
    }

    /**
     * Lê um registro de produto do banco de dados pelo seu ID.
     *
     * @param produtoId O ID do produto a ser lido.
     * @return O objeto Produto correspondente, ou null se não encontrado.
     * @throws SQLException se houver um erro ao tentar ler.
     */
    public Produto read(int produtoId) throws SQLException {
        // SQL para selecionar um produto pelo ID
        String sql = "SELECT * FROM produtos WHERE produto_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, produtoId); // Define o ID do produto a ser buscado
            ResultSet rs = stmt.executeQuery(); // Executa a consulta
            if (rs.next()) {
                // Mapeia os dados do ResultSet para um objeto Produto
                Produto produto = new Produto();
                produto.setProdutoId(rs.getInt("produto_id"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setPreco(rs.getDouble("preco"));
                produto.setEstoque(rs.getInt("estoque"));
                produto.setCategoriaId(rs.getInt("categoria_id"));
                produto.setFornecedorId(rs.getInt("fornecedor_id"));
                return produto; // Retorna o produto encontrado
            }
        }
        return null; // Retorna null se o produto não for encontrado
    }

    /**
     * Lê todos os registros de produtos do banco de dados.
     *
     * @return Uma lista de objetos Produto.
     * @throws SQLException se houver um erro ao tentar ler.
     */
    public List<Produto> readAll() throws SQLException {
        List<Produto> produtos = new ArrayList<>(); // Lista para armazenar os produtos
        String sql = "SELECT * FROM produtos"; // SQL para selecionar todos os produtos
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                // Mapeia os dados do ResultSet para um objeto Produto e adiciona à lista
                Produto produto = new Produto();
                produto.setProdutoId(rs.getInt("produto_id"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setPreco(rs.getDouble("preco"));
                produto.setEstoque(rs.getInt("estoque"));
                produto.setCategoriaId(rs.getInt("categoria_id"));
                produto.setFornecedorId(rs.getInt("fornecedor_id"));
                produtos.add(produto);
            }
        }
        return produtos; // Retorna a lista de produtos
    }

    /**
     * Atualiza um registro de produto no banco de dados.
     * Atualiza os dados do produto baseado no seu ID.
     *
     * @param produto O objeto Produto com os dados atualizados.
     * @throws SQLException se houver um erro ao tentar atualizar.
     */
    public void update(Produto produto) throws SQLException {
        // SQL para atualizar um produto
        String sql = "UPDATE produtos SET nome = ?, descricao = ?, preco = ?, estoque = ?, categoria_id = ?, fornecedor_id = ? WHERE produto_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Define os valores dos parâmetros do SQL
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setDouble(3, produto.getPreco());
            stmt.setInt(4, produto.getEstoque());
            stmt.setInt(5, produto.getCategoriaId());
            stmt.setInt(6, produto.getFornecedorId());
            stmt.setInt(7, produto.getProdutoId()); // Define o ID do produto a ser atualizado
            stmt.executeUpdate(); // Executa a atualização
        }
    }

    /**
     * Exclui um registro de produto do banco de dados pelo seu ID.
     *
     * @param produtoId O ID do produto a ser excluído.
     * @throws SQLException se houver um erro ao tentar excluir.
     */
    public void delete(int produtoId) throws SQLException {
        // SQL para deletar um produto pelo ID
        String sql = "DELETE FROM produtos WHERE produto_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, produtoId); // Define o ID do produto a ser deletado
            stmt.executeUpdate(); // Executa a exclusão
        }
    }
}
//Comentario Ana Carolina
