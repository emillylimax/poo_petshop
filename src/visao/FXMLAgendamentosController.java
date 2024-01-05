package visao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dominio.Agendamentos;
import dominio.Clientes;
import persistencia.AgendamentosDAO;

public class FXMLAgendamentosController implements Initializable {
    @FXML
    private Button btnExcluir, btnCad, btnVoltar;

    @FXML
    private TableView<Agendamentos> tvAgen;

    @FXML
    private TableColumn<Agendamentos, Integer> idAgen;

    @FXML
    private TableColumn<Agendamentos, String> animalAgen;

    @FXML
    private TableColumn<Agendamentos, String> servicosAgen;

    @FXML
    private TableColumn<Agendamentos, String> dataAgen;

    @FXML
    private TableColumn<Agendamentos, String> horaAgen;

    @FXML
    private TableColumn<Agendamentos, Double> totalAgen;

    private Clientes cliente;
    private AgendamentosDAO agendamentosDAO = new AgendamentosDAO();

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
        carregarAgendamentosDoCliente();
        btnCad.setOnAction(event -> telaCadastro(event));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idAgen.setCellValueFactory(new PropertyValueFactory<>("id"));
        animalAgen.setCellValueFactory(new PropertyValueFactory<>("nomeAnimal"));
        servicosAgen.setCellValueFactory(new PropertyValueFactory<>("servicos"));
        dataAgen.setCellValueFactory(new PropertyValueFactory<>("data"));
        horaAgen.setCellValueFactory(new PropertyValueFactory<>("hora"));
        totalAgen.setCellValueFactory(new PropertyValueFactory<>("total"));

        btnExcluir.setOnAction(event -> excluirAgendamento());
        btnCad.setOnAction(event -> cadastrarAgendamento());
        btnVoltar.setOnAction(event -> voltar(event));

        carregarAgendamentosDoCliente();
        btnVoltar.setOnAction(event -> voltar(event));
    }

    private void carregarAgendamentosDoCliente() {
	    ObservableList<Agendamentos> agendamentosDoCliente = FXCollections.observableArrayList(
		        agendamentosDAO.buscarPorCliente(cliente));

		    tvAgen.setItems(agendamentosDoCliente);
		}

    private void excluirAgendamento() {
       
    }

    private void cadastrarAgendamento() {
        
    }

    public void voltar(ActionEvent event) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("telaMenu.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            FXMLMenuController menuController = loader.getController();
            menuController.setClienteAutenticado(cliente);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }	
    
    }
    
    private void telaCadastro(ActionEvent event) {
   	 try {
   	        FXMLLoader loader = new FXMLLoader(getClass().getResource("telaCadastrarAgendamentos.fxml"));
   	        Parent root = loader.load();
   	        FXMLCadAgendamentosController cadAgendamentosController = loader.getController();
   	        cadAgendamentosController.setCliente(cliente);
   	        Scene scene = new Scene(root);
   	        Stage stage = new Stage();
   	        stage.setTitle("Cadastro de Agendamentos");
   	        //stage.initModality(Modality.APPLICATION_MODAL);
   	        stage.setScene(scene);
   	        stage.show();
   	    } catch (IOException e) {
   	        e.printStackTrace();
   	    }
   	}
}