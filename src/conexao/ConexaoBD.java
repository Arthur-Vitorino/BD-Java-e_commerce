package conexao;
//A classe ConexaoBD é responsável por gerenciar a conexão com o banco de dados.
//Utiliza o driver JDBC para estabelecer uma conexão com o banco MySQL.
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {
// URL de conexão ao banco de dados MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/e_commerce";
    private static final String USER = "root";  // Nome de usuário para acessar o banco de dados
    private static final String PASSWORD = "catolica";  // Senha para acessar o banco de dados

    public static Connection getConnection() throws SQLException {
        try { // Registra o driver JDBC do MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD); // Retorna a conexão com o banco de dados
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC não encontrado", e); // Lança uma SQLException se o driver não for encontrado
        }
    }
}
// Comentario feito por Anna Júlia 
