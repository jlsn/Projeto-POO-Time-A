package fwboarding;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import helpers.Routes;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import javafx.stage.Stage;
import login.LoginController;

public class FwBoarding extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		// Carregar tela Login
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(Routes.LOGINVIEW));
		Parent root = loader.load();

		LoginController controller = loader.getController();
		controller.setStage(stage);

		Scene scene = new Scene(root);
		scene.getStylesheets().add(FwBoarding.class.getResource("/view/styles/styles.css").toExternalForm());
		//stage.initStyle(StageStyle.UTILITY);
		stage.setResizable(false);
		stage.setScene(scene);
		stage.getIcons().add(new Image(FwBoarding.class.getResource("/view/images/Icons/IconNavio.png").toString()));
		stage.setIconified(false);
		stage.show();
	}
	public static void main(String[] args) {

		try {
			launch(args);
		} catch (Exception e) {
			try {
				PrintWriter pw = new PrintWriter(new File("somefilename.txt"));
				e.printStackTrace(pw);
				pw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
