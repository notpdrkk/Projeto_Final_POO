package br.com.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "12345";

    public Connection getConnection() {
        System.out.println("Conectando no banco de dados.....");
        try {
            // Força carregamento do driver (opcional em JDBC 4+, mas garante compatibilidade)
            Class.forName("org.postgresql.Driver");

            Connection connection = DriverManager.getConnection(URL, USUARIO, SENHA);
            System.out.println("✅ Conectado com sucesso!");
            return connection;

        } catch (ClassNotFoundException e) {
            System.err.println("❌ Driver do PostgreSQL não encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("❌ Erro ao conectar: " + e.getMessage());
        }
        return null;
    }
}
