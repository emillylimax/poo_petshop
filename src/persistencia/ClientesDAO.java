package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import dominio.*;

public class ClientesDAO {
    private Conexao c;
    private String REL = "SELECT * FROM Clientes";
    private String BUS = "SELECT * FROM Clientes WHERE cpf=?";
    private String BUSEMAIL = "SELECT * FROM Clientes WHERE email=?";
    private String AUT = "SELECT * FROM Clientes WHERE cpf=? AND senha=?";
    private String INC = "INSERT INTO Clientes (cpf, nome, endereco, telefone, email, senha) VALUES (?,?,?,?,?,?)";
    private String DEL = "DELETE FROM Clientes WHERE cpf = ?";
    private String ALT = "UPDATE Clientes SET nome = ?, endereco = ?, telefone= ?, email= ?, senha=? WHERE cpf = ?";

    public ClientesDAO() {
        c = new Conexao("jdbc:postgresql://localhost:5432/ProjPetShop", "postgres", "123");
    }

    public void excluir(String cpf) {
        try {
            c.conectar();
            PreparedStatement instrucao = c.getConexao().prepareStatement(DEL);
            instrucao.setString(1, cpf);
            instrucao.executeUpdate();
            c.desconectar();
        } catch (Exception e) {
            System.out.println("Erro na exclusão");
        }
    }

    public void incluir(Clientes cliente) {
        try {
            c.conectar();
            PreparedStatement instrucao = c.getConexao().prepareStatement(INC);
            instrucao.setString(1, cliente.getCpf());
            instrucao.setString(2, cliente.getNome());
            instrucao.setString(3, cliente.getEndereco());
            instrucao.setString(4, cliente.getTelefone());
            instrucao.setString(5, cliente.getEmail());
            instrucao.setString(6, cliente.getSenha());
            instrucao.executeUpdate();
            c.desconectar();
        } catch (Exception e) {
            System.out.println("Erro na inclusão");
        }
    }

    public void alterar(Clientes cliente) {
        try {
            c.conectar();
            PreparedStatement instrucao = c.getConexao().prepareStatement(ALT);
            instrucao.setString(1, cliente.getNome());
            System.out.println(cliente.getNome());
            instrucao.setString(2, cliente.getEndereco());
            instrucao.setString(3, cliente.getTelefone());
            instrucao.setString(4, cliente.getEmail());
            instrucao.setString(5, cliente.getSenha());
            System.out.println(cliente.getSenha());
            instrucao.setString(6, cliente.getCpf());
            instrucao.executeUpdate();
            c.desconectar();
        } catch (Exception e) {
        	e.printStackTrace();
            System.out.println("Erro na alteração");
        }
    }

    public Clientes buscar(String cpf) {
        Clientes cliente = null;
        try {
            c.conectar();
            PreparedStatement instrucao = c.getConexao().prepareStatement(BUS);
            instrucao.setString(1, cpf);
            ResultSet rs = instrucao.executeQuery();
            if (rs.next()) {
                cliente = new Clientes(rs.getString("cpf"), 
                					   rs.getString("nome"), 
                					   rs.getString("endereco"),
                					   rs.getString("telefone"), 
                					   rs.getString("email"), 
                					   rs.getString("senha"));
            }
            c.desconectar();
        } catch (Exception e) {
            e.printStackTrace(); 
            System.out.println("Erro na busca");
        }
        return cliente;
    }
    
    
    public boolean buscarPorEmail(String email) {
    	boolean achou=false;
        try {
        	c.conectar();
            PreparedStatement instrucao = c.getConexao().prepareStatement(BUSEMAIL);
            instrucao.setString(1, email);
            ResultSet rs = instrucao.executeQuery();
            if (rs.next()){
            	achou=true;
            }
            c.desconectar();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro na busca por e-mail");
        }
            return achou;
       
   }

    public Clientes autentificacao(String cpf, String senha) {
        Clientes cliente = null;
        try {
            c.conectar();
            PreparedStatement instrucao = c.getConexao().prepareStatement(AUT);
            instrucao.setString(1, cpf);
            instrucao.setString(2, senha);
            ResultSet rs = instrucao.executeQuery();
            if (rs.next()) {
                cliente = new Clientes(rs.getString("cpf"), 
                					   rs.getString("nome"), 
                					   rs.getString("endereco"),
                					   rs.getString("telefone"), 
                					   rs.getString("email"), 
                					   rs.getString("senha"));
            }
            c.desconectar();
        } catch (Exception e) {
            e.printStackTrace(); 
            System.out.println("Erro na busca");
        }
        return cliente;
    }

    
    
    public ArrayList<Clientes> emitirRelatorio() {
        Clientes cliente;
        ArrayList<Clientes> lista = new ArrayList<>();
        try {
            c.conectar();
            Statement instrucao = c.getConexao().createStatement();
            ResultSet rs = instrucao.executeQuery(REL);
            while (rs.next()) {
                cliente = new Clientes(rs.getString("cpf"), rs.getString("nome"), rs.getString("endereco"),
                        rs.getString("telefone"), rs.getString("email"), rs.getString("senha"));
                lista.add(cliente);
            }
            c.desconectar();
        } catch (Exception e) {
            System.out.println("Erro no relatório");
        }
        return lista;
    }
}

