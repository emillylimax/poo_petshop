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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import dominio.*;
import persistencia.*;

public class FXMLAnimaisController implements Initializable {

    @FXML
    private TableView<Gatos> tvGato;
    @FXML
    private TableView<Cachorros> tvCach;
    @FXML
    private TableColumn<Gatos, Integer> colunaId;
    @FXML
    private TableColumn<Gatos, String> colunaNome;
    @FXML
    private TableColumn<Gatos, Integer> colunaIdade;
    @FXML
    private TableColumn<Gatos, String> colunaCor;
    @FXML
    private TableColumn<Gatos, Double> colunaPeso;
    @FXML
    private TableColumn<Gatos, String> colunaPelo;
    @FXML
    private TableColumn<Gatos, String> colunaHist;
    @FXML
    private TableColumn<Cachorros, Integer> colunaId2;
    @FXML
    private TableColumn<Cachorros, String> colunaNome2;
    @FXML
    private TableColumn<Cachorros, Integer> colunaIdade2;
    @FXML
    private TableColumn<Cachorros, String> colunaCor2;
    @FXML
    private TableColumn<Cachorros, Double> colunaPeso2;
    @FXML
    private TableColumn<Cachorros, String> colunaRaca;
    @FXML
    private TableColumn<Cachorros, String> colunaPorte;
    @FXML
    private TableColumn<Cachorros, String> colunaHist2;
    @FXML
    private Button btnVoltar, btnAlterar, btnExcluir, btnCadastroGatos, btnCadastroCach;
    private ObservableList<Gatos> gatosData = FXCollections.observableArrayList();
    private ObservableList<Cachorros> cachorrosData = FXCollections.observableArrayList();
    private GatosDAO gatosDAO = new GatosDAO();
    private CachorrosDAO cachorrosDAO = new CachorrosDAO();
    private Clientes cliente;

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
        inicializarTabelaGatos(); 
        inicializarTabelaCachorros();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	btnAlterar.setOnAction(event -> alterarAnimal(event));
    	btnExcluir.setOnAction(event -> excluirAnimal(event));
    	btnVoltar.setOnAction(event -> voltar(event));
    	btnCadastroGatos.setOnAction(event -> cadastrarGatos(event));
    	btnCadastroCach.setOnAction(event -> cadastrarCachorros(event));
    }

    private void inicializarTabelaGatos() {
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));
        colunaCor.setCellValueFactory(new PropertyValueFactory<>("cor"));
        colunaPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
        colunaPelo.setCellValueFactory(new PropertyValueFactory<>("tipoPelo"));
        colunaHist.setCellValueFactory(new PropertyValueFactory<>("historicoSaude"));

        gatosData.addAll(gatosDAO.listarGatosPorCliente(cliente.getCpf()));
        tvGato.setItems(gatosData);
    }
    
    private void inicializarTabelaCachorros() {
        colunaId2.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNome2.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaIdade2.setCellValueFactory(new PropertyValueFactory<>("idade"));
        colunaCor2.setCellValueFactory(new PropertyValueFactory<>("cor"));
        colunaPeso2.setCellValueFactory(new PropertyValueFactory<>("peso"));
        colunaRaca.setCellValueFactory(new PropertyValueFactory<>("raca"));
        colunaPorte.setCellValueFactory(new PropertyValueFactory<>("porte"));
        colunaHist2.setCellValueFactory(new PropertyValueFactory<>("historicoSaude"));

        cachorrosData.addAll(cachorrosDAO.listarCachorrosPorCliente(cliente.getCpf()));
        tvCach.setItems(cachorrosData);
    }
    
    @FXML
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
        
    @FXML
    public void excluirAnimal(ActionEvent event) {
        if (tvGato.getSelectionModel().getSelectedItem() != null) {
            excluirGato();
        } else if (tvCach.getSelectionModel().getSelectedItem() != null) {
            excluirCachorro();
        } else {
            exibirAlerta("Selecione um animal para excluir.");
        }
    }

    private void excluirGato() {
        Gatos gatoSelecionado = tvGato.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de Exclusão");
        alert.setHeaderText("Tem certeza que deseja excluir o gato " + gatoSelecionado.getNome() + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            gatosDAO.excluir(gatoSelecionado.getId());
            gatosData.remove(gatoSelecionado);
        }
    }

    private void excluirCachorro() {
        Cachorros cachorroSelecionado = tvCach.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de Exclusão");
        alert.setHeaderText("Tem certeza que deseja excluir o cachorro " + cachorroSelecionado.getNome() + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            cachorrosDAO.excluir(cachorroSelecionado.getId());
            cachorrosData.remove(cachorroSelecionado);
        }
    }

    private void exibirAlerta(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setContentText(mensagem);
        alert.showAndWait();
    }  
    
    @FXML
    private void cadastrarGatos(ActionEvent event) {
        try {
        	 FXMLLoader loader = new FXMLLoader(getClass().getResource("telaGatos.fxml"));
             Parent root = loader.load();

             FXMLGatosController gatosController = loader.getController();
             gatosController.setCliente(cliente);

             Scene scene = new Scene(root);
             Stage stage = new Stage();
             stage.setTitle("Cadstro de Gatos");
             stage.initModality(Modality.APPLICATION_MODAL);
             stage.setScene(scene);
             stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 
    
    @FXML
    private void cadastrarCachorros(ActionEvent event) {
        try {
        	 FXMLLoader loader = new FXMLLoader(getClass().getResource("telaCachorros.fxml"));
             Parent root = loader.load();

             FXMLCachorrosController cachorrosController = loader.getController();
             cachorrosController.setCliente(cliente);

             Scene scene = new Scene(root);
             Stage stage = new Stage();
             stage.setTitle("Cadstro de Cachorros");
             stage.initModality(Modality.APPLICATION_MODAL);
             stage.setScene(scene);
             stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }     
    
    @FXML
    public void alterarAnimal(ActionEvent event) {
        if (tvGato.getSelectionModel().getSelectedItem() != null) {
            Gatos animalSelecionado = tvGato.getSelectionModel().getSelectedItem();
            abrirTelaEdicaoGato(animalSelecionado);
        } else if (tvCach.getSelectionModel().getSelectedItem() != null) {
            Cachorros animalSelecionado = tvCach.getSelectionModel().getSelectedItem();
            abrirTelaEdicaoCachorro(animalSelecionado);
        } else {
            exibirAlerta("Selecione um animal para editar.");
        }
    }
    
    private void abrirTelaEdicaoCachorro(Cachorros cachorro) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("telaCachorrosAlterarDados.fxml"));
            Parent root = loader.load();

            FXMLEdicaoCachController edicaoController = loader.getController();
            edicaoController.setCachorro(cachorro);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Edição de Cachorro");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

            inicializarTabelaGatos();
            inicializarTabelaCachorros();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /////
    private void abrirTelaEdicaoGato(Gatos gato) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("telaGatosAlterarDados.fxml"));
            Parent root = loader.load();

            FXMLEdicaoGatoController edicaoController = loader.getController();
            edicaoController.setGatoParaEdicao(gato);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Edição de Gato");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

            inicializarTabelaGatos();
            inicializarTabelaCachorros();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}