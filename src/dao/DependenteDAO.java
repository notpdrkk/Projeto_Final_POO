package dao;

import model.Dependente;
import java.sql.*;

public class DependenteDAO {
    public static void salvar(Connection conn, Dependente dep, int funcionarioId) throws SQLException {
        String sql = "INSERT INTO DEPENDENTE (nome, cpf, data_nascimento, parentesco, funcionario_id) VALUES (?, ?, ?, ?::parentesco_tipo, ?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dep.getNome());
            stmt.setString(2, dep.getCpf());
            stmt.setDate(3, Date.valueOf(dep.getDataNascimento()));
            stmt.setString(4, dep.getParentesco().name());    
            stmt.setInt(5, funcionarioId);
            stmt.executeUpdate();
        }
    }
}