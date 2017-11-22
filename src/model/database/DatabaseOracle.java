package model.database;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import view.ConstruirDialog;

public class DatabaseOracle implements Database {

	private Connection conn;

	@Override
	public Connection conectar() {
		
		Properties props = DatabaseParams.getProp();
		
		String host = props.getProperty("database.oracle.host");
		String port = props.getProperty("database.oracle.port");
		String sid = props.getProperty("database.oracle.sid");
		
		String user = props.getProperty("database.oracle.user");
		String password = props.getProperty("database.oracle.password");
		
		

		String url = "jdbc:oracle:thin:@" + host + ":" + port + ":" + sid;
		
		try {
			// Abrir conex�o com DB
			this.conn = DriverManager.getConnection(url, user, password);
			return this.conn;
		} catch (SQLException e) {
			
			ConstruirDialog erro = new ConstruirDialog();		
			erro.DialogError("Erro de Conex�o", "A conex�o com o banco de dados falhou!",e.getErrorCode(),e.getMessage(),"Poss�veis Causas:\n"
					+ "Endere�o do Servidor\n"
					+ "Servi�os do Banco de Dados");
			Logger.getLogger(DatabaseOracle.class.getName()).log(Level.SEVERE, null, e);
			return null;
		}

	}

	@Override
	public void desconectar(Connection conn) {
		try {
			// encerra conex�o
			this.conn.close();
		} catch (SQLException ex) {
			Logger.getLogger(DatabaseOracle.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

}
