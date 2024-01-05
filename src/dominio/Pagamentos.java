package dominio;

//Pilares de POO: Encapsulamento (private, public, getters e setters sendo utilizados)

public class Pagamentos {
    private int id;
    private double valorTotal;
    private String metodoPagamento;
    private Agendamentos agendamento;

    public Pagamentos(double valorTotal, String metodoPagamento, Agendamentos agendamento) {
        this.valorTotal = valorTotal;
        this.metodoPagamento = metodoPagamento;
        this.agendamento = agendamento;
    }

    public int getId() {
        return id;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getMetodoPagamento() {
		return metodoPagamento;
	}

	public void setMetodoPagamento(String metodoPagamento) {
		this.metodoPagamento = metodoPagamento;
	}

	public Agendamentos getAgendamento() {
        return agendamento;
    }

    public void setAgendamento(Agendamentos agendamento) {
        this.agendamento = agendamento;
    }
}