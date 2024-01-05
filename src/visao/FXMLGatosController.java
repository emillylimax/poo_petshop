package visao;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.net.URL;
import java.util.ResourceBundle;

import dominio.*;
import persistencia.*;

public class FXMLGatosController implements Initializable {
	@FXML
    private Button btnCancGato, btnSalvarGato;
	@FXML
    private TextField campoNomeGato, campoIdadeGato, campoCorGato, campoPesoGato, campoPeloGato, campoHistGato;
	
	private Clientes cliente;
	GatosDAO gatosDAO=new GatosDAO();
	
	public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }
	
	 @Override
	    public void initialize(URL arg0, ResourceBundle arg1) {
	        Platform.runLater(() -> campoNomeGato.requestFocus());
	        btnSalvarGato.setOnAction(event -> salvarGato());
	    }

	 @FXML
	    private void salvarGato() {
	        if (gatosDAO.buscarPorNome(campoNomeGato.getText()) != null) {
	            exibirMensagem("Gato já cadastrado", "Este gato já está cadastrado para o cliente.");
	            return;
	        }

	        String nomeGato = campoNomeGato.getText();
	        int idadeGato = Integer.parseInt(campoIdadeGato.getText());
	        String corGato = campoCorGato.getText();
	        double pesoGato = Double.parseDouble(campoPesoGato.getText());
	        String tipoPeloGato = campoPeloGato.getText();
	        String histGato = campoHistGato.getText();

	        Gatos novoGato = new Gatos(nomeGato, idadeGato, corGato, pesoGato, tipoPeloGato, histGato, cliente);
	        gatosDAO.incluir(novoGato);
	        
	        String detalhesGato = novoGato.exibirObj();
	        exibirDetalhesGatoNaInterface(detalhesGato);
	        
	        exibirMensagem("Gato cadastrado", "O gato foi cadastrado com sucesso.");
	        fecharJanela();
	    }

	    @FXML
	    private void cancelarCadastroGato(ActionEvent event) {
	        try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("telaMeusAnimais.fxml"));
	            Parent root = loader.load();
	            Scene scene = new Scene(root);
	            FXMLAnimaisController animaisController = loader.getController();
	            animaisController.setCliente(cliente);
	            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	            stage.setScene(scene);
	            stage.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    private void exibirMensagem(String titulo, String mensagem) {
	        Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle(titulo);
	        alert.setHeaderText(null);
	        alert.setContentText(mensagem);
	        alert.showAndWait();
	    }

	    private void fecharJanela() {
	        Stage stage = (Stage) btnCancGato.getScene().getWindow();
	        stage.close();
	    }
	    
	    private void exibirDetalhesGatoNaInterface(String detalhesGato) {
	        Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle("Detalhes do Gato");
	        alert.setHeaderText(null);
	        alert.setContentText(detalhesGato);
	        alert.showAndWait();
	    }
}