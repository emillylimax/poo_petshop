package persistencia;

import dominio.Servicos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ServicosDAO {
    private Conexao c;
    private String REL = "SELECT nome, descricao, preco FROM Servicos";
    private String BUS = "SELECT nome, descricao, preco FROM Servicos WHERE id=?";
    private String BUSCA_ID= "SELECT id FROM Servicos WHERE nome=?";
    private String SERV_NOME="SELECT * FROM Servicos WHERE nome = ?";

    public ServicosDAO() {
        c = new Conexao("jdbc:postgresql://localhost:5432/ProjPetShop", "postgres", "123");
    }

    public ArrayList<Servicos> listarServicos() {
        ArrayList<Servicos> lista = new ArrayList<>();
        try {
            c.conectar();
            PreparedStatement instrucao = c.getConexao().prepareStatement(REL);
            ResultSet rs = instrucao.executeQuery();
            while (rs.next()) {
                Servicos servico = new Servicos(
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getDouble("preco")
                );
                lista.add(servico);
            }
            c.desconectar();
        } catch (Exception e) {
            System.out.println("Erro ao listar serviços");
        }
        return lista;
    }
    
    public Servicos buscarPorId(int id) {
        Servicos servico = null;
        try {
            c.conectar();
            PreparedStatement instrucao = c.getConexao().prepareStatement(BUS);
            instrucao.setInt(1, id);
            ResultSet rs = instrucao.executeQuery();
            if (rs.next()) {
                servico = new Servicos(
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getDouble("preco")
                );
            }
            c.desconectar();
        } catch (Exception e) {
            System.out.println("Erro ao buscar serviço por ID");
        }
        return servico;
    }
    
    public int obterIdPorNome(String nome) {
        int id = -1; // Valor padrão para indicar que o serviço não foi encontrado
        try {
            c.conectar();
            PreparedStatement instrucao = c.getConexao().prepareStatement(BUSCA_ID);
            instrucao.setString(1, nome);
            ResultSet rs = instrucao.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
            c.desconectar();
        } catch (Exception e) {
            System.out.println("Erro ao obter ID do serviço por nome");
        }
        return id;
    }
    
    public Servicos obterServicoPorNome(String nome) {
        Servicos servico = null;
        try {
            c.conectar();
            PreparedStatement instrucao = c.getConexao().prepareStatement(SERV_NOME);
            instrucao.setString(1, nome);
            ResultSet rs = instrucao.executeQuery();
            if (rs.next()) {
                servico = new Servicos(
                    rs.getString("nome"),
                    rs.getString("descricao"),
                    rs.getDouble("preco")
                );
            }
            c.desconectar();
        } catch (Exception e) {
            System.out.println("Erro ao obter serviço por nome");
        }
        return servico;
    }
}


