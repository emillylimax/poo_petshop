package visao;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import dominio.Clientes;
import persistencia.ClientesDAO;

public class FXMLMenuController {

    @FXML
    private Button btnMeuPerfil, btnMeusAnimais, btnAgendamentos;

    private Clientes clienteAutenticado;
    private ClientesDAO clientesDAO = new ClientesDAO();

    @FXML
    private void initialize() {
        btnMeuPerfil.setOnAction(event -> mostrarMeuPerfil());
        btnMeusAnimais.setOnAction(event -> mostrarMeusAnimais());
        btnAgendamentos.setOnAction(event -> mostrarAgendamentos());
    }

    public void setClienteAutenticado(Clientes cliente) {
        this.clienteAutenticado = cliente;
        System.out.println("Valor de cliente classe menu: " + cliente);
    }

    @FXML
    private void mostrarMeuPerfil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("telaMeuPerfil.fxml"));
            Parent root = loader.load();

            FXMLPerfilController perfilController = loader.getController();
            perfilController.setCliente(clienteAutenticado);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Meu Perfil");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            exibirMensagem("Erro ao exibir perfil", "Houve um problema ao exibir o perfil.");
        }
    }

    @FXML
    private void mostrarMeusAnimais() {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("telaMeusAnimais.fxml"));
            Parent root = loader.load();

            FXMLAnimaisController animaisController = loader.getController();
            animaisController.setCliente(clienteAutenticado);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Meus Animais");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            exibirMensagem("Erro ao exibir animais", "Houve um problema ao exibir os animais.");
        }
    }

    @FXML
    private void mostrarAgendamentos() {
        try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("telaAgendamentos.fxml"));
            Parent root = loader.load();

            FXMLAgendamentosController agendamentosController = loader.getController();
            agendamentosController.setCliente(clienteAutenticado);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Agendamentos");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            exibirMensagem("Erro ao exibir os agendamentos", "Houve um problema ao exibir os agendamentos.");
        }
    }

    private void exibirMensagem(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}