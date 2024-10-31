package dao;

import entidade.Fornecedor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A classe FornecedorDAO é responsável por realizar operações de acesso ao banco de dados
 * relacionadas à entidade Fornecedor. Permite criar, ler, atualizar e excluir registros
 * de fornecedores.
 */
public class FornecedorDAO {
    private Connection connection; // Conexão com o banco de dados

    /**
     * Construtor da classe FornecedorDAO.
     * Inicializa a conexão com o banco de dados.
     *
     * @param connection A conexão com o banco de dados.
     */
    public FornecedorDAO(Connection connection) {
        this.connection = connection; // Armazena a conexão para uso posterior
    }

    /**
     * Cria um novo registro de fornecedor no banco de dados.
     * Insere um novo fornecedor com os dados de nome, contato, telefone, email e endereço.
     *
     * @param fornecedor O objeto Fornecedor a ser inserido.
     * @throws SQLException se houver um erro ao tentar inserir.
     */
    public void create(Fornecedor fornecedor) throws SQLException {
        // SQL para inserir um novo fornecedor
        String sql = "INSERT INTO fornecedores (nome, contato, telefone, email, endereco) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Define os valores dos parâmetros do SQL
            stmt.setString(1, fornecedor.getNome());
            stmt.setString(2, fornecedor.getContato());
            stmt.setString(3, fornecedor.getTelefone());
            stmt.setString(4, fornecedor.getEmail());
            stmt.setString(5, fornecedor.getEndereco());
            stmt.executeUpdate(); // Executa a inserção

            // Obtém o ID gerado do fornecedor inserido
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    fornecedor.setFornecedorId(generatedKeys.getInt(1)); // Define o ID gerado para o fornecedor
                }
            }
        }
    }

    /**
     * Lê um registro de fornecedor do banco de dados pelo seu ID.
     *
     * @param fornecedorId O ID do fornecedor a ser lido.
     * @return O objeto Fornecedor correspondente, ou null se não encontrado.
     * @throws SQLException se houver um erro ao tentar ler.
     */
    public Fornecedor read(int fornecedorId) throws SQLException {
        // SQL para selecionar um fornecedor pelo ID
        String sql = "SELECT * FROM fornecedores WHERE fornecedor_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, fornecedorId); // Define o ID do fornecedor a ser buscado
            ResultSet rs = stmt.executeQuery(); // Executa a consulta
            if (rs.next()) {
                // Mapeia os dados do ResultSet para um objeto Fornecedor
                return mapResultSetToFornecedor(rs);
            }
        }
        return null; // Retorna null se o fornecedor não for encontrado
    }

    /**
     * Lê todos os registros de fornecedores do banco de dados.
     *
     * @return Uma lista de objetos Fornecedor.
     * @throws SQLException se houver um erro ao tentar ler.
     */
    public List<Fornecedor> readAll() throws SQLException {
        List<Fornecedor> fornecedores = new ArrayList<>(); // Lista para armazenar os fornecedores
        String sql = "SELECT * FROM fornecedores"; // SQL para selecionar todos os fornecedores
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql); // Executa a consulta
            while (rs.next()) {
                // Mapeia os dados do ResultSet para um objeto Fornecedor e adiciona à lista
                fornecedores.add(mapResultSetToFornecedor(rs));
            }
        }
        return fornecedores; // Retorna a lista de fornecedores
    }

    /**
     * Atualiza um registro de fornecedor no banco de dados.
     * Atualiza os dados do fornecedor baseado no seu ID.
     *
     * @param fornecedor O objeto Fornecedor com os dados atualizados.
     * @throws SQLException se houver um erro ao tentar atualizar.
     */
    public void update(Fornecedor fornecedor) throws SQLException {
        // SQL para atualizar um fornecedor
        String sql = "UPDATE fornecedores SET nome = ?, contato = ?, telefone = ?, email = ?, endereco = ? WHERE fornecedor_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Define os valores dos parâmetros do SQL
            stmt.setString(1, fornecedor.getNome());
            stmt.setString(2, fornecedor.getContato());
            stmt.setString(3, fornecedor.getTelefone());
            stmt.setString(4, fornecedor.getEmail());
            stmt.setString(5, fornecedor.getEndereco());
            stmt.setInt(6, fornecedor.getFornecedorId()); // Define o ID do fornecedor a ser atualizado
            stmt.executeUpdate(); // Executa a atualização
        }
    }

    /**
     * Exclui um registro de fornecedor do banco de dados pelo seu ID.
     *
     * @param fornecedorId O ID do fornecedor a ser excluído.
     * @throws SQLException se houver um erro ao tentar excluir.
     */
    public void delete(int fornecedorId) throws SQLException {
        // SQL para deletar um fornecedor pelo ID
        String sql = "DELETE FROM fornecedores WHERE fornecedor_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, fornecedorId); // Define o ID do fornecedor a ser deletado
            stmt.executeUpdate(); // Executa a exclusão
        }
    }

    /**
     * Mapeia os dados do ResultSet para um objeto Fornecedor.
     *
     * @param rs O ResultSet contendo os dados do fornecedor.
     * @return O objeto Fornecedor mapeado.
     * @throws SQLException se houver um erro ao acessar os dados.
     */
    private Fornecedor mapResultSetToFornecedor(ResultSet rs) throws SQLException {
        Fornecedor fornecedor = new Fornecedor(); // Cria uma nova instância de Fornecedor
        fornecedor.setFornecedorId(rs.getInt("fornecedor_id"));
        fornecedor.setNome(rs.getString("nome"));
        fornecedor.setContato(rs.getString("contato"));
        fornecedor.setTelefone(rs.getString("telefone"));
        fornecedor.setEmail(rs.getString("email"));
        fornecedor.setEndereco(rs.getString("endereco"));
        return fornecedor; // Retorna o objeto Fornecedor mapeado
    }
}
//Comentario Arthur
