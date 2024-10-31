package dao;

import entidade.Categoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {
    private Connection connection;

    public CategoriaDAO(Connection connection) {
        this.connection = connection;
    }

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

    public Categoria read(int categoriaId) throws SQLException {
        String sql = "SELECT * FROM categorias WHERE categoria_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, categoriaId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToCategoria(rs);
            }
        }
        return null;
    }

    public List<Categoria> readAll() throws SQLException {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categorias";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                categorias.add(mapResultSetToCategoria(rs));
            }
        }
        return categorias;
    }

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
        Categoria categoria = new Categoria();
        categoria.setCategoriaId(rs.getInt("categoria_id"));
        categoria.setNome(rs.getString("nome"));
        categoria.setDescricao(rs.getString("descricao"));
        return categoria;
    }
}
