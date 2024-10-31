package dao;

import entidade.Categoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * A classe CategoriaDAO é responsável por realizar operações de acesso ao banco de dados
 * relacionadas à entidade Categoria. Permite criar, ler, atualizar e excluir registros
 * de categorias de produtos.
 */
public class CategoriaDAO {
    private Connection connection; //Construtor da classe CategoriaDAO

    public CategoriaDAO(Connection connection) {
        this.connection = connection;
    }
/**
     * Cria um novo registro de categoria no banco de dados.
     * Insere uma nova categoria com nome e descrição.
     *
     * @throws SQLException se houver um erro ao tentar inserir.
     */
    public void create(Categoria categoria) throws SQLException {
        String sql = "INSERT INTO categorias (nome, descricao) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, categoria.getNome());
            stmt.setString(2, categoria.getDescricao());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    categoria.setCategoriaId(generatedKeys.getInt(1));
                }
            }
        }
    }
//Lê um registro de categoria do banco de dados pelo seu ID.
    public Categoria read(int categoriaId) throws SQLException {
        String sql = "SELECT * FROM categorias WHERE categoria_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, categoriaId);  // Define o ID da categoria a ser buscada
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToCategoria(rs); // Mapeia o ResultSet para um objeto Categoria
            }
        }
        return null;
    }
//Lê todos os registros de categorias do banco de dados.
    public List<Categoria> readAll() throws SQLException {
        List<Categoria> categorias = new ArrayList<>(); // Lista para armazenar todas as categorias
        String sql = "SELECT * FROM categorias";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                // Adiciona cada categoria mapeada à lista
                categorias.add(mapResultSetToCategoria(rs));
            }
        }
        return categorias;
    }
//Atualiza um registro de categoria no banco de dados.
     // Atualiza os dados da categoria baseado no seu ID.
    public void update(Categoria categoria) throws SQLException {
        String sql = "UPDATE categorias SET nome = ?, descricao = ? WHERE categoria_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, categoria.getNome());
            stmt.setString(2, categoria.getDescricao());
            stmt.setInt(3, categoria.getCategoriaId());
            stmt.executeUpdate();
        }
    }

    public void delete(int categoriaId) throws SQLException {
        String sql = "DELETE FROM categorias WHERE categoria_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, categoriaId);
            stmt.executeUpdate();
        }
    }

    private Categoria mapResultSetToCategoria(ResultSet rs) throws SQLException {
        Categoria categoria = new Categoria(); // Mapeia os dados do ResultSet para o objeto Categoria
        categoria.setCategoriaId(rs.getInt("categoria_id"));
        categoria.setNome(rs.getString("nome"));
        categoria.setDescricao(rs.getString("descricao"));
        return categoria;
    }
}
//Comentario feito por Anna Júlia
