package br.com.persistence;

import java.sql.Connection;
import java.sql.Statement;

public class CriarTabela {
    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();

        try (Connection conn = factory.getConnection();
             Statement stmt = conn.createStatement()) {

            // Criar tipo ENUM no PostgreSQL
            String sqlTipoParentesco = """
                DO $$
                BEGIN
                    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'tipo_parentesco') THEN
                        CREATE TYPE tipo_parentesco AS ENUM ('FILHO','SOBRINHO','OUTROS');
                    END IF;
                END$$;
                """;

            // Tabela funcionario
            String sqlFuncionario = """
                DROP TABLE IF EXISTS funcionario CASCADE;
                CREATE TABLE funcionario (
                    id SERIAL PRIMARY KEY,
                    nome VARCHAR(50) NOT NULL,
                    cpf VARCHAR(11) UNIQUE NOT NULL,
                    data_nascimento DATE NOT NULL,
                    salarioBruto NUMERIC(10,2) NOT NULL,
                    descontoInss NUMERIC(10,2) NOT NULL,
                    descontoIR NUMERIC(10,2) NOT NULL
                );
                """;

            // Tabela parentesco
            String sqlParentesco = """
                DROP TABLE IF EXISTS parentesco CASCADE;
                CREATE TABLE parentesco (
                    id SERIAL PRIMARY KEY,
                    parentesco tipo_parentesco NOT NULL
                );
                """;

            // Tabela dependente
            String sqlDependente = """
                DROP TABLE IF EXISTS dependente CASCADE;
                CREATE TABLE dependente (
                    id SERIAL PRIMARY KEY,
                    nome VARCHAR(50) NOT NULL,
                    cpf VARCHAR(11) UNIQUE NOT NULL,
                    data_nascimento DATE NOT NULL,
                    parentesco_id INTEGER REFERENCES parentesco(id)
                );
                """;

            // Tabela folhaPagamento
            String sqlFolha = """
                DROP TABLE IF EXISTS folhaPagamento CASCADE;
                CREATE TABLE folhaPagamento (
                    id SERIAL PRIMARY KEY,
                    codigo INTEGER NOT NULL,
                    dataPagamento DATE NOT NULL,
                    descontoInss NUMERIC(10,2),
                    descontoIR NUMERIC(10,2),
                    funcionario_id INTEGER NOT NULL REFERENCES funcionario(id)
                );
                """;

            // Executando na ordem correta
            stmt.execute(sqlTipoParentesco);
            stmt.execute(sqlFuncionario);
            stmt.execute(sqlParentesco);
            stmt.execute(sqlDependente);
            stmt.execute(sqlFolha);

            System.out.println("✅ Todas as tabelas foram criadas com sucesso!");

        } catch (Exception e) {
            System.err.println("⚠️ Erro ao criar tabelas: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
