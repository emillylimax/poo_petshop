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

public class FXMLCachorrosController implements Initializable {
    @FXML
    private Button btnCancCachorro, btnSalvarCachorro;
    @FXML
    private TextField campoNomeCachorro, campoIdadeCachorro, campoCorCachorro, campoPesoCachorro, campoRacaCachorro, campoPorteCachorro, campoHistCachorro;

    private Clientes cliente;
    CachorrosDAO cachorrosDAO = new CachorrosDAO();

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        Platform.runLater(() -> campoNomeCachorro.requestFocus());
        btnSalvarCachorro.setOnAction(event -> salvarCachorro());
    }

    @FXML
    private void salvarCachorro() {
        if (cachorrosDAO.buscarPorNome(campoNomeCachorro.getText()) != null) {
            exibirMensagem("Cachorro já cadastrado", "Este cachorro já está cadastrado para o cliente.");
            return;
        }

        String nomeCachorro = campoNomeCachorro.getText();
        int idadeCachorro = Integer.parseInt(campoIdadeCachorro.getText());
        String corCachorro = campoCorCachorro.getText();
        double pesoCachorro = Double.parseDouble(campoPesoCachorro.getText());
        String racaCachorro = campoRacaCachorro.getText();
        String porteCachorro = campoPorteCachorro.getText();
        String histCachorro = campoHistCachorro.getText();

        Cachorros novoCachorro = new Cachorros(nomeCachorro, idadeCachorro, corCachorro, pesoCachorro, racaCachorro, porteCachorro, histCachorro, cliente);
        cachorrosDAO.incluir(novoCachorro);

        String detalhesCachorro = novoCachorro.exibirObj();
        exibirDetalhesCachorroNaInterface(detalhesCachorro);
        
        exibirMensagem("Cachorro cadastrado", "O cachorro foi cadastrado com sucesso.");

        fecharJanela();
    }

    @FXML
    private void cancelarCadastroCachorro(ActionEvent event) {
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
        Stage stage = (Stage) btnCancCachorro.getScene().getWindow();
        stage.close();
    }
    
    private void exibirDetalhesCachorroNaInterface(String detalhesCachorro) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Detalhes do Cachorro");
        alert.setHeaderText(null);
        alert.setContentText(detalhesCachorro);
        alert.showAndWait();
    }
}