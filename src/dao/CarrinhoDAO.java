package dao;

import entidade.Carrinho;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * A classe CarrinhoDAO é responsável por realizar operações de acesso ao banco de dados
 * relacionadas à entidade Carrinho. Permite criar, ler, atualizar e excluir registros
 * de carrinhos de compras.
 */
public class CarrinhoDAO {
    private Connection connection;
/**
     * Construtor da classe CarrinhoDAO.
     */
    public CarrinhoDAO(Connection connection) {
        this.connection = connection;
    } //Cria um novo registro de carrinho no banco de dados.

    public void create(Carrinho carrinho) throws SQLException {    // SQL para inserir um novo carrinho
        String sql = "INSERT INTO carrinho (cliente_id, produto_id, quantidade) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, carrinho.getClienteId());  // Define os valores dos parâmetros do SQL
            stmt.setInt(2, carrinho.getProdutoId());
            stmt.setInt(3, carrinho.getQuantidade());
            stmt.executeUpdate(); // Executa a inserção

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {  // Obtém o ID gerado do carrinho inserido
                if (generatedKeys.next()) {
                    carrinho.setCarrinhoId(generatedKeys.getInt(1)); // Define o ID gerado para o carrinho
                }
            }
        }
    }
     /**
     * Lê um registro de carrinho do banco de dados pelo seu ID.
     *
     * @return O objeto Carrinho correspondente, ou null se não encontrado.
     * @throws SQLException se houver um erro ao tentar ler.
     */

    public Carrinho read(int carrinhoId) throws SQLException {
        String sql = "SELECT * FROM carrinho WHERE carrinho_id = ?";   // SQL para selecionar um carrinho pelo ID
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, carrinhoId);  // Define o ID do carrinho a ser buscado
            ResultSet rs = stmt.executeQuery();  // Executa a consulta
            if (rs.next()) {
                return mapResultSetToCarrinho(rs);  // Mapeia o ResultSet para um objeto Carrinho
            }
        }
        return null; // Retorna null se não encontrar o carrinho
    }
     /* Lê todos os registros de carrinhos do banco de dados.
     *
     * @return Uma lista de objetos Carrinho.
     * @throws SQLException se houver um erro ao tentar ler.
     */

    public List<Carrinho> readAll() throws SQLException {
        List<Carrinho> carrinhos = new ArrayList<>();  // Lista para armazenar todos os carrinhos
        String sql = "SELECT * FROM carrinho"; // SQL para selecionar todos os carrinhos
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);// Executa a consulta
            while (rs.next()) {
                carrinhos.add(mapResultSetToCarrinho(rs));    // Adiciona cada carrinho mapeado à lista
            }
        }
        return carrinhos; // Retorna a lista de carrinhos
    }
/**
     * Atualiza um registro de carrinho no banco de dados.
     * Atualiza os dados do carrinho baseado no seu ID.
     *
     * @throws SQLException se houver um erro ao tentar atualizar.
     */
    public void update(Carrinho carrinho) throws SQLException { 
        String sql = "UPDATE carrinho SET cliente_id = ?, produto_id = ?, quantidade = ? WHERE carrinho_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, carrinho.getClienteId());   // Define os valores dos parâmetros do SQL
            stmt.setInt(2, carrinho.getProdutoId());
            stmt.setInt(3, carrinho.getQuantidade());
            stmt.setInt(4, carrinho.getCarrinhoId());
            stmt.executeUpdate();
        }
    }

    public void delete(int carrinhoId) throws SQLException { //Exclui um registro de carrinho do banco de dados pelo seu ID.
        String sql = "DELETE FROM carrinho WHERE carrinho_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, carrinhoId);
            stmt.executeUpdate();
        }
    }

    private Carrinho mapResultSetToCarrinho(ResultSet rs) throws SQLException {
        Carrinho carrinho = new Carrinho(); // Mapeia os dados do ResultSet para o objeto Carrinho
        carrinho.setCarrinhoId(rs.getInt("carrinho_id"));
        carrinho.setClienteId(rs.getInt("cliente_id"));
        carrinho.setProdutoId(rs.getInt("produto_id"));
        carrinho.setQuantidade(rs.getInt("quantidade"));
        return carrinho; // Retorna o objeto Carrinho
    }
} // Comentario de Anna Júlia
