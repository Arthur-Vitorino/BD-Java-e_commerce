package dao;

import entidade.Pagamento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A classe PagamentoDAO é responsável por realizar operações de acesso ao banco de dados
 * relacionadas à entidade Pagamento. Permite criar, ler, atualizar e excluir registros
 * de pagamentos.
 */
public class PagamentoDAO {
    private Connection connection; // Conexão com o banco de dados

    /**
     * Construtor da classe PagamentoDAO.
     * Inicializa a conexão com o banco de dados.
     *
     * @param connection A conexão com o banco de dados.
     */
    public PagamentoDAO(Connection connection) {
        this.connection = connection; // Armazena a conexão para uso posterior
    }

    /**
     * Cria um novo registro de pagamento no banco de dados.
     * Insere um novo pagamento com os dados de venda_id, valor, metodo_pagamento e status.
     *
     * @param pagamento O objeto Pagamento a ser inserido.
     * @throws SQLException se houver um erro ao tentar inserir.
     */
    public void create(Pagamento pagamento) throws SQLException {
        // SQL para inserir um novo pagamento
        String sql = "INSERT INTO pagamentos (venda_id, valor, metodo_pagamento, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Define os valores dos parâmetros do SQL
            stmt.setInt(1, pagamento.getVendaId());
            stmt.setBigDecimal(2, pagamento.getValor());
            stmt.setString(3, pagamento.getMetodoPagamento());
            stmt.setString(4, pagamento.getStatus());
            stmt.executeUpdate(); // Executa a inserção

            // Obtém o ID gerado do pagamento inserido
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    pagamento.setPagamentoId(generatedKeys.getInt(1)); // Define o ID gerado para o pagamento
                }
            }
        }
    }

    /**
     * Lê um registro de pagamento do banco de dados pelo seu ID.
     *
     * @param pagamentoId O ID do pagamento a ser lido.
     * @return O objeto Pagamento correspondente, ou null se não encontrado.
     * @throws SQLException se houver um erro ao tentar ler.
     */
    public Pagamento read(int pagamentoId) throws SQLException {
        // SQL para selecionar um pagamento pelo ID
        String sql = "SELECT * FROM pagamentos WHERE pagamento_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, pagamentoId); // Define o ID do pagamento a ser buscado
            ResultSet rs = stmt.executeQuery(); // Executa a consulta
            if (rs.next()) {
                // Mapeia os dados do ResultSet para um objeto Pagamento
                return mapResultSetToPagamento(rs);
            }
        }
        return null; // Retorna null se o pagamento não for encontrado
    }

    /**
     * Lê todos os registros de pagamentos do banco de dados.
     *
     * @return Uma lista de objetos Pagamento.
     * @throws SQLException se houver um erro ao tentar ler.
     */
    public List<Pagamento> readAll() throws SQLException {
        List<Pagamento> pagamentos = new ArrayList<>(); // Lista para armazenar os pagamentos
        String sql = "SELECT * FROM pagamentos"; // SQL para selecionar todos os pagamentos
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql); // Executa a consulta
            while (rs.next()) {
                // Mapeia os dados do ResultSet para um objeto Pagamento e adiciona à lista
                pagamentos.add(mapResultSetToPagamento(rs));
            }
        }
        return pagamentos; // Retorna a lista de pagamentos
    }

    /**
     * Atualiza um registro de pagamento no banco de dados.
     * Atualiza os dados do pagamento baseado no seu ID.
     *
     * @param pagamento O objeto Pagamento com os dados atualizados.
     * @throws SQLException se houver um erro ao tentar atualizar.
     */
    public void update(Pagamento pagamento) throws SQLException {
        // SQL para atualizar um pagamento
        String sql = "UPDATE pagamentos SET venda_id = ?, valor = ?, metodo_pagamento = ?, status = ? WHERE pagamento_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Define os valores dos parâmetros do SQL
            stmt.setInt(1, pagamento.getVendaId());
            stmt.setBigDecimal(2, pagamento.getValor());
            stmt.setString(3, pagamento.getMetodoPagamento());
            stmt.setString(4, pagamento.getStatus());
            stmt.setInt(5, pagamento.getPagamentoId()); // Define o ID do pagamento a ser atualizado
            stmt.executeUpdate(); // Executa a atualização
        }
    }

    /**
     * Exclui um registro de pagamento do banco de dados pelo seu ID.
     *
     * @param pagamentoId O ID do pagamento a ser excluído.
     * @throws SQLException se houver um erro ao tentar excluir.
     */
    public void delete(int pagamentoId) throws SQLException {
        // SQL para deletar um pagamento pelo ID
        String sql = "DELETE FROM pagamentos WHERE pagamento_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, pagamentoId); // Define o ID do pagamento a ser deletado
            stmt.executeUpdate(); // Executa a exclusão
        }
    }

    /**
     * Mapeia os dados do ResultSet para um objeto Pagamento.
     *
     * @param rs O ResultSet contendo os dados do pagamento.
     * @return O objeto Pagamento mapeado.
     * @throws SQLException se houver um erro ao acessar os dados.
     */
    private Pagamento mapResultSetToPagamento(ResultSet rs) throws SQLException {
        Pagamento pagamento = new Pagamento(); // Cria uma nova instância de Pagamento
        pagamento.setPagamentoId(rs.getInt("pagamento_id"));
        pagamento.setVendaId(rs.getInt("venda_id"));
        pagamento.setValor(rs.getBigDecimal("valor"));
        pagamento.setMetodoPagamento(rs.getString("metodo_pagamento"));
        pagamento.setStatus(rs.getString("status"));
        return pagamento; // Retorna o objeto Pagamento mapeado
    }
}
//Comentario Ana Carolina
