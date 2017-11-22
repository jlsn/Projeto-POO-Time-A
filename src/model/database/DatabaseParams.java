package model.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import view.ConstruirDialog;
/*
 * Respons�vel pela leitura dos parametros da base de dados
 */
public class DatabaseParams {

	public static Properties getProp() {
		try {

			Properties props = new Properties();
			FileInputStream file = new FileInputStream("resourcess/config/database.properties");

			props.load(file);

			return props;
		} catch (IOException e) {
			ConstruirDialog dialogError = new ConstruirDialog();
			dialogError.DialogError(
					"Database Conection", "Erro: verifique o arquivos de configura��o!",
					0, e.getMessage(),
					 "Arquivo de configura��o da Database: "+e.getMessage());
			return null;
		}
		

	}
}
