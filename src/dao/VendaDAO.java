package dao;

import entidade.Venda;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A classe VendaDAO é responsável por realizar operações de acesso ao banco de dados
 * relacionadas à entidade Venda. Permite criar, ler, atualizar e excluir registros
 * de vendas.
 */
public class VendaDAO {
    private Connection connection; // Conexão com o banco de dados

    /**
     * Construtor da classe VendaDAO.
     * Inicializa a conexão com o banco de dados.
     *
     * @param connection A conexão com o banco de dados.
     */
    public VendaDAO(Connection connection) {
        this.connection = connection; // Armazena a conexão para uso posterior
    }

    /**
     * Cria um novo registro de venda no banco de dados.
     * Insere uma nova venda com o cliente, status e valor total fornecidos.
     *
     * @param venda O objeto Venda a ser inserido.
     * @throws SQLException se houver um erro ao tentar inserir.
     */
    public void create(Venda venda) throws SQLException {
        // SQL para inserir uma nova venda
        String sql = "INSERT INTO vendas (cliente_id, status_pedido_id, valor_total) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, venda.getClienteId()); // Define o ID do cliente
            stmt.setInt(2, venda.getStatusPedidoId()); // Define o status do pedido
            stmt.setBigDecimal(3, venda.getValorTotal()); // Define o valor total da venda
            stmt.executeUpdate(); // Executa a inserção

            // Obtém o ID gerado da venda inserida
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    venda.setVendaId(generatedKeys.getInt(1)); // Define o ID gerado
                }
            }
        }
    }

    /**
     * Lê um registro de venda do banco de dados pelo seu ID.
     *
     * @param vendaId O ID da venda a ser lida.
     * @return O objeto Venda correspondente, ou null se não encontrado.
     * @throws SQLException se houver um erro ao tentar ler.
     */
    public Venda read(int vendaId) throws SQLException {
        // SQL para selecionar uma venda pelo ID
        String sql = "SELECT * FROM vendas WHERE venda_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, vendaId); // Define o ID da venda a ser buscada
            ResultSet rs = stmt.executeQuery(); // Executa a consulta
            if (rs.next()) {
                return mapResultSetToVenda(rs); // Retorna a venda encontrada
            }
        }
        return null; // Retorna null se a venda não for encontrada
    }

    /**
     * Lê todos os registros de vendas do banco de dados.
     *
     * @return Uma lista de objetos Venda.
     * @throws SQLException se houver um erro ao tentar ler.
     */
    public List<Venda> readAll() throws SQLException {
        List<Venda> vendas = new ArrayList<>(); // Lista para armazenar as vendas
        String sql = "SELECT * FROM vendas"; // SQL para selecionar todas as vendas
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                // Mapeia os dados do ResultSet para um objeto Venda e adiciona à lista
                vendas.add(mapResultSetToVenda(rs));
            }
        }
        return vendas; // Retorna a lista de vendas
    }

    /**
     * Atualiza um registro de venda no banco de dados.
     * Atualiza os dados da venda baseado no seu ID.
     *
     * @param venda O objeto Venda com os dados atualizados.
     * @throws SQLException se houver um erro ao tentar atualizar.
     */
    public void update(Venda venda) throws SQLException {
        // SQL para atualizar uma venda
        String sql = "UPDATE vendas SET cliente_id = ?, status_pedido_id = ?, valor_total = ? WHERE venda_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, venda.getClienteId()); // Define o ID do cliente
            stmt.setInt(2, venda.getStatusPedidoId()); // Define o status do pedido
            stmt.setBigDecimal(3, venda.getValorTotal()); // Define o valor total da venda
            stmt.setInt(4, venda.getVendaId()); // Define o ID da venda a ser atualizado
            stmt.executeUpdate(); // Executa a atualização
        }
    }

    /**
     * Exclui um registro de venda do banco de dados pelo seu ID.
     *
     * @param vendaId O ID da venda a ser excluída.
     * @throws SQLException se houver um erro ao tentar excluir.
     */
    public void delete(int vendaId) throws SQLException {
        // SQL para deletar uma venda pelo ID
        String sql = "DELETE FROM vendas WHERE venda_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, vendaId); // Define o ID da venda a ser deletada
            stmt.executeUpdate(); // Executa a exclusão
        }
    }

    /**
     * Mapeia os dados de um ResultSet para um objeto Venda.
     *
     * @param rs O ResultSet contendo os dados da venda.
     * @return O objeto Venda mapeado.
     * @throws SQLException se houver um erro ao tentar mapear.
     */
    private Venda mapResultSetToVenda(ResultSet rs) throws SQLException {
        Venda venda = new Venda();
        venda.setVendaId(rs.getInt("venda_id")); // Define o ID da venda
        venda.setClienteId(rs.getInt("cliente_id")); // Define o ID do cliente
        venda.setStatusPedidoId(rs.getInt("status_pedido_id")); // Define o status do pedido
        venda.setValorTotal(rs.getBigDecimal("valor_total")); // Define o valor total da venda
        venda.setDataVenda(rs.getTimestamp("data_venda").toLocalDateTime()); // Define a data da venda
        return venda; // Retorna o objeto Venda
    }
}
//Comentario Arthur
