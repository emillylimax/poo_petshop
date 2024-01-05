package dominio;

//Pilares de POO: Abstração
//Pilares de POO: Encapsulamento (private, public, getters e setters sendo utilizados)

public abstract class Animais {
    private int id;
    private String nome;
    private int idade;
    private String cor;
    private double peso;
    private String historicoSaude;
    private Clientes cliente;

    public Animais(String nome, int idade, String cor, double peso, String historicoSaude, Clientes cliente) {
        this.nome = nome;
        this.idade = idade;
        this.cor = cor;
        this.peso = peso;
        this.historicoSaude = historicoSaude;
        this.cliente = cliente;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getHistoricoSaude() {
        return historicoSaude;
    }

    public void setHistoricoSaude(String historicoSaude) {
        this.historicoSaude = historicoSaude;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }
    
    //Método abstrato
    public abstract String exibirObj();
}