package visao;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dominio.Clientes;
import persistencia.ClientesDAO;

public class FXMLPerfilController implements Initializable {
    @FXML
    private TextField campoNomePerfil, campoCpfPerfil, campoEndPerfil, campoTelPerfil, campoEmailPerfil, campoSenhaPerfil;
    @FXML
    private AnchorPane anchorPaneGeral, anchorPaneCampos;
    @FXML
    private Button btnAltPerfil, btnVoltarPerfil, btnExcluirPerfil, btnSairPerfil;

    private Clientes cliente;
    private ClientesDAO clientesDAO = new ClientesDAO();
    private boolean dadosAlterados = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        campoNomePerfil.setEditable(false);
        campoCpfPerfil.setEditable(false);
        campoEndPerfil.setEditable(false);
        campoTelPerfil.setEditable(false);
        campoEmailPerfil.setEditable(false);
        campoSenhaPerfil.setEditable(false);

        btnAltPerfil.setOnAction(event -> alterarCliente(event));
        btnExcluirPerfil.setOnAction(event -> excluirCliente(event));
        btnVoltarPerfil.setOnAction(event -> voltarPerfil(event));
        btnSairPerfil.setOnAction(event -> logout(event));
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
        if (cliente != null) {
            preencherCampos();
        }
        System.out.println("Valor de cliente classe agendamento: " + cliente);
    }

    @FXML
    public void alterarCliente(ActionEvent event) {
        if (btnAltPerfil.getText().equals("ALTERAR")) {
            btnAltPerfil.setText("SALVAR");
            configurarCampos(true);
        } else {
            Clientes clienteAtualizado = obterClienteAtualizado();

            if (dadosAlterados || !cliente.equals(clienteAtualizado)) {
                if (!camposPreenchidos(clienteAtualizado)) {
                    exibirMensagem("Erro", "Preencha todos os campos antes de salvar.");
                } else {
                    verificarAlteracaoCPF(clienteAtualizado);
                }
            } else {
                exibirMensagem("Aviso", "Nenhuma alteração realizada.");
                configurarCampos(false);
                btnAltPerfil.setText("ALTERAR");
            }
        }
    }

    private boolean camposPreenchidos(Clientes cliente) {
        return !cliente.getNome().isEmpty()
                && !cliente.getCpf().isEmpty()
                && !cliente.getEndereco().isEmpty()
                && !cliente.getTelefone().isEmpty()
                && !cliente.getEmail().isEmpty()
                && !cliente.getSenha().isEmpty();
    }

    private void verificarAlteracaoCPF(Clientes clienteAtualizado) {
        if (!cliente.getCpf().equals(clienteAtualizado.getCpf())) {
            Clientes clienteExistente = clientesDAO.buscar(clienteAtualizado.getCpf());
            if (clienteExistente != null) {
                exibirMensagem("Erro", "Não é possível alterar o CPF, pois já pertence a outro cliente cadastrado.");
                configurarCampos(false);
                btnAltPerfil.setText("ALTERAR");
                preencherCampos();
            } else {
                verificarAlteracaoEmail(clienteAtualizado);
            }
        } else {
            verificarAlteracaoEmail(clienteAtualizado);
        }
    }

    private void verificarAlteracaoEmail(Clientes clienteAtualizado) {
    	if (!cliente.getEmail().equals(clienteAtualizado.getEmail())) {
            if (clientesDAO.buscarPorEmail(clienteAtualizado.getEmail())) {
                exibirMensagem("Erro", "Não é possível alterar o e-mail, pois já pertence a outro cliente cadastrado.");
                configurarCampos(false);
                btnAltPerfil.setText("ALTERAR");
                preencherCampos();
            } else {
                exibirConfirmacaoAlteracao(clienteAtualizado);
            }
        } else {
            exibirConfirmacaoAlteracao(clienteAtualizado);
        }
    }

    private void configurarCampos(boolean editavel) {
        campoNomePerfil.setEditable(editavel);
        campoCpfPerfil.setEditable(editavel);
        campoEndPerfil.setEditable(editavel);
        campoTelPerfil.setEditable(editavel);
        campoEmailPerfil.setEditable(editavel);
        campoSenhaPerfil.setEditable(editavel);
    }

    private void exibirMensagem(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void exibirConfirmacaoAlteracao(Clientes clienteAtualizado) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText("Confirmação de Alteração");

        String dadosCliente = obterDadosCliente(clienteAtualizado);

        alert.setContentText("Deseja salvar as alterações?\n" + dadosCliente);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            cliente = clienteAtualizado;
            clientesDAO.alterar(cliente);
            preencherCampos();
            configurarCampos(false);
            btnAltPerfil.setText("ALTERAR");
            dadosAlterados = false;
        } else {
            configurarCampos(false);
            btnAltPerfil.setText("ALTERAR");
        }
    }

    private Clientes obterClienteAtualizado() {
        return new Clientes(
                campoCpfPerfil.getText(),
                campoNomePerfil.getText(),
                campoEndPerfil.getText(),
                campoTelPerfil.getText(),
                campoEmailPerfil.getText(),
                campoSenhaPerfil.getText()
        );
    }

    private String obterDadosCliente(Clientes cliente) {
        return "Nome: " + cliente.getNome() + "\n" +
                "CPF: " + cliente.getCpf() + "\n" +
                "Endereço: " + cliente.getEndereco() + "\n" +
                "Telefone: " + cliente.getTelefone() + "\n" +
                "Email: " + cliente.getEmail() + "\n" +
                "Senha: " + cliente.getSenha();
    }

    private void preencherCampos() {
        campoNomePerfil.setText(cliente.getNome());
        campoCpfPerfil.setText(cliente.getCpf());
        campoEndPerfil.setText(cliente.getEndereco());
        campoTelPerfil.setText(cliente.getTelefone());
        campoEmailPerfil.setText(cliente.getEmail());
        campoSenhaPerfil.setText(cliente.getSenha());
    }
    
    @FXML
    public void excluirCliente(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText("Confirmação de Exclusão");
        alert.setContentText("Tem certeza que deseja excluir o cliente? Esta ação não pode ser desfeita.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            clientesDAO.excluir(cliente.getCpf());
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("telaLogin.fxml"));
                Parent root = loader.load();
                Scene scene = anchorPaneGeral.getScene();
                scene.setRoot(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void voltarPerfil(ActionEvent event) {
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
    public void logout(ActionEvent event) {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText("Você tem certeza que deseja sair do sistema?");
        alert.setContentText("Clique OK para confirmar, ou Cancelar para continuar no sistema.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("telaLogin.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
    }
    
}