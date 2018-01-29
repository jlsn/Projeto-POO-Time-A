package safra;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import model.vo.SafraVO;

public class CadastroSafraController implements Initializable {

	@FXML
	private JFXTextField fieldTextAnoSafra;

	@FXML
	private JFXButton buttonConfirmar;
	

	private Stage dialogStage;

	static boolean isAlterarSafra = false;

	public static String anoSafra;
	
	private final SafraDAO safraDAO = new SafraDAO();
	
	private SafraVO safraAlterar;
	
	private ObservableList<SafraVO> observableListSafra;

	@FXML
	void clickOnConfirm() {
		if (validarEntrada()) {
			if (isAlterarSafra == false) { 
			SafraVO safra = new SafraVO();
			safra.setAnoSafra(fieldTextAnoSafra.getText());
			safraDAO.inserir(safra);
			if (safra.getAnoSafra().equals(safraDAO.retornaAnoSafra(safra.getAnoSafra()))) {
				SafraViewController.observableListSafra.addAll(safra);
			}
			dialogStage.close();
		} else {
			safraAlterar.setAnoSafra(fieldTextAnoSafra.getText());
			safraDAO.alterar(safraAlterar);
			SafraViewController.itensEncontrados.set(ConsultaUsuario.itensEncontrados.indexOf(safraAlterar),
					safraAlterar);
			dialogStage.close();
		}
		}
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
		dialogStage.setOnCloseRequest(event -> {
			if (isAlterarSafra) {
				dialogStage.close();
			} else {
				event.consume();
			}

		});
	}
	
	public String getAnoSafra() {
		return anoSafra;
	}
	
	private boolean validarEntrada() {
		String errorMessage = "";
		if (fieldTextAnoSafra.getText() == null || fieldTextAnoSafra.getText().length() == 0) {

			errorMessage = "Ano de Safra invalido ou nulo!\n";
			fieldTextAnoSafra.requestFocus();
		}
		if (errorMessage.length() == 0) {
			anoSafra = fieldTextAnoSafra.getText();	
			return true;
		} else {
			errorMessage = "Ano de Safra invalido !\n";
			return false;
		}

	}
	
	public void setSafraAlterar(SafraVO safra) {
		this.safraAlterar = safra;
		fieldTextAnoSafra.setText(safraAlterar.getAnoSafra());
		buttonConfirmar.setText("Aplicar");
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}


	public void setSafraAlterar(SafraVO safra) {
		// TODO Auto-generated method stub
		
	}

}