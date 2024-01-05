package visao;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.net.URL;
import java.util.ResourceBundle;

import dominio.*;
import persistencia.*;

public class FXMLLoginController implements Initializable {
 	@FXML
    private Button btnLogin, btnCriarConta;
 	@FXML
    private AnchorPane anchorPane;
    @FXML
    private PasswordField campoSenhaLogin;
    @FXML
    private TextField campoCpfLogin;
    @FXML
    
    private ClientesDAO clientesDAO = new ClientesDAO();
	 
 @Override
    public void initialize(URL location, ResourceBundle resources) {
	 	 Platform.runLater(() -> campoCpfLogin.requestFocus());
    	 btnLogin.setDisable(true);

         btnLogin.disableProperty().bind(
        		 campoCpfLogin.textProperty().isEmpty().or(campoSenhaLogin.textProperty().isEmpty())
         );
         
         btnLogin.setOnAction(event-> Login(event));
         
         btnCriarConta.setOnAction(event -> clickCriarConta(event));
    }
         
    private void Login(ActionEvent event) {
         Clientes clienteLogado = clientesDAO.autentificacao(campoCpfLogin.getText(), campoSenhaLogin.getText());

         if (clienteLogado != null) {
        	 mostrarTelaMenu(clienteLogado);
         } else {
        	exibirAlertaErro("Login falhou", "Credenciais inválidas. Tente novamente.");
        	campoCpfLogin.clear();
            campoSenhaLogin.clear();
         }
     }         
 
    private void exibirAlertaErro(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
  
    private void clickCriarConta(ActionEvent event) {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("telaCadastroClientes.fxml"));
	        Parent root = loader.load();
	        
	        FXMLCadastroController cadastroController = loader.getController();

	        cadastroController.setLoginController(this);

	        Scene scene = new Scene(root);
	        Stage stage = new Stage();
	        stage.setScene(scene);
	        stage.setTitle("Cadastro de Cliente");
	        stage.show();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
    
    private void mostrarTelaMenu(Clientes clienteLogado) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("telaMenu.fxml"));
            Parent menuRoot = loader.load();

            FXMLMenuController menuController = loader.getController();

            menuController.setClienteAutenticado(clienteLogado);

            Scene menuScene = new Scene(menuRoot);

            Stage loginStage = (Stage) btnLogin.getScene().getWindow();

            loginStage.setScene(menuScene);
            loginStage.setTitle("Menu");
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 
}