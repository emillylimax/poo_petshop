package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import dominio.*;

public class Servicos_agendadosDAO {
    private Conexao c;
    private String BUS = "SELECT * FROM Servicos_agendados WHERE agendamento_id=?";
    private String INC = "INSERT INTO Servicos_agendados (agendamento_id, servico_id) VALUES (?,?)";
    private String DEL = "DELETE FROM Servicos_agendados WHERE id=?";
    private String BUS_POR_CLIENTE = "SELECT sa.* FROM Servicos_agendados sa JOIN Agendamentos a ON sa.agendamento_id = a.id JOIN Clientes c ON a.cliente_id = c.id WHERE c.cpf=?";
    private String BUS_POR_SERVICO ="SELECT sa.* FROM Servicos_agendados sa JOIN Servicos s ON sa.servico_id = s.id WHERE s.nome=?";

    
    public Servicos_agendadosDAO() {
        c = new Conexao("jdbc:postgresql://localhost:5432/ProjPetShop", "postgres", "123");
    }

    public void associarServicoAgendamento(Servicos_agendados servicosAgendados) {
        try {
            c.conectar();
            PreparedStatement instrucao = c.getConexao().prepareStatement(INC);
            instrucao.setInt(1, servicosAgendados.getAgendamento().getId());
            instrucao.setInt(2, servicosAgendados.getServico().getId());
            instrucao.executeUpdate();
            c.desconectar();
        } catch (Exception e) {
            System.out.println("Erro ao associar serviço ao agendamento");
            e.printStackTrace();
        }
    }

    public ArrayList<Servicos_agendados> buscarPorAgendamento(int agendamentoId) {
        ArrayList<Servicos_agendados> lista = new ArrayList<>();
        try {
            c.conectar();
            PreparedStatement instrucao = c.getConexao().prepareStatement(BUS);
            instrucao.setInt(1, agendamentoId);
            ResultSet rs = instrucao.executeQuery();
            while (rs.next()) {
            	int agendamentoIdResultado = rs.getInt("agendamento_id");
                Agendamentos agendamento = new AgendamentosDAO().buscarPorId(agendamentoIdResultado);

                int servicoIdResultado = rs.getInt("servico_id");
                Servicos servico = new ServicosDAO().buscarPorId(servicoIdResultado);

                Servicos_agendados servicosAgendados = new Servicos_agendados(agendamento, servico);
                lista.add(servicosAgendados);
            }
            c.desconectar();
        } catch (Exception e) {
            System.out.println("Erro na busca por agendamento");
            e.printStackTrace();
        }
        return lista;
    }

    public void excluir(int servicosAgendadosId) {
        try {
            c.conectar();
            PreparedStatement instrucao = c.getConexao().prepareStatement(DEL);
            instrucao.setInt(1, servicosAgendadosId);
            instrucao.executeUpdate();
            c.desconectar();
        } catch (Exception e) {
            System.out.println("Erro na exclusão");
            e.printStackTrace();
        }
    }

    public ArrayList<Servicos_agendados> buscarPorCliente(String cpfCliente) {
        ArrayList<Servicos_agendados> lista = new ArrayList<>();
        try {
            c.conectar();
            PreparedStatement instrucao = c.getConexao().prepareStatement(BUS_POR_CLIENTE);
            instrucao.setString(1, cpfCliente);
            ResultSet rs = instrucao.executeQuery();
            while (rs.next()) {
            	int agendamentoIdResultado = rs.getInt("agendamento_id");
                Agendamentos agendamento = new AgendamentosDAO().buscarPorId(agendamentoIdResultado);

                int servicoIdResultado = rs.getInt("servico_id");
                Servicos servico = new ServicosDAO().buscarPorId(servicoIdResultado);

                Servicos_agendados servicosAgendados = new Servicos_agendados(agendamento, servico);
                lista.add(servicosAgendados);
            }
            c.desconectar();
        } catch (Exception e) {
            System.out.println("Erro na busca por cliente");
            e.printStackTrace();
        }
        return lista;
    }

    public ArrayList<Servicos_agendados> buscarPorServico(String nomeServico) {
        ArrayList<Servicos_agendados> lista = new ArrayList<>();
        try {
            c.conectar();
            PreparedStatement instrucao = c.getConexao().prepareStatement(BUS_POR_SERVICO);
            instrucao.setString(1, nomeServico);
            ResultSet rs = instrucao.executeQuery();
            while (rs.next()) {
            	int agendamentoIdResultado = rs.getInt("agendamento_id");
                Agendamentos agendamento = new AgendamentosDAO().buscarPorId(agendamentoIdResultado);

                int servicoIdResultado = rs.getInt("servico_id");
                Servicos servico = new ServicosDAO().buscarPorId(servicoIdResultado);

                Servicos_agendados servicosAgendados = new Servicos_agendados(agendamento, servico);
                lista.add(servicosAgendados);
            }
            c.desconectar();
        } catch (Exception e) {
            System.out.println("Erro na busca por serviço");
            e.printStackTrace();
        }
        return lista;
    }

}