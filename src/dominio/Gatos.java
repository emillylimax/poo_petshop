package dominio;

//Pilares de POO: Encapsulamento (private, public, getters e setters sendo utilizados)
//Pilares de POO: Herança

public class Gatos extends Animais{
    private String tipoPelo;

//Pilares de POO: Polimorfismo(Sobrecarga)
    public Gatos(String nome, int idade, String cor, double peso, String tipoPelo, String historicoSaude, Clientes cliente) {
        super(nome, idade, cor, peso, historicoSaude, cliente);
        this.tipoPelo = tipoPelo;
    }
    
//Pilares de POO: Polimorfismo(Sobrecarga)
    public Gatos(int id, String nome, int idade, String cor, double peso, String tipoPelo, String historicoSaude, Clientes cliente) {
        super(nome, idade, cor, peso, historicoSaude, cliente);
        this.tipoPelo = tipoPelo;
        setId(id);
    }
    
    public String getTipoPelo() {
        return tipoPelo;
    }

    public void setTipoPelo(String tipoPelo) {
        this.tipoPelo = tipoPelo;
    }

//Pilares de POO: Polimorfismo(Sobreposição)
    public String exibirObj() {
        return "Gato: " +
                "\nNome: " + getNome() +
                "\nIdade: " + getIdade() +
                "\nCor: " + getCor() +
                "\nPeso: " + getPeso() +
                "\nTipo de pelo: " + getTipoPelo() +
                "\nHistórico de Saúde: " + getHistoricoSaude() +
                "\nDono: " + getCliente().getNome();
    }
}