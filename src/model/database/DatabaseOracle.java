package model.database;

import java.sql.*;
import java.util.Properties;
import view.ConstruirDialog;

public class DatabaseOracle implements Database {

	private static DatabaseOracle instance;

	private static Connection conn;

	private Properties props = DatabaseParams.getProp();

	private String host = props.getProperty("database.oracle.host");
	private String port = props.getProperty("database.oracle.port");
	private String sid = props.getProperty("database.oracle.sid");

	private String user = props.getProperty("database.oracle.user");
	private String password = props.getProperty("database.oracle.password");

	public DatabaseOracle() {

		String url = "jdbc:oracle:thin:@" + host + ":" + port + ":" + sid;

		try {
			// Abrir conex�o com DB
			DatabaseOracle.conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {

			ConstruirDialog erro = new ConstruirDialog();
			erro.DialogError("Erro de Conex�o", "A conex�o com o banco de dados falhou!", e.getErrorCode(),
					e.getMessage(), "Poss�veis Causas:\n" + "Endere�o do Servidor\n" + "Servi�os do Banco de Dados");
		}

	}

	public synchronized static DatabaseOracle getDatabase() throws SQLException {
		if (instance == null) {
			instance = new DatabaseOracle();
		}else if(conn.isClosed()) {
			instance = new DatabaseOracle();
		}
		return instance;

	}

	@Override
	public Connection getConection() {
		return DatabaseOracle.conn;
	}

	@Override
	public void desconectar(Connection conn) {
		try {
			// encerra conex�o
			DatabaseOracle.conn.close();
		} catch (SQLException e) {
			ConstruirDialog erro = new ConstruirDialog();
			erro.DialogError("Erro ao desconectar!","Falha ao tentar desconectar do banco",e.getErrorCode(),e.getMessage(),e.getMessage());
		}

	}

}
