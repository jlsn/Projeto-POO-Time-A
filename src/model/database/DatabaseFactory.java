package model.database;

import java.sql.SQLException;
import java.util.Properties;

import view.ConstruirDialog;

public class DatabaseFactory {
	private static Properties props = DatabaseParams.getProp();
	private static String base = props.getProperty("database");

	public static Database getDatabase() {

		if (base.equals("oracle")) {
			try {
				return DatabaseOracle.getDatabase();
			} catch (SQLException e) {
				ConstruirDialog erro = new ConstruirDialog();
				erro.DialogError("Erro de Conex�o", "A conex�o com o banco de dados falhou!", e.getErrorCode(),
						e.getMessage(),
						"Poss�veis Causas:\n" + "Endere�o do Servidor\n" + "Servi�os do Banco de Dados");
			}
		} else if (base.equals("postgresql")) {
			try {
				return DatabasePostgresql.getDatabase();
			} catch (SQLException e) {
				ConstruirDialog erro = new ConstruirDialog();
				erro.DialogError("Erro de Conex�o", "A conex�o com o banco de dados falhou!", e.getErrorCode(),
						e.getMessage(),
						"Poss�veis Causas:\n" + "Endere�o do Servidor\n" + "Servi�os do Banco de Dados");
			}
		}
		ConstruirDialog erro = new ConstruirDialog();
		erro.DialogError("Erro ao Fabricar Base da Dados", "Sistema n�o possui implementa��o para essa base de dados",
				0, "A base: " + base + " N�o existe!",
				"Contate o admnistrador da sua rede\nVerifique o arquivo de configura��o");
		return null;
	}
}
