package dominio;
import java.util.ArrayList;

//Pilares de POO: Encapsulamento (private, public, getters e setters sendo utilizados)

public class Clientes {
    private String cpf;
    private String nome;
    private String endereco;
    private String telefone;
    private String email;
    private String senha;
    private ArrayList<Agendamentos> agendamentos;
    private ArrayList<Gatos> gatos;
    private ArrayList<Cachorros> cachorros;

    public Clientes() {
        //esse construtor vazio foi criado para evitar os erros de NullPointer
    }
    
    public Clientes(String cpf, String nome, String endereco, String telefone, String email, String senha) {
        this.cpf = cpf;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
        this.senha=senha;
        this.agendamentos = new ArrayList<>();
        this.gatos = new ArrayList<>();
        this.cachorros = new ArrayList<>();
    }

    public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public ArrayList<Agendamentos> getAgendamentos() {
        return agendamentos;
    }

    public ArrayList<Gatos> getGatos() {
        return gatos;
    }

    public ArrayList<Cachorros> getCachorros() {
        return cachorros;
    }
    
    public ArrayList<Animais> consultarAnimais() {
        ArrayList<Animais> animais = new ArrayList<>();
        
        animais.addAll(this.gatos);
        animais.addAll(this.cachorros);

        return animais;
    }

    public void adicionarAgendamento(Agendamentos agendamento) {
        agendamentos.add(agendamento);
    }

    public void adicionarGato(Gatos gato) {
        gatos.add(gato);
    }

    public void adicionarCachorro(Cachorros cachorro) {
        cachorros.add(cachorro);
    }

    public void removerAgendamento(Agendamentos agendamento) {
        agendamentos.remove(agendamento);
    }

    public void removerGato(Gatos gato) {
        gatos.remove(gato);
    }

    public void removerCachorro(Cachorros cachorro) {
        cachorros.remove(cachorro);
    }
}