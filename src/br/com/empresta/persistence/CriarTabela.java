package br.com.empresta.persistence;

import java.sql.DriverManager;

public class CriarTabela {
	public static void main(String[] args) {
		String sqlFuncionario = """
				DROP TABLE IF EXISTS funcionario;
				CREATE TABLE funcionario (
				id SERIAL PRIMARY KEY,
				nome VARCHAR (50) NOT NULL,
				cpf VARCHAR (11) UNIQUE NOT NULL,
				data_nascimento DATE NOT NULL,
				salarioBruto NUMERIC (10, 2) NOT NULL,
				descontoInss NUMERIC (10,2) NOT NULL,
				descontoIR NUMERIC (10,2) NOT NULL
				);
				""";
		String sqlDependente = """
				DROP TABLE IF EXISTS dependente;
				CREATE TABLE dependente (
				id SERIAL PRIMARY KEY,
				nome VARCHAR (50) NOT NULL,
				cpf VARCHAR (11) UNIQUE NOT NULL,
				data_nascimento DATE NOT NULL
				FOREIGN KEY parentesco REFERENCES parentesco(id)
				);
				""";
		String sqlParentesco = """
				DROP TABLE IF EXISTS parentesco;
				CREATE TABLE parentesco (
				id SERIAL PRIMARY KEY,
				parentesco enum ('FILHO','SOBRINHO','OUTROS')
				);				
				""";
		
		String sqlFolha = """
				DROP TABLE IF EXISTS folhaPagamento;
				CREATE TABLE folhaPagamento (
				id SERIAL PRIMARY KEY,
				codigo INTEGER NOT NULL,
				dataPagamento DATE NOT NULL,
				descontoInss NUMERIC (10,2),
				descontoIR NUMERIC (10,2), 
				FOREIGN (funcionario_id) REFERENCES funcionario(id)				
				);
				""";
		
		;
	}
}