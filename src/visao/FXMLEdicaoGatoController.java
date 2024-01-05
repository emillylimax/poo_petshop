package visao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
//import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;
import dominio.Gatos;
import persistencia.GatosDAO;


public class FXMLEdicaoGatoController implements Initializable {

    @FXML
    private TextField campoNome;
    @FXML
    private TextField campoIdade;
    @FXML
    private TextField campoCor;
    @FXML
    private TextField campoPeso;
    @FXML
    private TextField campoPelo;
    @FXML
    private TextField campoHist;
    @FXML
    private Button btnSalvar, btnCan;

    private Gatos gatoParaEdicao;
    private GatosDAO gatosDAO = new GatosDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	btnSalvar.setOnAction(event -> salvarAlteracoes());
        btnCan.setOnAction(event -> cancelarEdicao());
    }
    
    public void setGatoParaEdicao(Gatos gato) {
      this.gatoParaEdicao = gato;
      preencherCampos();
    }
  
    private void preencherCampos() {
        campoNome.setText(gatoParaEdicao.getNome());
        campoIdade.setText(String.valueOf(gatoParaEdicao.getIdade()));
        campoCor.setText(gatoParaEdicao.getCor());
        campoPeso.setText(String.valueOf(gatoParaEdicao.getPeso()));
        campoPelo.setText(gatoParaEdicao.getTipoPelo());
        campoHist.setText(gatoParaEdicao.getHistoricoSaude());
    }
   
    @FXML
    public void salvarAlteracoes() {
        String nome = campoNome.getText().trim();
        int idade = Integer.parseInt(campoIdade.getText().trim());
        String cor = campoCor.getText().trim();
        double peso = Double.parseDouble(campoPeso.getText().trim());
        String pelo = campoPelo.getText().trim();
        String histSaude = campoHist.getText().trim();
       
        Gatos gatoExistente = gatosDAO.buscarPorCaracteristicas(nome, idade, cor, peso, pelo, histSaude, gatoParaEdicao.getCliente());

        if (gatoExistente != null && gatoExistente.getId() != gatoParaEdicao.getId()) {
            gatoExistente.setNome(nome);
            gatoExistente.setIdade(idade);
            gatoExistente.setCor(cor);
            gatoExistente.setPeso(peso);
            gatoExistente.setTipoPelo(pelo);
            gatoExistente.setHistoricoSaude(histSaude);

            gatosDAO.alterar(gatoExistente);

            ((Stage) campoNome.getScene().getWindow()).close();
        } else {
        	gatoParaEdicao.setNome(nome);
        	gatoParaEdicao.setIdade(idade);
        	gatoParaEdicao.setCor(cor);
        	gatoParaEdicao.setPeso(peso);
        	gatoParaEdicao.setTipoPelo(pelo);
        	gatoParaEdicao.setHistoricoSaude(histSaude);

            gatosDAO.alterar(gatoParaEdicao);

            ((Stage) campoNome.getScene().getWindow()).close();
        }
    }
 
    @FXML
    public void cancelarEdicao() {
        
        ((Stage) campoNome.getScene().getWindow()).close();
    }

//    private void exibirAlerta(String mensagem) {
//        Alert alert = new Alert(Alert.AlertType.WARNING);
//        alert.setTitle("Aviso");
//        alert.setContentText(mensagem);
//        alert.showAndWait();
//    }
    
}