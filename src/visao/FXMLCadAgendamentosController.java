package visao;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import dominio.*;
import persistencia.*;

public class FXMLCadAgendamentosController implements Initializable {
    private ServicosDAO servicosDAO = new ServicosDAO();
    private AgendamentosDAO agendamentosDAO = new AgendamentosDAO();
    private Servicos_agendadosDAO servicosAgendadosDAO = new Servicos_agendadosDAO();

    @FXML
    private Button btnConfirmar, btnVoltar;

    @FXML
    private CheckBox checkBanho, checkTosa, checkHidra, checkUnhas, checkDentes, checkOuvidos;

    @FXML
    private TextField animalNome, dataAgen, horaAgen;

    private Clientes clienteLogado;

    public void setCliente(Clientes cliente) {
        this.clienteLogado = cliente;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        agendamentosDAO = new AgendamentosDAO();

        selecionaCheckBox(checkBanho);
        selecionaCheckBox(checkTosa);
        selecionaCheckBox(checkHidra);
        selecionaCheckBox(checkUnhas);
        selecionaCheckBox(checkDentes);
        selecionaCheckBox(checkOuvidos);
        
        btnConfirmar.setOnAction(event -> salvarAgendamento(event));
    }

    private void selecionaCheckBox(CheckBox checkbox) {
        checkbox.setOnAction(event -> {
            if (checkbox.isSelected()) {
                Servicos_agendados servicoAgendado = new Servicos_agendados(agendamentosDAO, servicosDAO.obterServicoPorNome(checkbox.getText()));
                agendamentosDAO.adicionarServicoAgendado(servicoAgendado);
            }
        });
    }

    @FXML
    private void salvarAgendamento(ActionEvent event) {
        agendamentosDAO.setData(dataAgen.getText());
        agendamentosDAO.setHora(horaAgen.getText());

        Gatos gato = null;
        Cachorros cachorro = null;

        GatosDAO gatosDAO = new GatosDAO();
        CachorrosDAO cachorrosDAO = new CachorrosDAO();

        gato = gatosDAO.buscarGatoPorNomeECliente(animalNome.getText(), clienteLogado);
        cachorro = cachorrosDAO.buscarCachorroPorNomeECliente(animalNome.getText(), clienteLogado);

        if (gato != null) {
            agendamentosDAO.setGato(gato);
        } else if (cachorro != null) {
            agendamentosDAO.setCachorro(cachorro);
        }

        if (agendamentosDAO.getGato() != null || agendamentosDAO.getCachorro() != null) {
            agendamentosDAO.setCliente(clienteLogado);

            AgendamentosDAO agendamentosDAO = new AgendamentosDAO();
            agendamentosDAO.criarAgendamento(agendamentosDAO);

            Servicos_agendadosDAO servicosAgendadosDAO = new Servicos_agendadosDAO();
            for (Servicos_agendados servicoAgendado : agendamentosDAO.getServicosAgendados()) {
                servicosAgendadosDAO.associarServicoAgendamento(servicoAgendado);
            }
        } else {
            System.out.println("Animal não encontrado para o cliente.");
        }
    }
}