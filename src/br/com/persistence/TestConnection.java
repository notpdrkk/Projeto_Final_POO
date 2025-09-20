package br.com.persistence;

import java.sql.Connection;

public class TestConnection {
	public static void main(String[] args) {
		Connection connection = new ConnectionFactory().getConnection();
	}
}
