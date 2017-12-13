package model.database;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import view.DialogErro;

public class DatabaseOracle implements Database {

	private Connection conn;

	@Override
	public Connection conectar() {

		// Param de conex�o
		String server = "localhost";
		String port = "1521";
		String database = "XE";

		// Param de auth
		String user = "empat";
		String passwd = "teste123";
		String url = "jdbc:oracle:thin:@" + server + ":" + port + ":" + database;
		try {
			// Abrir conex�o com DB
			this.conn = DriverManager.getConnection(url, user, passwd);
			return this.conn;
		} catch (SQLException e) {
			
			DialogErro erro = new DialogErro();		
			erro.DialogError("SQLException", "Erro ao conectar no banco de Dados",e.getMessage(),"Verificar Conex�o com o banco de dados!");
			Logger.getLogger(DatabaseOracle.class.getName()).log(Level.SEVERE, null, e);
			return null;
		}

	}

	@Override
	public void desconectar(Connection conn) {
		try {
			//encerra conex�o
			this.conn.close();
		}catch (SQLException ex) {
			Logger.getLogger(DatabaseOracle.class.getName()).log(Level.SEVERE, null, ex);
		}
		

	}

}
