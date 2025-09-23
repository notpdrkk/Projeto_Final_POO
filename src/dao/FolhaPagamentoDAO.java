package dao;

import model.FolhaPagamento;
import java.sql.*;

public class FolhaPagamentoDAO {
    public static void salvar(Connection conn, FolhaPagamento folha, int funcionarioId) throws SQLException {
        String sql = "INSERT INTO folha_pagamento (funcionario_id, data_pagamento, desconto_inss, desconto_ir, salario_liquido) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, funcionarioId);
            stmt.setDate(2, Date.valueOf(folha.getDataPagamento()));
            stmt.setDouble(3, folha.getDescontoINSS());
            stmt.setDouble(4, folha.getDescontoIR());
            stmt.setDouble(5, folha.getSalarioLiquido());
            stmt.executeUpdate();
        }
    }
}