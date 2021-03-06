package grupoUsuario;

import java.io.IOException;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

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
import model.vo.GrupoUsuarioVO;
import model.vo.NavioVO;
import usuario.CadastroUsuarioController;
import view.ConstruirDialog;
import model.dao.GrupoUsuarioDAO;

public class ConsultaGrupoUsuarioController implements Initializable {

	@FXML
	private AnchorPane anchorPaneGrupoUsuario;
	
    @FXML
    private JFXButton buttonAdd;

	@FXML
	private TableView<GrupoUsuarioVO> TableGrupoUsuario;

	@FXML
	private TableColumn<GrupoUsuarioVO, String> TableColumnCodigoGrupo;

	@FXML
	private TableColumn<GrupoUsuarioVO, String> TableColumnDescricaoGrupo;
	
    @FXML
    private TableColumn<GrupoUsuarioVO, Button> columnButton;

	@FXML
	private TextField textFieldPesquisar;

	@FXML
	private Button ButtonPesquisar;

	public static ObservableList<GrupoUsuarioVO> observableListGrupo;
	private final GrupoUsuarioDAO grupoUsuarioDAO = new GrupoUsuarioDAO();
	static ObservableList<GrupoUsuarioVO> itensEncontrados;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		carregarTableViewGrupo();

	}

	public void carregarTableViewGrupo() {
		TableColumnCodigoGrupo.setCellValueFactory(new PropertyValueFactory<>("codigoGrupo"));
		TableColumnDescricaoGrupo.setCellValueFactory(new PropertyValueFactory<>("descricaoGrupo"));
		columnButton.setCellValueFactory(new PropertyValueFactory<>("buttonBar"));

		observableListGrupo = FXCollections.observableArrayList(grupoUsuarioDAO.listar());
		TableGrupoUsuario.setItems(observableListGrupo);
		clickOnPesquisar();

	}

	@FXML
	public void clickOnAlterar() throws IOException {

		int selectedIndex = TableGrupoUsuario.getSelectionModel().getSelectedIndex();
		GrupoUsuarioVO grupoUsuario = TableGrupoUsuario.getSelectionModel().getSelectedItem();

		if (selectedIndex >= 0) {
			CadastroGrupoController.isAlterarGrupo = true;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(Routes.CADASTROGRUPOUSUARIOVIEW));
			
			AnchorPane cadastroGrupoUsuario = loader.load();
			CadastroGrupoController controller = loader.getController();
			controller.setGrupoUsuarioAlterar(grupoUsuario);
			setNode(cadastroGrupoUsuario);

		} else {
			// Nada selecionado.
			ConstruirDialog alerta = new ConstruirDialog();
			alerta.dialogAlert("N�o h� sele��o", "Nenhum Grupo selecionado", "Selecione um Grupo!");
		}
		// Para Atualizar a ObservableList itensEncontrados
		clickOnPesquisar();
	}

	@FXML
	public void clickOnExcluir() throws Exception {

		int selectedIndex = TableGrupoUsuario.getSelectionModel().getSelectedIndex();
		GrupoUsuarioVO grupoUsuario = (GrupoUsuarioVO) TableGrupoUsuario.getSelectionModel().getSelectedItem();

		if (selectedIndex >= 0) {
			if (confirmouExcluisaoDoGrupo(grupoUsuario.getDescricaoGrupo().toString())) {
				grupoUsuarioDAO.deletar(grupoUsuario.getCodigoGrupo());
				grupoUsuarioDAO.verificarSeGrupoFoiExcluido(grupoUsuario.getCodigoGrupo());
				// TableColumnNavio.getItems().remove(selectedIndex);
				observableListGrupo.remove(grupoUsuario);
			}

		} else {
			// Nada selecionado.
			ConstruirDialog alerta = new ConstruirDialog();
			alerta.dialogAlert("N�o h� sele��o", "Nenhum Grupo selecionado", "Selecione um Grupo!");
		}
		// Para Atualizar a ObservableList itensEncontrados
		clickOnPesquisar();
	}

	@FXML
	void clickOnIncluir() throws IOException {

		CadastroGrupoController.isAlterarGrupo = false;
		AnchorPane cadastroGrupoUsuario = FXMLLoader.load(getClass().getResource(Routes.CADASTROGRUPOUSUARIOVIEW));
		setNode(cadastroGrupoUsuario);
		// Para Atualizar a ObservableList itensEncontrados
		//clickOnPesquisar();
	}

	@FXML
	void clickOnPesquisar() {
		itensEncontrados = FXCollections.observableArrayList();
		for (GrupoUsuarioVO itens : observableListGrupo) {
			itens.setButtonBar(new ButtonBar());
			ButtonBar btnBar = itens.getButtonBar();
			btnBar.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
			btnBar.getStylesheets().add(getClass().getResource("/view/styles/styles.css").toExternalForm());
			
			Image excluirIcon = new Image(getClass().getResourceAsStream("/view/images/Icons/deletar.png"));
			//button excluir
			JFXButton buttonExcluir = new JFXButton();
			buttonExcluir.getStyleClass().add("buttonTable");
			buttonExcluir.setGraphic(new ImageView(excluirIcon));
			buttonExcluir.setOnAction(event -> {
				try {
					TableGrupoUsuario.getSelectionModel().select(itens);
					clickOnExcluir();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});

			Image editarIcon = new Image(getClass().getResourceAsStream("/view/images/Icons/editarIcon.png"));
			//button editar
			JFXButton buttonEdit = new JFXButton();
			buttonEdit.setGraphic(new ImageView(editarIcon));
			buttonEdit.setOnAction(event -> {
				try {
					TableGrupoUsuario.getSelectionModel().select(itens);
					clickOnAlterar();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			btnBar.getButtons().addAll(buttonExcluir, buttonEdit);
			if (itens.getDescricaoGrupo().toLowerCase().contains(textFieldPesquisar.getText().toLowerCase())) {
				itensEncontrados.add(itens);
			}
		}
		TableGrupoUsuario.setItems(itensEncontrados);
	}
	
	public boolean confirmouExcluisaoDoGrupo(String descricaoGrupo) {
		ConstruirDialog confirmar = new ConstruirDialog();
		Optional<ButtonType> result = confirmar.DialogConfirm("Confirmar Exclus�o",
				"O Grupo " + descricaoGrupo + " ser� EXCLU�DO", "Confirma a Exclus�o? Pressione OK para concluir!");
		if (result.get() == ButtonType.OK) {
			return true;
		} else {
			return false;
		}
	}
	
	@FXML
	void onKeyPressed(KeyEvent event) throws Exception {
		
		int selectedIndex = TableGrupoUsuario.getSelectionModel().getSelectedIndex();
		if (event.getCode().equals(KeyCode.ENTER) && selectedIndex >= 0) {
			clickOnAlterar();
		}
		else if(event.getCode().isLetterKey() || event.getCode().isWhitespaceKey() || event.getCode().equals(KeyCode.BACK_SPACE) ) {
			
			clickOnPesquisar();
		}
		if (event.getCode().equals(KeyCode.DELETE) && selectedIndex >= 0) {
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
    
    public void setNode(Node node) {
    	anchorPaneGrupoUsuario.getChildren().clear();
    	anchorPaneGrupoUsuario.getChildren().add((Node) node);
    }
}
