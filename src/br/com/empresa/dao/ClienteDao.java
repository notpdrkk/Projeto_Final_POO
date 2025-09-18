package br.com.empresa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.empresta.persistence.ConnectionFactory;


public class ClienteDao {
	private Connection connection; 
	
	public ClienteDao() {
		this.connection = new ConnectionFactory().getConnection();
	}
	
	
	
}	
