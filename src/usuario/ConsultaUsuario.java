package usuario;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.swing.plaf.synth.SynthSeparatorUI;

import com.jfoenix.controls.JFXButton;

import fwboarding.FwBoarding;
import helpers.Routes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import model.dao.NavioDAO;
import model.dao.UsuarioDAO;
import model.vo.NavioVO;
import model.vo.UsuarioVO;
import navio.CadastroNavioController;
import view.ConstruirDialog;

public class ConsultaUsuario implements Initializable {

	@FXML
	private AnchorPane TelaConsultasAnchorPane;

	@FXML
	private BorderPane AnchorPaneBorderPane;

	@FXML
	private TextField textFieldPesquisar;

	@FXML
	private ButtonBar AnchorPaneButtonBar;

	@FXML
    private JFXButton buttonAdd;

	@FXML
	private TableView<UsuarioVO> TableView;

	@FXML
	private TableColumn<UsuarioVO, String> TableColumnCodigo;

	@FXML
	private TableColumn<UsuarioVO, String> TableColumnNome;

	@FXML
	private TableColumn<UsuarioVO, String> TableColumnGrupo;

	@FXML
	private TableColumn<UsuarioVO, Button> columnButton;

	public static ObservableList<UsuarioVO> observableListUsuario;

	private final UsuarioDAO usuarioDAO = new UsuarioDAO();

	public static ObservableList<UsuarioVO> itensEncontrados;

	private Button btn;

	@FXML
	public void clickOnIncluir() throws IOException {

		CadastroUsuarioController.isAlterarUsuario = false;
		AnchorPane cadastroUsuario = FXMLLoader.load(getClass().getResource(Routes.CADASTROUSUARIOVIEW));
		setNode(cadastroUsuario);
		// Para Atualizar a ObservableList itensEncontrados
		// clickOnPesquisar();
	}

	@FXML
	public void clickOnAlterar() throws IOException {

		int selectedIndex = TableView.getSelectionModel().getSelectedIndex();
		UsuarioVO usuario = TableView.getSelectionModel().getSelectedItem();

		if (selectedIndex >= 0) {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(Routes.CADASTROUSUARIOVIEW));
			CadastroUsuarioController.isAlterarUsuario = true;

			AnchorPane cadastroUsuario = loader.load();
			CadastroUsuarioController controller = loader.getController();
			controller.setUsuarioAlterar(usuario);
			setNode(cadastroUsuario);

		} else {
			// Nada selecionado.
			ConstruirDialog alerta = new ConstruirDialog();
			alerta.dialogAlert("N�o h� sele��o", "Nenhum usu�rio selecionado", "Selecione um usu�rio!");
		}
		// Para Atualizar a ObservableList itensEncontrados
		clickOnPesquisar();
	}

	@FXML
	public void clickOnExcluir() throws Exception {

		int selectedIndex = TableView.getSelectionModel().getSelectedIndex();

		UsuarioVO usuario = TableView.getSelectionModel().getSelectedItem();

		if (selectedIndex >= 0) {
			if (confirmouExcluisaoDoNavio(usuario.getNomeUsuario().toString())) {
				usuarioDAO.deletar(usuario.getCodigoUsuario());
				usuarioDAO.verificaSeUsuarioFoiExcluido(usuario.getCodigoUsuario());
				// TableColumnNavio.getItems().remove(selectedIndex);
				observableListUsuario.remove(usuario);
			}

		} else {
			// Nada selecionado.
			ConstruirDialog alerta = new ConstruirDialog();
			alerta.dialogAlert("N�o h� sele��o", "Nenhum usu�rio selecionado", "Selecione um usu�rio!");
		}
		// Para Atualizar a ObservableList itensEncontrados
		clickOnPesquisar();
	}

	public boolean confirmouExcluisaoDoNavio(String descricaoUsuario) {
		ConstruirDialog confirmar = new ConstruirDialog();
		Optional<ButtonType> result = confirmar.DialogConfirm("Confirmar Exclus�o",
				"O usuario " + descricaoUsuario + " ser� EXCLU�DO", "Confirma a Exclus�o? Pressione OK para concluir!");
		if (result.get() == ButtonType.OK) {
			return true;
		} else {
			return false;
		}
	}

	@FXML
	private void onKeyPressed(KeyEvent event) throws Exception {
		int selectedIndex = TableView.getSelectionModel().getSelectedIndex();
		if (event.getCode().equals(KeyCode.ENTER) && selectedIndex >= 0) {
			clickOnAlterar();
		} else if (event.getCode().isLetterKey() || event.getCode().isWhitespaceKey()
				|| event.getCode().equals(KeyCode.BACK_SPACE)) {
			// System.out.println(event.getCode().getName());
			clickOnPesquisar();
		} else if (event.getCode().equals(KeyCode.DELETE) && selectedIndex >= 0) {
			clickOnExcluir();
		}
	}

	@FXML
	void onMouseClicked(MouseEvent mouseEvent) throws IOException {
		if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
			if (mouseEvent.getClickCount() == 2) {
				clickOnAlterar();
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		TableColumnCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoUsuario"));
		TableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nomeUsuario"));
		TableColumnGrupo.setCellValueFactory(new PropertyValueFactory<>("grupoUsuario"));
		columnButton.setCellValueFactory(new PropertyValueFactory<>("buttonBar"));

		observableListUsuario = FXCollections.observableArrayList(usuarioDAO.listar());

		TableView.setItems(observableListUsuario);
		clickOnPesquisar();
	}

	@FXML
	private void clickOnPesquisar() {

		itensEncontrados = FXCollections.observableArrayList();
		for (UsuarioVO itens : observableListUsuario) {
			itens.setButtonBar(new HBox());
			HBox btnBar = itens.getButtonBar();
			btnBar.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
			btnBar.getStylesheets().add(getClass().getResource("/view/styles/styles.css").toExternalForm());

			Image excluirIcon = new Image(getClass().getResourceAsStream("/view/images/Icons/deletar.png"));
			// button excluir
			JFXButton buttonExcluir = new JFXButton();
			buttonExcluir.getStyleClass().add("buttonTable");
			buttonExcluir.setGraphic(new ImageView(excluirIcon));
			buttonExcluir.setOnAction(event -> {
				try {
					TableView.getSelectionModel().select(itens);
					clickOnExcluir();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});

			Image editarIcon = new Image(getClass().getResourceAsStream("/view/images/Icons/editarIcon.png"));
			// button editar
			JFXButton buttonEdit = new JFXButton();
			buttonEdit.setGraphic(new ImageView(editarIcon));
			buttonEdit.setOnAction(event -> {
				try {
					TableView.getSelectionModel().select(itens);
					clickOnAlterar();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			btnBar.getChildren().addAll(buttonExcluir, buttonEdit);
			if (itens.getNomeUsuario().toLowerCase().contains(textFieldPesquisar.getText().toLowerCase())) {
				itensEncontrados.add(itens);
			}
		}
		TableView.setItems(itensEncontrados);
	}

	public void setNode(Node node) {
		TelaConsultasAnchorPane.getChildren().clear();
		TelaConsultasAnchorPane.getChildren().add((Node) node);
	}

}
