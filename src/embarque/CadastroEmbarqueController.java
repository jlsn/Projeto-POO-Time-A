package embarque;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import helpers.Routes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.dao.EmbarqueDAO;
import model.dao.PaisDAO;
import model.vo.EmbarqueVO;
import model.vo.NavioVO;
import model.vo.PaisVO;
import navio.ConsultasNavioController;
import view.ConstruirDialog;

public class CadastroEmbarqueController implements Initializable {

	@FXML
	private AnchorPane anchorPaneCadastroEmbarque;

	@FXML
	private Label labelCadastrarEmbarque;

	@FXML
	private Label labelCodigoEmbarque;

	@FXML
	private JFXTextField textFieldCodigoNavio;

	@FXML
	private JFXComboBox<PaisVO> comboBoxPaisDestino;

	@FXML
	private JFXTextField textFieldQuantidadeAcucar;

	@FXML
	private JFXButton buttonGravar;

	@FXML
	private JFXButton buttonCancelar;

	private ObservableList<PaisVO> observableListPais;
	private final EmbarqueDAO embarqueDAO = new EmbarqueDAO();
	private final PaisDAO paisDAO = new PaisDAO();
	private EmbarqueVO embarqueAlterar;
	public static boolean isAlterarEmbarque;
	private Stage dialogStage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		labelCodigoEmbarque.setText(Integer.toString(embarqueDAO.verificaUltimoCodigo() + 1));
		comboBoxPaisDestino.setItems(observableListPais);
	}

	@FXML
	public void clickOnCancelar() {
		if (confirmouCancelamentoOuFehamento()) {
			chamarConsultaEmbarque();

		}
	}

	private void chamarConsultaEmbarque() {
		AnchorPane parent = (AnchorPane) anchorPaneCadastroEmbarque.getParent();
		parent.getChildren().clear();
		AnchorPane telaEmbarque;
		try {
			telaEmbarque = FXMLLoader.load(getClass().getResource(Routes.EMBARQUEVIEW));
			parent.getChildren().add((Node) telaEmbarque);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void clickOnGravar() {

		if (validarEntrada()) {
			if (isAlterarEmbarque == false) {
				EmbarqueVO embarque = new EmbarqueVO();
				embarque.setCodigoEmbarque(Integer.valueOf(labelCodigoEmbarque.getText()));
				embarque.setCodigoNavio(Integer.valueOf(textFieldCodigoNavio.getText()));
				embarque.setPaisDestino(comboBoxPaisDestino.getSelectionModel().getSelectedItem());
				embarque.setQuantidadeDeAcucar(Long.valueOf(textFieldQuantidadeAcucar.getText()));

				embarqueDAO.Inserir(embarque);
				// Atualiza Tela de Consulta
				// olhar esse if
				// if
				// (embarque.getCodigoNavio().equals(embarqueDAO.retornaCodigoEmbarque(embarque.getCodigoNavio())))
				// {
				TelaEmbarqueController.observableListEmbarque.addAll(embarque);
				// }
				// fechar dialog
				chamarConsultaEmbarque();
			} else {

				embarqueAlterar.setCodigoEmbarque(Integer.valueOf(labelCodigoEmbarque.getText()));
				embarqueAlterar.setCodigoNavio(Integer.valueOf(textFieldCodigoNavio.getText()));
				embarqueAlterar.setPaisDestino(comboBoxPaisDestino.getSelectionModel().getSelectedItem());
				embarqueAlterar.setQuantidadeDeAcucar(Long.valueOf(textFieldQuantidadeAcucar.getText()));
				embarqueDAO.alterar(embarqueAlterar);
				TelaEmbarqueController.itensEncontrados
						.set(TelaEmbarqueController.itensEncontrados.indexOf(embarqueAlterar), embarqueAlterar);
				chamarConsultaEmbarque();
			}
		}
	}

	private boolean validarEntrada() {
		String errorMessage = "";

		if (textFieldCodigoNavio.getText() == null || textFieldCodigoNavio.getText().length() == 0) {

			errorMessage = "Codigo inv�lido ou nulo!\n";
			textFieldCodigoNavio.requestFocus();
		} else if (comboBoxPaisDestino.getSelectionModel().getSelectedItem() == null) {

			errorMessage = "Selecione o pa�s!\n";
			comboBoxPaisDestino.requestFocus();

		} else if (textFieldQuantidadeAcucar.getText() == null || textFieldQuantidadeAcucar.getText().length() == 0
				|| Double.valueOf(textFieldQuantidadeAcucar.getText()) <= 0) {

			errorMessage = "Insira Capacidade !\n";
			textFieldQuantidadeAcucar.requestFocus();
			//olhar esse else if
		} else if (embarqueDAO.retornaCodigoEmbarque(Long.valueOf(textFieldCodigoNavio.getText()).equals(textFieldCodigoNavio.getText()))) {

			// Caso n�o tenha este if da erro!
			if (isAlterarEmbarque == true) {
				if (!textFieldCodigoNavio.getText().equals(embarqueAlterar.getCodigoEmbarque())) {
					errorMessage = "Embarque j� existe!";
				}
			} else {
				errorMessage = "Embarque j� existe!";
				textFieldCodigoNavio.requestFocus();
			}

		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			ConstruirDialog dialogErro = new ConstruirDialog();
			dialogErro.DialogError("Erro cadastro do Embarque", errorMessage, 0, "", errorMessage);
			return false;
		}

	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
		dialogStage.setOnCloseRequest(event -> {
			if (confirmouCancelamentoOuFehamento()) {
				// ... Usu�rio clicou ok
				dialogStage.close();
			} else {
				event.consume();
			}
		});
	}
	
	public boolean confirmouCancelamentoOuFehamento() {
		ConstruirDialog confirmar = new ConstruirDialog();
		Optional<ButtonType> result = confirmar.DialogConfirm("Confirmar Cancelamento",
				"Aten��o, se continuar seus dados ser�o perdidos!", "Deseja cancelar?");
		if (result.get() == ButtonType.OK) {
			return true;
		} else {
			return false;
		}
	}
	
	public void setEmbarqueAlterar(EmbarqueVO embarque) {
		this.embarqueAlterar = embarque;

		labelCodigoEmbarque.setText(Long.toString(embarque.getCodigoNavio()));
		textFieldCodigoNavio.setText(Long.toString(embarque.getCodigoNavio()));
		//consertar ComboBox
		comboBoxPaisDestino.getSelectionModel().select(embarque.getPaisDestino());
		//comboBoxQuantidadePorao.getSelectionModel().select((Integer) navioAlterar.getQtdPorao());
		textFieldQuantidadeAcucar.setText(Double.toString(embarque.getQuantidadeDeAcucar()));
		buttonGravar.setText("Aplicar");
	}
}
