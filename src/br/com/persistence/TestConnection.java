package br.com.persistence;

import java.sql.Connection;

public class TestConnection {
	public static void main(String[] args) {
		ConnectionFactory factory = new ConnectionFactory();
		Connection conn = factory.getConnection();

		if (conn != null) {
			System.out.println("🚀 Teste concluído: conexão está OK!");
			try {
				conn.close();
				System.out.println("🔒 Conexão fechada com sucesso.");
			} catch (Exception e) {
				System.err.println("⚠️ Erro ao fechar a conexão: " + e.getMessage());
			}
		} else {
			System.out.println("❌ Teste falhou: não foi possível conectar.");
		}
	}

}
