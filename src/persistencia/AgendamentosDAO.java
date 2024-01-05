package persistencia;

import dominio.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AgendamentosDAO {
    private Conexao c;
    private String REL = "SELECT id, data, hora, cliente_cpf, gato_id, cachorro_id FROM Agendamentos";
    private String BUSCA_POR_ID = "SELECT id, data, hora, cliente_cpf, gato_id, cachorro_id FROM Agendamentos WHERE id=?";
    private String BUSCA_POR_CLIENTE = "SELECT id, data, hora, cliente_cpf, gato_id, cachorro_id FROM Agendamentos WHERE cliente_cpf=?";
    private String BUSCA_POR_DATA = "SELECT id, data, hora, cliente_cpf, gato_id, cachorro_id FROM Agendamentos WHERE data=?";
    private String INSERIR = "INSERT INTO Agendamentos (data, hora, cliente_cpf, gato_id, cachorro_id) VALUES (?, ?, ?, ?, ?)";
    private String ATUALIZAR = "UPDATE Agendamentos SET data=?, hora=?, cliente_cpf=?, gato_id=?, cachorro_id=? WHERE id=?";
    private String EXCLUIR = "DELETE FROM Agendamentos WHERE id=?";

    public AgendamentosDAO() {
        c = new Conexao("jdbc:postgresql://localhost:5432/ProjPetShop", "postgres", "123");
    }

    public ArrayList<Agendamentos> listarAgendamentos() {
        ArrayList<Agendamentos> lista = new ArrayList<>();
        try {
            c.conectar();
            PreparedStatement instrucao = c.getConexao().prepareStatement(REL);
            ResultSet rs = instrucao.executeQuery();
            while (rs.next()) {
                String clienteCpf = rs.getString("cliente_cpf");
                int gatoId = rs.getInt("gato_id");
                int cachorroId = rs.getInt("cachorro_id");
                
                ClientesDAO clientesDAO = new ClientesDAO();
                Clientes clienteAssociado = clientesDAO.buscar(clienteCpf);

                GatosDAO gatosDAO = new GatosDAO();
                Gatos gatoAssociado = gatosDAO.buscar(gatoId);

                CachorrosDAO cachorrosDAO = new CachorrosDAO();
                Cachorros cachorroAssociado = cachorrosDAO.buscar(cachorroId);
                
                Agendamentos agendamento = new Agendamentos(
                    rs.getInt("id"),
                    rs.getString("data"),
                    rs.getString("hora"),
                    clienteAssociado,
                    gatoAssociado,
                    cachorroAssociado
                );

                lista.add(agendamento);
            }
            c.desconectar();
        } catch (Exception e) {
            System.out.println("Erro ao listar agendamentos");
        }
        return lista;
    }
    
    
    public Agendamentos buscarPorId(int id) {
        Agendamentos agendamento = null;
        try {
            c.conectar();
            PreparedStatement instrucao = c.getConexao().prepareStatement(BUSCA_POR_ID);
            instrucao.setInt(1, id);
            ResultSet rs = instrucao.executeQuery();
            if (rs.next()) {
                String clienteCpf = rs.getString("cliente_cpf");
                int gatoId = rs.getInt("gato_id");
                int cachorroId = rs.getInt("cachorro_id");
                
                ClientesDAO clientesDAO = new ClientesDAO();
                Clientes clienteAssociado = clientesDAO.buscar(clienteCpf);

                GatosDAO gatosDAO = new GatosDAO();
                Gatos gatoAssociado = gatosDAO.buscar(gatoId);

                CachorrosDAO cachorrosDAO = new CachorrosDAO();
                Cachorros cachorroAssociado = cachorrosDAO.buscar(cachorroId);

                agendamento = new Agendamentos(
                    rs.getInt("id"),
                    rs.getString("data"),
                    rs.getString("hora"),
                    clienteAssociado,
                    gatoAssociado,
                    cachorroAssociado
                );
            }
            c.desconectar();
        } catch (Exception e) {
            System.out.println("Erro ao buscar agendamento por ID");
        }
        return agendamento;
    }

    public ArrayList<Agendamentos> buscarPorCliente(Clientes cliente) {
        ArrayList<Agendamentos> lista = new ArrayList<>();
        try {
            c.conectar();
            PreparedStatement instrucao = c.getConexao().prepareStatement(BUSCA_POR_CLIENTE);
            instrucao.setString(1, cliente.getCpf());
            ResultSet rs = instrucao.executeQuery();
            while (rs.next()) {
                String clienteCpfResultado = rs.getString("cliente_cpf");
                int gatoId = rs.getInt("gato_id");
                int cachorroId = rs.getInt("cachorro_id");
                
                ClientesDAO clientesDAO = new ClientesDAO();
                Clientes clienteAssociado = clientesDAO.buscar(clienteCpfResultado);

                GatosDAO gatosDAO = new GatosDAO();
                Gatos gatoAssociado = gatosDAO.buscar(gatoId);

                CachorrosDAO cachorrosDAO = new CachorrosDAO();
                Cachorros cachorroAssociado = cachorrosDAO.buscar(cachorroId);

                Agendamentos agendamento = new Agendamentos(
                    rs.getInt("id"),
                    rs.getString("data"),
                    rs.getString("hora"),
                    clienteAssociado,
                    gatoAssociado,
                    cachorroAssociado
                );
                lista.add(agendamento);
            }
            c.desconectar();
        } catch (Exception e) {
        	e.printStackTrace();
            System.out.println("Erro ao buscar agendamentos por cliente");
        }
        return lista;
    }

    public ArrayList<Agendamentos> buscarPorData(String data) {
        ArrayList<Agendamentos> lista = new ArrayList<>();
        try {
            c.conectar();
            PreparedStatement instrucao = c.getConexao().prepareStatement(BUSCA_POR_DATA);
            instrucao.setString(1, data);
            ResultSet rs = instrucao.executeQuery();
            while (rs.next()) {
            	 String clienteCpf = rs.getString("cliente_cpf");
                 int gatoId = rs.getInt("gato_id");
                 int cachorroId = rs.getInt("cachorro_id");
                 
                 ClientesDAO clientesDAO = new ClientesDAO();
                 Clientes clienteAssociado = clientesDAO.buscar(clienteCpf);

                 GatosDAO gatosDAO = new GatosDAO();
                 Gatos gatoAssociado = gatosDAO.buscar(gatoId);

                 CachorrosDAO cachorrosDAO = new CachorrosDAO();
                 Cachorros cachorroAssociado = cachorrosDAO.buscar(cachorroId);

                 Agendamentos agendamento = new Agendamentos(
                     rs.getInt("id"),
                     rs.getString("data"),
                     rs.getString("hora"),
                     clienteAssociado,
                     gatoAssociado,
                     cachorroAssociado
                );
                lista.add(agendamento);
            }
            c.desconectar();
        } catch (Exception e) {
            System.out.println("Erro ao buscar agendamentos por data");
        }
        return lista;
    }

    public void criarAgendamento(Agendamentos agendamento) {
        try {
            c.conectar();
            PreparedStatement instrucao = c.getConexao().prepareStatement(INSERIR);
            instrucao.setString(1, (agendamento.getData()));
            instrucao.setString(2, (agendamento.getHora()));
            instrucao.setString(3, agendamento.getCliente().getCpf());
            instrucao.setInt(4, agendamento.getGato().getId());
            instrucao.setInt(5, agendamento.getCachorro().getId());
            instrucao.executeUpdate();
            c.desconectar();
        } catch (Exception e) {
            System.out.println("Erro ao criar agendamento");
        }
    }

    public void atualizarAgendamento(Agendamentos agendamento) {
        try {
            c.conectar();
            PreparedStatement instrucao = c.getConexao().prepareStatement(ATUALIZAR);
            instrucao.setString(1, (agendamento.getData()));
            instrucao.setString(2, (agendamento.getHora()));
            instrucao.setString(3, agendamento.getCliente().getCpf());
            instrucao.setInt(4, agendamento.getGato().getId());
            instrucao.setInt(5, agendamento.getCachorro().getId());
            instrucao.setInt(6, agendamento.getId());
            instrucao.executeUpdate();
            c.desconectar();
        } catch (Exception e) {
            System.out.println("Erro ao atualizar agendamento");
        }
    }

    public void excluirAgendamento(Agendamentos agendamento) {
        try {
            c.conectar();
            PreparedStatement instrucao = c.getConexao().prepareStatement(EXCLUIR);
            instrucao.setInt(1, agendamento.getId());
            instrucao.executeUpdate();
            c.desconectar();
        } catch (Exception e) {
            System.out.println("Erro ao excluir agendamento");
        }
    }

}
