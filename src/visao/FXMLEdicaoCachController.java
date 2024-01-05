package visao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;
import dominio.*;
import persistencia.*;

public class FXMLEdicaoCachController implements Initializable {

    @FXML
    private TextField campoNome;
    @FXML
    private TextField campoIdade;
    @FXML
    private TextField campoCor;
    @FXML
    private TextField campoPeso;
    @FXML
    private TextField campoRaca;
    @FXML
    private TextField campoPorte;
    @FXML
    private TextField campoHist;
    @FXML
    private Button btnSalvar, btnCan;

    private Cachorros cachorro;
    private CachorrosDAO cachorrosDAO = new CachorrosDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	btnSalvar.setOnAction(event -> salvarAlteracoes());
        btnCan.setOnAction(event -> cancelarEdicao());
    }

    public void setCachorro(Cachorros cachorro) {
		this.cachorro=cachorro;
		preencherCampos();
	}

    private void preencherCampos() {
        campoNome.setText(cachorro.getNome());
        campoIdade.setText(String.valueOf(cachorro.getIdade()));
        campoCor.setText(cachorro.getCor());
        campoPeso.setText(String.valueOf(cachorro.getPeso()));
        campoRaca.setText(cachorro.getRaca());
        campoPorte.setText(cachorro.getPorte());
        campoHist.setText(cachorro.getHistoricoSaude());
    }

    @FXML
    public void salvarAlteracoes() {
        String nome = campoNome.getText().trim();
        int idade = Integer.parseInt(campoIdade.getText().trim());
        String cor = campoCor.getText().trim();
        double peso = Double.parseDouble(campoPeso.getText().trim());
        String raca = campoRaca.getText().trim();
        String porte = campoPorte.getText().trim();
        String histSaude = campoHist.getText().trim();

        Cachorros cachorroExistente = cachorrosDAO.buscarPorCaracteristicas(nome, idade, cor, peso, raca, porte, histSaude, cachorro.getCliente());

        if (cachorroExistente != null && cachorroExistente.getId() != cachorro.getId()) {
            cachorroExistente.setNome(nome);
            cachorroExistente.setIdade(idade);
            cachorroExistente.setCor(cor);
            cachorroExistente.setPeso(peso);
            cachorroExistente.setRaca(raca);
            cachorroExistente.setPorte(porte);
            cachorroExistente.setHistoricoSaude(histSaude);

            cachorrosDAO.alterar(cachorroExistente);

            ((Stage) campoNome.getScene().getWindow()).close();
        } else {
            cachorro.setNome(nome);
            cachorro.setIdade(idade);
            cachorro.setCor(cor);
            cachorro.setPeso(peso);
            cachorro.setRaca(raca);
            cachorro.setPorte(porte);
            cachorro.setHistoricoSaude(histSaude);

            cachorrosDAO.alterar(cachorro);

            ((Stage) campoNome.getScene().getWindow()).close();
        }
    }
    
    @FXML
    public void cancelarEdicao() {
        ((Stage) campoNome.getScene().getWindow()).close();
    }

    private void exibirAlerta(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}