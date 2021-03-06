package grupoUsuario;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.dao.GrupoUsuarioDAO;
import model.vo.GrupoUsuarioVO;
import model.vo.NavioVO;
import view.ConstruirDialog;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;

import helpers.Routes;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class CadastroGrupoController implements Initializable {


    @FXML
    private AnchorPane anchorPaneCadastroGrupo;

    @FXML
    private Label labelCodigoGrupo;

    @FXML
    private JFXTextField textFieldDescricao;

    @FXML
    private JFXCheckBox checkCadastrarNavio;

    @FXML
    private JFXCheckBox checkAlterarNavio;

    @FXML
    private JFXCheckBox checkExibirNavio;

    @FXML
    private JFXCheckBox checkExcluirNavio;

    @FXML
    private JFXCheckBox checkCadastrarUsuario;

    @FXML
    private JFXCheckBox checkExibirUsuario;

    @FXML
    private JFXCheckBox checkAlterarUsuario;

    @FXML
    private JFXCheckBox checkExcluirUsuario;

    @FXML
    private JFXCheckBox checkIniciarMovimento;

    @FXML
    private JFXCheckBox checkMonitorarMovimento;

    @FXML
    private JFXCheckBox checkPausarMovimento;

    @FXML
    private JFXCheckBox checkCancelarMovimento;

    @FXML
    private JFXCheckBox checkCadastrarEmbarque;

    @FXML
    private JFXCheckBox checkExibirEmbarque;

    @FXML
    private JFXCheckBox checkAlterarEmbarque;

    @FXML
    private JFXCheckBox checkExcluirEmbarque;

    @FXML
    private JFXButton buttonCancelar;

    @FXML
    private JFXButton buttomCadastrar;

	private final GrupoUsuarioDAO grupoUsuarioDAO = new GrupoUsuarioDAO();
	public static boolean isAlterarGrupo;
	private GrupoUsuarioVO grupoUsuarioAlterar;
	private Stage dialogStage;

	@FXML
	void clickOnCadastrar(ActionEvent event) {

		if (validarEntrada()) {
			if (isAlterarGrupo == false) {
				GrupoUsuarioVO grupoUsuario = new GrupoUsuarioVO();
				grupoUsuario.setCodigoGrupo(Long.valueOf(labelCodigoGrupo.getText()));
				grupoUsuario.setDescricaoGrupo(textFieldDescricao.getText());
				if (checkCadastrarNavio.isSelected()) {
					grupoUsuario.setPermissaoInsertNavio("T");
				} else {
					grupoUsuario.setPermissaoInsertNavio("F");
				}
				if (checkAlterarNavio.isSelected()) {
					grupoUsuario.setPermissaoAlterNavio("T");
				} else {
					grupoUsuario.setPermissaoAlterNavio("F");
				}
				if (checkExibirNavio.isSelected()) {
					grupoUsuario.setPermissaoConsulNavio("T");
				} else {
					grupoUsuario.setPermissaoConsulNavio("F");
				}
				if (checkExcluirNavio.isSelected()) {
					grupoUsuario.setPermissaoDeletNavio("T");
				} else {
					grupoUsuario.setPermissaoDeletNavio("F");
				}
				if (checkCadastrarUsuario.isSelected()) {
					grupoUsuario.setPermissaoInsertUser("T");
				} else {
					grupoUsuario.setPermissaoInsertUser("F");
				}
				if (checkAlterarUsuario.isSelected()) {
					grupoUsuario.setPermissaoAlterUser("T");
				} else {
					grupoUsuario.setPermissaoAlterUser("F");
				}
				if (checkExibirUsuario.isSelected()) {
					grupoUsuario.setPermissaoConsulUser("T");
				} else {
					grupoUsuario.setPermissaoConsulUser("F");
				}
				if (checkExcluirUsuario.isSelected()) {
					grupoUsuario.setPermissaoDeletUser("T");
				} else {
					grupoUsuario.setPermissaoDeletUser("F");
				}
				if (checkIniciarMovimento.isSelected()) {
					grupoUsuario.setPermissaoInsertMovimento("T");
				} else {
					grupoUsuario.setPermissaoInsertMovimento("F");
				}
				if (checkMonitorarMovimento.isSelected()) {
					grupoUsuario.setPermissaoConsulMovimento("T");
				} else {
					grupoUsuario.setPermissaoConsulMovimento("F");
				}
				if (checkPausarMovimento.isSelected()) {
					grupoUsuario.setPermissaoAlterMovimento("T");
				} else {
					grupoUsuario.setPermissaoAlterMovimento("F");
				}
				if (checkCancelarMovimento.isSelected()) {
					grupoUsuario.setPermissaoDeletMovimento("T");
				} else {
					grupoUsuario.setPermissaoDeletMovimento("F");
				}
				if (checkCadastrarEmbarque.isSelected()) {
					grupoUsuario.setPermissaoInsertEmbarque("T");
				} else {
					grupoUsuario.setPermissaoInsertEmbarque("F");
				}
				if (checkAlterarEmbarque.isSelected()) {
					grupoUsuario.setPermissaoAlterEmbarque("T");
				} else {
					grupoUsuario.setPermissaoAlterEmbarque("F");
				}
				if (checkExibirEmbarque.isSelected()) {
					grupoUsuario.setPermissaoConsulEmbarque("T");
				} else {
					grupoUsuario.setPermissaoConsulEmbarque("F");
				}
				if (checkExcluirEmbarque.isSelected()) {
					grupoUsuario.setPermissaoDeletEmbarque("T");
				} else {
					grupoUsuario.setPermissaoDeletEmbarque("F");
				}
				grupoUsuarioDAO.Inserir(grupoUsuario);
				if (grupoUsuario.getDescricaoGrupo()
						.equals(grupoUsuarioDAO.retornaDescricaoGrupoUsuario(grupoUsuario.getDescricaoGrupo()))) {
					ConsultaGrupoUsuarioController.observableListGrupo.addAll(grupoUsuario);
				}
				//dialogStage.close();
				chamarConsultaGrupo();
			} else {
				grupoUsuarioAlterar.setDescricaoGrupo(textFieldDescricao.getText());
				if (checkCadastrarNavio.isSelected()) {
					grupoUsuarioAlterar.setPermissaoInsertNavio("T");
				} else {
					grupoUsuarioAlterar.setPermissaoInsertNavio("F");
				}
				if (checkAlterarNavio.isSelected()) {
					grupoUsuarioAlterar.setPermissaoAlterNavio("T");
				} else {
					grupoUsuarioAlterar.setPermissaoAlterNavio("F");
				}
				if (checkExibirNavio.isSelected()) {
					grupoUsuarioAlterar.setPermissaoConsulNavio("T");
				} else {
					grupoUsuarioAlterar.setPermissaoConsulNavio("F");
				}
				if (checkExcluirNavio.isSelected()) {
					grupoUsuarioAlterar.setPermissaoDeletNavio("T");
				} else {
					grupoUsuarioAlterar.setPermissaoDeletNavio("F");
				}
				if (checkCadastrarUsuario.isSelected()) {
					grupoUsuarioAlterar.setPermissaoInsertUser("T");
				} else {
					grupoUsuarioAlterar.setPermissaoInsertUser("F");
				}
				if (checkAlterarUsuario.isSelected()) {
					grupoUsuarioAlterar.setPermissaoAlterUser("T");
				} else {
					grupoUsuarioAlterar.setPermissaoAlterUser("F");
				}
				if (checkExibirUsuario.isSelected()) {
					grupoUsuarioAlterar.setPermissaoConsulUser("T");
				} else {
					grupoUsuarioAlterar.setPermissaoConsulUser("F");
				}
				if (checkExcluirUsuario.isSelected()) {
					grupoUsuarioAlterar.setPermissaoDeletUser("T");
				} else {
					grupoUsuarioAlterar.setPermissaoDeletUser("F");
				}
				if (checkIniciarMovimento.isSelected()) {
					grupoUsuarioAlterar.setPermissaoInsertMovimento("T");
				} else {
					grupoUsuarioAlterar.setPermissaoInsertMovimento("F");
				}
				if (checkMonitorarMovimento.isSelected()) {
					grupoUsuarioAlterar.setPermissaoConsulMovimento("T");
				} else {
					grupoUsuarioAlterar.setPermissaoConsulMovimento("F");
				}
				if (checkPausarMovimento.isSelected()) {
					grupoUsuarioAlterar.setPermissaoAlterMovimento("T");
				} else {
					grupoUsuarioAlterar.setPermissaoAlterMovimento("F");
				}
				if (checkCancelarMovimento.isSelected()) {
					grupoUsuarioAlterar.setPermissaoDeletMovimento("T");
				} else {
					grupoUsuarioAlterar.setPermissaoDeletMovimento("F");
				}
				if (checkCadastrarEmbarque.isSelected()) {
					grupoUsuarioAlterar.setPermissaoInsertEmbarque("T");
				} else {
					grupoUsuarioAlterar.setPermissaoInsertEmbarque("F");
				}
				if (checkAlterarEmbarque.isSelected()) {
					grupoUsuarioAlterar.setPermissaoAlterEmbarque("T");
				} else {
					grupoUsuarioAlterar.setPermissaoAlterEmbarque("F");
				}
				if (checkExibirEmbarque.isSelected()) {
					grupoUsuarioAlterar.setPermissaoConsulEmbarque("T");
				} else {
					grupoUsuarioAlterar.setPermissaoConsulEmbarque("F");
				}
				if (checkExcluirEmbarque.isSelected()) {
					grupoUsuarioAlterar.setPermissaoDeletEmbarque("T");
				} else {
					grupoUsuarioAlterar.setPermissaoDeletEmbarque("F");
				}
				grupoUsuarioDAO.alterar(grupoUsuarioAlterar);
				ConsultaGrupoUsuarioController.itensEncontrados.set(
						ConsultaGrupoUsuarioController.itensEncontrados.indexOf(grupoUsuarioAlterar),
						grupoUsuarioAlterar);
				//dialogStage.close();
				chamarConsultaGrupo();
			}
		}
	}

	@FXML
	void clickOnCancelar(ActionEvent event) {
		if (confirmouCancelamentoOuFehamento()) {
			chamarConsultaGrupo();

		}
	}

	private boolean validarEntrada() {
		String errorMessage = "";

		if (textFieldDescricao.getText() == null || textFieldDescricao.getText().length() == 0) {

			errorMessage = "Descri��o inv�lida ou nula!\n";
			textFieldDescricao.requestFocus();

		} else if (grupoUsuarioDAO.retornaDescricaoGrupoUsuario(textFieldDescricao.getText())
				.equals(textFieldDescricao.getText())) {

			// Caso n�o tenha este if da erro!
			if (isAlterarGrupo == true) {
				if (!textFieldDescricao.getText().equals(grupoUsuarioAlterar.getDescricaoGrupo())) {
					errorMessage = "Grupo de Usu�rio j� existe!";
				}
			} else {
				errorMessage = "Grupo de Usu�rio j� existe!";
				textFieldDescricao.requestFocus();
			}

		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			ConstruirDialog dialogErro = new ConstruirDialog();
			dialogErro.DialogError("Erro cadastro do Grupo de Usu�rio", errorMessage, 0, "", errorMessage);
			return false;
		}

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		labelCodigoGrupo.setText(Long.toString(grupoUsuarioDAO.verificaUltimoCodigo() + 1));

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

	public void setGrupoUsuarioAlterar(GrupoUsuarioVO grupoUsuario) {
		this.grupoUsuarioAlterar = grupoUsuario;
		labelCodigoGrupo.setText(Long.toString(grupoUsuarioAlterar.getCodigoGrupo()));
		textFieldDescricao.setText(grupoUsuarioAlterar.getDescricaoGrupo());
		//CORRIGIDO
		if (grupoUsuarioAlterar.getPermissaoInsertNavio().equals("T")) {
			checkCadastrarNavio.setSelected(true);
		}
		if (grupoUsuarioAlterar.getPermissaoAlterNavio().equals("T")) {
			checkAlterarNavio.setSelected(true);
		}
		if (grupoUsuarioAlterar.getPermissaoConsulNavio().equals("T")) {
			checkExibirNavio.setSelected(true);
		}
		if (grupoUsuarioAlterar.getPermissaoDeletNavio().equals("T")) {
			checkExcluirNavio.setSelected(true);
		}
		if (grupoUsuarioAlterar.getPermissaoInsertUser().equals("T")) {
			checkCadastrarUsuario.setSelected(true);
		}
		if (grupoUsuarioAlterar.getPermissaoAlterUser().equals("T")) {
			checkAlterarUsuario.setSelected(true);
		}
		if (grupoUsuarioAlterar.getPermissaoConsulUser().equals("T")) {
			checkExibirUsuario.setSelected(true);
		}
		if (grupoUsuarioAlterar.getPermissaoDeletUser().equals("T")) {
			checkExcluirUsuario.setSelected(true);
		}
		if (grupoUsuarioAlterar.getPermissaoInsertMovimento().equals("T")) {
			checkIniciarMovimento.setSelected(true);
		}
		if (grupoUsuarioAlterar.getPermissaoAlterMovimento().equals("T")) {
			checkPausarMovimento.setSelected(true);
		}
		if (grupoUsuarioAlterar.getPermissaoConsulMovimento().equals("T")) {
			checkMonitorarMovimento.setSelected(true);
		}
		if (grupoUsuarioAlterar.getPermissaoDeletMovimento().equals("T")) {
			checkCancelarMovimento.setSelected(true);
		}
		if (grupoUsuarioAlterar.getPermissaoInsertEmbarque().equals("T")) {
			checkCadastrarEmbarque.setSelected(true);
		}
		if (grupoUsuarioAlterar.getPermissaoAlterEmbarque().equals("T")) {
			checkAlterarEmbarque.setSelected(true);
		}
		if (grupoUsuarioAlterar.getPermissaoConsulEmbarque().equals("T")) {
			checkExibirEmbarque.setSelected(true);
		}
		if (grupoUsuarioAlterar.getPermissaoDeletEmbarque().equals("T")) {
			checkExcluirEmbarque.setSelected(true);
		}

		buttomCadastrar.setText("Aplicar");
	}
	
	private void chamarConsultaGrupo() {
		AnchorPane parent = (AnchorPane) anchorPaneCadastroGrupo.getParent();
		parent.getChildren().clear();
		AnchorPane telaGrupoUsuario;
		try {
			telaGrupoUsuario = FXMLLoader.load(getClass().getResource(Routes.GRUPOUSUARIOVIEW));
			parent.getChildren().add((Node) telaGrupoUsuario);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
