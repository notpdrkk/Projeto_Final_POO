package service;

import java.sql.*;
import java.util.List;
import dao.DependenteDAO;
import dao.FolhaPagamentoDAO;
import dao.FuncionarioDAO;
import model.Dependente;
import model.Funcionario;

public class DatabaseService {
    private static final String URL = "jdbc:postgresql://localhost:5432/folha_pagamento";
    private static final String USER = "postgres";
    //senha do postgres
    private static final String PASSWORD = "123456"; 

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void criarBancoDados() {
        System.out.println("Lembre-se de criar o banco e as tabelas usando o script 'database.sql' no pgAdmin.");
    }

    public static void salvarFuncionarios(List<Funcionario> funcionarios) throws SQLException {
        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            for (Funcionario func : funcionarios) {
                int funcId = FuncionarioDAO.salvar(conn, func);
                for (Dependente dep : func.getDependentes()) {
                    DependenteDAO.salvar(conn, dep, funcId);
                }
            }
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) conn.close();
        }
    }

    public static void gerarFolhaPagamento(List<Funcionario> funcionarios) throws SQLException {
        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            CalculoService calcService = new CalculadoraSalario();
            
            for (Funcionario func : funcionarios) {
                Integer funcId = FuncionarioDAO.buscarIdPorCpf(conn, func.getCpf());
                if (funcId == null) {
                    System.err.println("Atenção: Funcionário com CPF " + func.getCpf() + " não encontrado para gerar folha de pagamento.");
                    continue;
                }

                model.FolhaPagamento folha = new model.FolhaPagamento(func, java.time.LocalDate.now());
                double liquido = calcService.calcularSalarioLiquido(func);
                folha.setDescontoINSS(func.getDescontoINSS());
                folha.setDescontoIR(func.getDescontoIR());
                folha.setSalarioLiquido(liquido);

                FolhaPagamentoDAO.salvar(conn, folha, funcId);
            }
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) conn.close();
        }
    }
}