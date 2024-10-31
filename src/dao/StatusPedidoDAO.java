package dao;

import entidade.StatusPedido;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A classe StatusPedidoDAO é responsável por realizar operações de acesso ao banco de dados
 * relacionadas à entidade StatusPedido. Permite criar, ler, atualizar e excluir registros
 * de status de pedidos.
 */
public class StatusPedidoDAO {
    private Connection connection; // Conexão com o banco de dados

    /**
     * Construtor da classe StatusPedidoDAO.
     * Inicializa a conexão com o banco de dados.
     *
     * @param connection A conexão com o banco de dados.
     */
    public StatusPedidoDAO(Connection connection) {
        this.connection = connection; // Armazena a conexão para uso posterior
    }

    /**
     * Cria um novo registro de status de pedido no banco de dados.
     * Insere um novo status de pedido com a descrição fornecida.
     *
     * @param statusPedido O objeto StatusPedido a ser inserido.
     * @throws SQLException se houver um erro ao tentar inserir.
     */
    public void create(StatusPedido statusPedido) throws SQLException {
        // SQL para inserir um novo status de pedido
        String sql = "INSERT INTO status_pedido (descricao) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, statusPedido.getDescricao()); // Define a descrição do status
            stmt.executeUpdate(); // Executa a inserção

            // Obtém o ID gerado do status inserido
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    statusPedido.setStatusPedidoId(generatedKeys.getInt(1)); // Define o ID gerado
                }
            }
        }
    }

    /**
     * Lê um registro de status de pedido do banco de dados pelo seu ID.
     *
     * @param statusPedidoId O ID do status de pedido a ser lido.
     * @return O objeto StatusPedido correspondente, ou null se não encontrado.
     * @throws SQLException se houver um erro ao tentar ler.
     */
    public StatusPedido read(int statusPedidoId) throws SQLException {
        // SQL para selecionar um status de pedido pelo ID
        String sql = "SELECT * FROM status_pedido WHERE status_pedido_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, statusPedidoId); // Define o ID do status a ser buscado
            ResultSet rs = stmt.executeQuery(); // Executa a consulta
            if (rs.next()) {
                return mapResultSetToStatusPedido(rs); // Retorna o status encontrado
            }
        }
        return null; // Retorna null se o status não for encontrado
    }

    /**
     * Lê todos os registros de status de pedidos do banco de dados.
     *
     * @return Uma lista de objetos StatusPedido.
     * @throws SQLException se houver um erro ao tentar ler.
     */
    public List<StatusPedido> readAll() throws SQLException {
        List<StatusPedido> statusPedidos = new ArrayList<>(); // Lista para armazenar os status
        String sql = "SELECT * FROM status_pedido"; // SQL para selecionar todos os status
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                // Mapeia os dados do ResultSet para um objeto StatusPedido e adiciona à lista
                statusPedidos.add(mapResultSetToStatusPedido(rs));
            }
        }
        return statusPedidos; // Retorna a lista de status
    }

    /**
     * Atualiza um registro de status de pedido no banco de dados.
     * Atualiza a descrição do status baseado no seu ID.
     *
     * @param statusPedido O objeto StatusPedido com os dados atualizados.
     * @throws SQLException se houver um erro ao tentar atualizar.
     */
    public void update(StatusPedido statusPedido) throws SQLException {
        // SQL para atualizar um status de pedido
        String sql = "UPDATE status_pedido SET descricao = ? WHERE status_pedido_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, statusPedido.getDescricao()); // Define a nova descrição
            stmt.setInt(2, statusPedido.getStatusPedidoId()); // Define o ID do status a ser atualizado
            stmt.executeUpdate(); // Executa a atualização
        }
    }

    /**
     * Exclui um registro de status de pedido do banco de dados pelo seu ID.
     *
     * @param statusPedidoId O ID do status de pedido a ser excluído.
     * @throws SQLException se houver um erro ao tentar excluir.
     */
    public void delete(int statusPedidoId) throws SQLException {
        // SQL para deletar um status de pedido pelo ID
        String sql = "DELETE FROM status_pedido WHERE status_pedido_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, statusPedidoId); // Define o ID do status a ser deletado
            stmt.executeUpdate(); // Executa a exclusão
        }
    }

    /**
     * Mapeia os dados de um ResultSet para um objeto StatusPedido.
     *
     * @param rs O ResultSet contendo os dados do status de pedido.
     * @return O objeto StatusPedido mapeado.
     * @throws SQLException se houver um erro ao tentar mapear.
     */
    private StatusPedido mapResultSetToStatusPedido(ResultSet rs) throws SQLException {
        StatusPedido statusPedido = new StatusPedido();
        statusPedido.setStatusPedidoId(rs.getInt("status_pedido_id")); // Define o ID do status
        statusPedido.setDescricao(rs.getString("descricao")); // Define a descrição do status
        return statusPedido; // Retorna o objeto StatusPedido
    }
}
//Comentario Ana Carolina
