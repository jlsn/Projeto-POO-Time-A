package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.dao.NavioDAO;
import model.dao.PaisDAO;
import model.database.Database;
import model.database.DatabaseFactory;
import model.vo.Navio;
import model.vo.NavioObservableList;
import model.vo.Pais;
import view.ConstruirDialog;

public class TelaCadastroNavioController implements Initializable {

	@FXML
	private GridPane gridPane;

	@FXML
	private TextField textFieldCodigo;

	@FXML
	private Label labelErro;
	@FXML
	private TextField textFieldDescricao;

	@FXML
	private Label labelCodigo;

	@FXML
	private Button buttonCadastrar;

	@FXML
	private ComboBox<Pais> comboBoxPaisOrigem;

	@FXML
	private ComboBox<Integer> comboBoxQuantidadePorao;

	@FXML
	private TextField textFieldCapacidadePorao;

	private ObservableList<Pais> observableListPais;

	private final Database database = DatabaseFactory.getDatabase("oracle");
	private final Connection conn = database.conectar();
	private final PaisDAO paisDAO = new PaisDAO();
	private final NavioDAO navioDAO = new NavioDAO();

	// Usado para definir palco e poder utilizar seus m�todos neste controller
	private Stage dialogStage;

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;

	}

	@FXML
	public void clickOnCancelar() {
		ConstruirDialog confirm = new ConstruirDialog();
		// Chama evento do bot�o
		Optional<ButtonType> result = confirm.DialogConfirm("Confirmar Cancelamento",
				"Aten��o! Voc� ir� Cancelar o Cadastro", "N�o ter� volta! :(");

		if (result.get() == ButtonType.OK) {

			// ... Usu�rio clicou ok
			dialogStage.close();
		}
	}

	@FXML
	public void clickOnCadastrar() {

		if (validarEntrada()) {
			Navio navio = new Navio();
			navio.setCodigoNavio(Integer.valueOf(labelCodigo.getText()));
			navio.setDescricaoNavio(textFieldDescricao.getText());
			navio.setPais(comboBoxPaisOrigem.getSelectionModel().getSelectedItem());
			navio.setQtdPorao(comboBoxQuantidadePorao.getSelectionModel().getSelectedItem());
			navio.setCapacidadePorao(Double.valueOf(textFieldCapacidadePorao.getText()));

			try {
				navioDAO.inserir(navio);
				// Atualiza Tela de Consulta
				TelaConsultasController.observableListNavio.add(new NavioObservableList(navio.getCodigoNavio(),
						navio.getDescricaoNavio(), navio.getPais().getNome()));
				// fechar dialog
				dialogStage.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public void desconectarBanco() {

		database.desconectar(conn);
	}

	private boolean validarEntrada() {
		String errorMessage = "";

		if (textFieldDescricao.getText() == null || textFieldDescricao.getText().length() == 0) {

			errorMessage = "Descri��o inv�lida ou nula!\n";
			textFieldDescricao.requestFocus();
		} else if (navioDAO.retornaDescricaoNavio(textFieldDescricao.getText()).equals(textFieldDescricao.getText())) {

			errorMessage = "Navio j� existe!";
			textFieldDescricao.requestFocus();

		} else if (comboBoxPaisOrigem.getSelectionModel().getSelectedItem() == null) {

			errorMessage = "Selecione o pa�s!\n";
			comboBoxPaisOrigem.requestFocus();

		} else if (comboBoxQuantidadePorao.getSelectionModel().getSelectedItem() == null) {

			errorMessage = "Selecione a quantidade de Por�o!\n";
			comboBoxQuantidadePorao.requestFocus();

		} else if (textFieldCapacidadePorao.getText() == null || textFieldCapacidadePorao.getText().length() == 0
				|| Double.valueOf(textFieldCapacidadePorao.getText()) <= 0) {

			errorMessage = "Insira Capacidade !\n";
			textFieldCapacidadePorao.requestFocus();

		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			ConstruirDialog dialogErro = new ConstruirDialog();
			dialogErro.DialogError("Erro cadastro do Navio", errorMessage, 0, "", errorMessage);
			return false;
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		paisDAO.setConnection(conn);
		navioDAO.setConnection(conn);

		// listaPais = listarDescricao();
		labelCodigo.setText(Integer.toString(navioDAO.verificaUltimoCodigo() + 1));
		observableListPais = FXCollections.observableArrayList(paisDAO.listarPais());

		comboBoxPaisOrigem.setItems(observableListPais);
		comboBoxQuantidadePorao.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
	}

}
