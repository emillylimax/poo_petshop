package visao;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dominio.*;
import persistencia.*;

public class FXMLCadastroController implements Initializable{
	@FXML
    private Button btnSalvarCadastro, btnCancCadastro;
 	@FXML
    private AnchorPane anchorPaneGeral, anchorPaneCampos;
    @FXML
    private PasswordField campoSenhaCadastro;
    @FXML
    private TextField campoCpfCadastro, campoNomeCadastro, campoEndCadastro, campoTelCadastro, campoEmailCadastro;
    
    private ClientesDAO clientesDAO=new ClientesDAO();
    private FXMLLoginController loginController;
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
    	Platform.runLater(() -> campoCpfCadastro.requestFocus());
   	 	btnSalvarCadastro.setDisable(true);

   	 	btnSalvarCadastro.disableProperty().bind(
   			 			campoCpfCadastro.textProperty().isEmpty()
                     .or(campoSenhaCadastro.textProperty().isEmpty())
                     .or(campoNomeCadastro.textProperty().isEmpty())
                     .or(campoEndCadastro.textProperty().isEmpty())
                     .or(campoTelCadastro.textProperty().isEmpty())
                     .or(campoEmailCadastro.textProperty().isEmpty())
                     
   	 	);
   	 	
   	 	btnSalvarCadastro.setOnAction(event -> salvarCadastro(event));
   	 	btnCancCadastro.setOnAction(event -> {
   	 		Stage cadastroStage = (Stage) btnCancCadastro.getScene().getWindow();
   	 		cadastroStage.close();
   	 	});
		
	}
	
	public void setLoginController(FXMLLoginController loginController) {
	    this.loginController = loginController;
	}
    
    @FXML
    private void salvarCadastro(ActionEvent event) {
        String cpf = campoCpfCadastro.getText();
        Clientes clienteExistente = clientesDAO.buscar(cpf);

        if (clienteExistente != null) {
            exibirMensagem("Cliente já cadastrado", "O CPF informado já possui uma conta cadastrada.");
            return;
        } else {
            String nome = campoNomeCadastro.getText();
            String endereco = campoEndCadastro.getText();
            String telefone = campoTelCadastro.getText();
            String email = campoEmailCadastro.getText();
            String senha = campoSenhaCadastro.getText();
            
            if (clientesDAO.buscarPorEmail(email)) {
                exibirMensagem("Erro", "O e-mail informado já está associado a outra conta.");
                return;
            }

            Alert confirmacao = new Alert(AlertType.CONFIRMATION);
            confirmacao.setTitle("Confirmação de Cadastro");
            confirmacao.setHeaderText("Por favor, confirme os dados:");
            confirmacao.setContentText("Nome: " + nome + "\nEndereço: " + endereco + "\nTelefone: " + telefone + "\nEmail: " + email);            
            confirmacao.getButtonTypes().setAll(ButtonType.YES, ButtonType.CANCEL);
            
            ButtonType resultado = confirmacao.showAndWait().orElse(ButtonType.CANCEL);

            if (resultado == ButtonType.YES) {
                Clientes novoCliente = new Clientes(cpf, nome, endereco, telefone, email, senha);
                clientesDAO.incluir(novoCliente);

                exibirMensagem("Cadastro realizado", "Seu cadastro foi realizado com sucesso, bem vindo!");
                abrirTelaMenu();
            } else {
            	 confirmacao.close();
            }

        }
    }
        
    private void exibirMensagem(String titulo, String mensagem) {
    	Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
    
    private void abrirTelaMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("telaMenu.fxml"));
            Parent root = loader.load();
            FXMLMenuController menuController = loader.getController();

            menuController.setClienteAutenticado(clientesDAO.buscar(campoCpfCadastro.getText()));
            Scene scene = campoCpfCadastro.getScene();
            Stage stage = (Stage) scene.getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }
}