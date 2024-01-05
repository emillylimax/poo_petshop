package dominio;

//Pilares de POO: Encapsulamento (private, public, getters e setters sendo utilizados)

public class Servicos_agendados {
    private int id;
    private Agendamentos agendamento;
    private Servicos servico;

    public Servicos_agendados(Agendamentos agendamento, Servicos servico) {
        this.agendamento = agendamento;
        this.servico = servico;
    }

    public int getId() {
        return id;
    }

    public Agendamentos getAgendamento() {
        return agendamento;
    }

    public void setAgendamento(Agendamentos agendamento) {
        this.agendamento = agendamento;
    }

    public Servicos getServico() {
        return servico;
    }

    public void setServico(Servicos servico) {
        this.servico = servico;
    }
}