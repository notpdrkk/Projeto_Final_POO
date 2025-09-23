package dao;

import model.Funcionario;
import java.sql.*;

public class FuncionarioDAO {
    public static int salvar(Connection conn, Funcionario func) throws SQLException {
        String sql = "INSERT INTO FUNCIONARIO (nome, cpf, data_nascimento, salario_bruto) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, func.getNome());
            stmt.setString(2, func.getCpf());
            stmt.setDate(3, Date.valueOf(func.getDataNascimento()));
            stmt.setDouble(4, func.getSalarioBruto());
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new SQLException("Falha ao salvar funcionário, nenhum ID obtido.");
    }

    public static Integer buscarIdPorCpf(Connection conn, String cpf) throws SQLException {
        String sql = "SELECT id FROM funcionario WHERE cpf = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cpf);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        return null;
    }
}