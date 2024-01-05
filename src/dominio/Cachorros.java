package dominio;

//Pilares de POO: Herança
//Pilares de POO: Polimorfismo
//Pilares de POO: Encapsulamento (private, public, getters e setters sendo utilizados)

public class Cachorros extends Animais {
    private String raca;
    private String porte;

//Pilares de POO: Polimorfismo(Sobrecarga)
    public Cachorros(String nome, int idade, String cor, double peso, String raca, String porte, String historicoSaude, Clientes cliente) {
        super(nome, idade, cor, peso, historicoSaude, cliente);
        this.raca = raca;
        this.porte = porte;
    }
    
//Pilares de POO: Polimorfismo(Sobrecarga)
    public Cachorros(int id, String nome, int idade, String cor, double peso, String raca, String porte, String historicoSaude, Clientes cliente) {
        super(nome, idade, cor, peso, historicoSaude, cliente);
        this.raca = raca;
        this.porte = porte;
        setId(id);
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getPorte() {
        return porte;
    }

    public void setPorte(String porte) {
        this.porte = porte;
    }
    
//Pilares de POO: Polimorfismo(Sobreposição)    
    public String exibirObj() {
            return "Cachorro: " +
                    "\nNome: " + getNome() +
                    "\nIdade: " + getIdade() +
                    "\nCor: " + getCor() +
                    "\nPeso: " + getPeso() +
                    "\nRaça: " + getRaca() +
                    "\nPorte: " + getPorte() +
                    "\nHistórico de Saúde: " + getHistoricoSaude() +
                    "\nDono: " + getCliente().getNome();
   }
 }