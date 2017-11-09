package model.database;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import view.DialogAlerta;

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

		try {

			String url = "jdbc:oracle:thin:@" + server + ":" + port + ":" + database;

			// Abrir conex�o com DB
			this.conn = DriverManager.getConnection(url, user, passwd);
			return this.conn;
		} catch (SQLException ex) {
			DialogAlerta Error = new DialogAlerta();
			Error.DialogError("Erro ", getClass().getName(), "teste");
			String msg = "Erro ao conectar no Banco";
			Logger.getLogger(DatabaseOracle.class.getName()).log(Level.SEVERE, msg, ex);
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
