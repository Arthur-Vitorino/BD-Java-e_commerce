package dao;

import entidade.ItemVenda;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A classe ItemVendaDAO é responsável por realizar operações de acesso ao banco de dados
 * relacionadas à entidade ItemVenda. Permite criar, ler, atualizar e excluir registros
 * de itens de venda.
 */
public class ItemVendaDAO {
    private Connection connection; // Conexão com o banco de dados

    /**
     * Construtor da classe ItemVendaDAO.
     * Inicializa a conexão com o banco de dados.
     *
     * @param connection A conexão com o banco de dados.
     */
    public ItemVendaDAO(Connection connection) {
        this.connection = connection; // Armazena a conexão para uso posterior
    }

    /**
     * Cria um novo registro de item de venda no banco de dados.
     * Insere um novo item de venda com os dados de venda_id, produto_id, quantidade e preco_unitario.
     *
     * @param itemVenda O objeto ItemVenda a ser inserido.
     * @throws SQLException se houver um erro ao tentar inserir.
     */
    public void create(ItemVenda itemVenda) throws SQLException {
        // SQL para inserir um novo item de venda
        String sql = "INSERT INTO itens_venda (venda_id, produto_id, quantidade, preco_unitario) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Define os valores dos parâmetros do SQL
            stmt.setInt(1, itemVenda.getVendaId());
            stmt.setInt(2, itemVenda.getProdutoId());
            stmt.setInt(3, itemVenda.getQuantidade());
            stmt.setBigDecimal(4, itemVenda.getPrecoUnitario());
            stmt.executeUpdate(); // Executa a inserção

            // Obtém o ID gerado do item de venda inserido
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    itemVenda.setItemVendaId(generatedKeys.getInt(1)); // Define o ID gerado para o item de venda
                }
            }
        }
    }

    /**
     * Lê um registro de item de venda do banco de dados pelo seu ID.
     *
     * @param itemVendaId O ID do item de venda a ser lido.
     * @return O objeto ItemVenda correspondente, ou null se não encontrado.
     * @throws SQLException se houver um erro ao tentar ler.
     */
    public ItemVenda read(int itemVendaId) throws SQLException {
        // SQL para selecionar um item de venda pelo ID
        String sql = "SELECT * FROM itens_venda WHERE item_venda_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, itemVendaId); // Define o ID do item de venda a ser buscado
            ResultSet rs = stmt.executeQuery(); // Executa a consulta
            if (rs.next()) {
                // Mapeia os dados do ResultSet para um objeto ItemVenda
                return mapResultSetToItemVenda(rs);
            }
        }
        return null; // Retorna null se o item de venda não for encontrado
    }

    /**
     * Lê todos os registros de itens de venda do banco de dados.
     *
     * @return Uma lista de objetos ItemVenda.
     * @throws SQLException se houver um erro ao tentar ler.
     */
    public List<ItemVenda> readAll() throws SQLException {
        List<ItemVenda> itensVenda = new ArrayList<>(); // Lista para armazenar os itens de venda
        String sql = "SELECT * FROM itens_venda"; // SQL para selecionar todos os itens de venda
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql); // Executa a consulta
            while (rs.next()) {
                // Mapeia os dados do ResultSet para um objeto ItemVenda e adiciona à lista
                itensVenda.add(mapResultSetToItemVenda(rs));
            }
        }
        return itensVenda; // Retorna a lista de itens de venda
    }

    /**
     * Atualiza um registro de item de venda no banco de dados.
     * Atualiza os dados do item de venda baseado no seu ID.
     *
     * @param itemVenda O objeto ItemVenda com os dados atualizados.
     * @throws SQLException se houver um erro ao tentar atualizar.
     */
    public void update(ItemVenda itemVenda) throws SQLException {
        // SQL para atualizar um item de venda
        String sql = "UPDATE itens_venda SET venda_id = ?, produto_id = ?, quantidade = ?, preco_unitario = ? WHERE item_venda_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Define os valores dos parâmetros do SQL
            stmt.setInt(1, itemVenda.getVendaId());
            stmt.setInt(2, itemVenda.getProdutoId());
            stmt.setInt(3, itemVenda.getQuantidade());
            stmt.setBigDecimal(4, itemVenda.getPrecoUnitario());
            stmt.setInt(5, itemVenda.getItemVendaId()); // Define o ID do item de venda a ser atualizado
            stmt.executeUpdate(); // Executa a atualização
        }
    }

    /**
     * Exclui um registro de item de venda do banco de dados pelo seu ID.
     *
     * @param itemVendaId O ID do item de venda a ser excluído.
     * @throws SQLException se houver um erro ao tentar excluir.
     */
    public void delete(int itemVendaId) throws SQLException {
        // SQL para deletar um item de venda pelo ID
        String sql = "DELETE FROM itens_venda WHERE item_venda_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, itemVendaId); // Define o ID do item de venda a ser deletado
            stmt.executeUpdate(); // Executa a exclusão
        }
    }

    /**
     * Mapeia os dados do ResultSet para um objeto ItemVenda.
     *
     * @param rs O ResultSet contendo os dados do item de venda.
     * @return O objeto ItemVenda mapeado.
     * @throws SQLException se houver um erro ao acessar os dados.
     */
    private ItemVenda mapResultSetToItemVenda(ResultSet rs) throws SQLException {
        ItemVenda itemVenda = new ItemVenda(); // Cria uma nova instância de ItemVenda
        itemVenda.setItemVendaId(rs.getInt("item_venda_id"));
        itemVenda.setVendaId(rs.getInt("venda_id"));
        itemVenda.setProdutoId(rs.getInt("produto_id"));
        itemVenda.setQuantidade(rs.getInt("quantidade"));
        itemVenda.setPrecoUnitario(rs.getBigDecimal("preco_unitario"));
        return itemVenda; // Retorna o objeto ItemVenda mapeado
    }
}
//Comentario Anna Júlia
