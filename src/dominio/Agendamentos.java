package dominio;
import java.util.ArrayList;

//Pilares de POO: Encapsulamento (private, public, getters e setters sendo utilizados)

public class Agendamentos {
    private int id;
    private String data;
    private String hora;
    private Clientes cliente; 
    private Gatos gato;
    private Cachorros cachorro;
    private ArrayList<Servicos_agendados> servicosAgendados;
    private Pagamentos pagamento;

    public Agendamentos() {
    	this.servicosAgendados = new ArrayList<>();
    }
    
	public Agendamentos(int id, String data, String hora, Clientes cliente, Gatos gato, Cachorros cachorro) {
    	this.id = id;
    	this.data = data;
        this.hora = hora;
        this.cliente = cliente;
        this.gato = gato;
        this.cachorro = cachorro;
        this.servicosAgendados = new ArrayList<>();
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getData() {
        return data;
    }
    
    public void setData(String data) {
        this.data = data;
    }
    
    public String getHora() {
        return hora;
    }
    
    public void setHora(String hora) {
        this.hora = hora;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public Gatos getGato() {
        return gato;
    }

    public void setGato(Gatos gato) {
        this.gato = gato;
    }

    public Cachorros getCachorro() {
        return cachorro;
    }

    public void setCachorro(Cachorros cachorro) {
        this.cachorro = cachorro;
    }

    public ArrayList<Servicos_agendados> getServicosAgendados() {
        return servicosAgendados;
    }

    public void adicionarServicoAgendado(Servicos_agendados servicoAgendado) {
        servicosAgendados.add(servicoAgendado);
    }
    
    public Pagamentos getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamentos pagamento) {
		this.pagamento = pagamento;
	}
}