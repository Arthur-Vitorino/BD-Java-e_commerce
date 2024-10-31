package dao;

import entidade.Endereco;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A classe EnderecoDAO é responsável por realizar operações de acesso ao banco de dados
 * relacionadas à entidade Endereco. Permite criar, ler, atualizar e excluir registros
 * de endereços.
 */
public class EnderecoDAO {
    private Connection connection; // Conexão com o banco de dados

    /**
     * Construtor da classe EnderecoDAO.
     * Inicializa a conexão com o banco de dados.
     *
     * @param connection A conexão com o banco de dados.
     */
    public EnderecoDAO(Connection connection) {
        this.connection = connection; // Armazena a conexão para uso posterior
    }

    /**
     * Cria um novo registro de endereço no banco de dados.
     * Insere um novo endereço com os dados do cliente, rua, número, complemento, 
     * bairro, cidade, estado e CEP.
     *
     * @param endereco O objeto Endereco a ser inserido.
     * @throws SQLException se houver um erro ao tentar inserir.
     */
    public void create(Endereco endereco) throws SQLException {
        // SQL para inserir um novo endereço
        String sql = "INSERT INTO enderecos (cliente_id, rua, numero, complemento, bairro, cidade, estado, cep) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Define os valores dos parâmetros do SQL
            stmt.setInt(1, endereco.getClienteId());
            stmt.setString(2, endereco.getRua());
            stmt.setString(3, endereco.getNumero());
            stmt.setString(4, endereco.getComplemento());
            stmt.setString(5, endereco.getBairro());
            stmt.setString(6, endereco.getCidade());
            stmt.setString(7, endereco.getEstado());
            stmt.setString(8, endereco.getCep());
            stmt.executeUpdate(); // Executa a inserção

            // Obtém o ID gerado do endereço inserido
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    endereco.setEnderecoId(generatedKeys.getInt(1)); // Define o ID gerado para o endereço
                }
            }
        }
    }

    /**
     * Lê um registro de endereço do banco de dados pelo seu ID.
     *
     * @param enderecoId O ID do endereço a ser lido.
     * @return O objeto Endereco correspondente, ou null se não encontrado.
     * @throws SQLException se houver um erro ao tentar ler.
     */
    public Endereco read(int enderecoId) throws SQLException {
        // SQL para selecionar um endereço pelo ID
        String sql = "SELECT * FROM enderecos WHERE endereco_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, enderecoId); // Define o ID do endereço a ser buscado
            ResultSet rs = stmt.executeQuery(); // Executa a consulta
            if (rs.next()) {
                // Mapeia os dados do ResultSet para um objeto Endereco
                return mapResultSetToEndereco(rs);
            }
        }
        return null; // Retorna null se o endereço não for encontrado
    }

    /**
     * Lê todos os registros de endereços associados a um cliente pelo seu ID.
     *
     * @param clienteId O ID do cliente cujos endereços devem ser lidos.
     * @return Uma lista de objetos Endereco.
     * @throws SQLException se houver um erro ao tentar ler.
     */
    public List<Endereco> readByClienteId(int clienteId) throws SQLException {
        List<Endereco> enderecos = new ArrayList<>(); // Lista para armazenar os endereços
        String sql = "SELECT * FROM enderecos WHERE cliente_id = ?"; // SQL para selecionar endereços de um cliente
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, clienteId); // Define o ID do cliente a ser buscado
            try (ResultSet rs = stmt.executeQuery()) { // Executa a consulta
                while (rs.next()) {
                    // Mapeia os dados do ResultSet para um objeto Endereco e adiciona à lista
                    enderecos.add(mapResultSetToEndereco(rs));
                }
            }
        }
        return enderecos; // Retorna a lista de endereços
    }

    /**
     * Atualiza um registro de endereço no banco de dados.
     * Atualiza os dados do endereço baseado no seu ID.
     *
     * @param endereco O objeto Endereco com os dados atualizados.
     * @throws SQLException se houver um erro ao tentar atualizar.
     */
    public void update(Endereco endereco) throws SQLException {
        // SQL para atualizar um endereço
        String sql = "UPDATE enderecos SET cliente_id = ?, rua = ?, numero = ?, complemento = ?, bairro = ?, cidade = ?, estado = ?, cep = ? WHERE endereco_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Define os valores dos parâmetros do SQL
            stmt.setInt(1, endereco.getClienteId());
            stmt.setString(2, endereco.getRua());
            stmt.setString(3, endereco.getNumero());
            stmt.setString(4, endereco.getComplemento());
            stmt.setString(5, endereco.getBairro());
            stmt.setString(6, endereco.getCidade());
            stmt.setString(7, endereco.getEstado());
            stmt.setString(8, endereco.getCep());
            stmt.setInt(9, endereco.getEnderecoId()); // Define o ID do endereço a ser atualizado
            stmt.executeUpdate(); // Executa a atualização
        }
    }

    /**
     * Exclui um registro de endereço do banco de dados pelo seu ID.
     *
     * @param enderecoId O ID do endereço a ser excluído.
     * @throws SQLException se houver um erro ao tentar excluir.
     */
    public void delete(int enderecoId) throws SQLException {
        // SQL para deletar um endereço pelo ID
        String sql = "DELETE FROM enderecos WHERE endereco_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, enderecoId); // Define o ID do endereço a ser deletado
            stmt.executeUpdate(); // Executa a exclusão
        }
    }

    /**
     * Mapeia os dados do ResultSet para um objeto Endereco.
     *
     * @param rs O ResultSet contendo os dados do endereço.
     * @return O objeto Endereco mapeado.
     * @throws SQLException se houver um erro ao acessar os dados.
     */
    private Endereco mapResultSetToEndereco(ResultSet rs) throws SQLException {
        Endereco endereco = new Endereco(); // Cria uma nova instância de Endereco
        endereco.setEnderecoId(rs.getInt("endereco_id"));
        endereco.setClienteId(rs.getInt("cliente_id"));
        endereco.setRua(rs.getString("rua"));
        endereco.setNumero(rs.getString("numero"));
        endereco.setComplemento(rs.getString("complemento"));
        endereco.setBairro(rs.getString("bairro"));
        endereco.setCidade(rs.getString("cidade"));
        endereco.setEstado(rs.getString("estado"));
        endereco.setCep(rs.getString("cep"));
        return endereco; // Retorna o objeto Endereco mapeado
    }
}
//Comentario Arthur
