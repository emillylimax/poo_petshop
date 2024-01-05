package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import dominio.*;

public class PagamentosDAO {
    private Conexao c;
    private String REL = "SELECT * FROM Pagamentos";
    private String BUS = "SELECT * FROM Pagamentos WHERE id=?";
    private String INC = "INSERT INTO Pagamentos (valor_total, metodo_pagamento, agendamento_id) VALUES (?,?,?)";
    private String DEL = "DELETE FROM Pagamentos WHERE id = ?";
    private String ALT = "UPDATE Pagamentos SET valor_total = ?, metodo_pagamento= ?, agendamento_id= ? WHERE id = ?";
    private String BUS_POR_AGENDAMENTO = "SELECT * FROM Pagamentos WHERE agendamento_id=?";
    private String BUS_POR_METPAGAMENTO = "SELECT * FROM Pagamentos WHERE metodo_pagamento=?";

    public PagamentosDAO() {
        c = new Conexao("jdbc:postgresql://localhost:5432/ProjPetShop", "postgres", "123");
    }

    public void excluir(int id) {
        try {
            c.conectar();
            PreparedStatement instrucao = c.getConexao().prepareStatement(DEL);
            instrucao.setInt(1, id);
            instrucao.executeUpdate();
            c.desconectar();
        } catch (Exception e) {
            System.out.println("Erro na exclusão");
        }
    }

    public void incluir(Pagamentos pagamento) {
        try {
            c.conectar();
            PreparedStatement instrucao = c.getConexao().prepareStatement(INC);
            instrucao.setDouble(1, pagamento.getValorTotal());
            instrucao.setString(2, pagamento.getMetodoPagamento());
            instrucao.setInt(3, pagamento.getAgendamento().getId());
            instrucao.executeUpdate();
            c.desconectar();
        } catch (Exception e) {
            System.out.println("Erro na inclusão");
        }
    }

    public void alterar(Pagamentos pagamento) {
        try {
            c.conectar();
            PreparedStatement instrucao = c.getConexao().prepareStatement(ALT);
            instrucao.setDouble(1, pagamento.getValorTotal());
            instrucao.setString(2, pagamento.getMetodoPagamento());
            instrucao.setInt(3, pagamento.getAgendamento().getId());
            instrucao.setInt(4, pagamento.getId());
            instrucao.executeUpdate();
            c.desconectar();
        } catch (Exception e) {
            System.out.println("Erro na alteração");
        }
    }

    public Pagamentos buscar(int id) {
        Pagamentos pagamento = null;
        try {
            c.conectar();
            PreparedStatement instrucao = c.getConexao().prepareStatement(BUS);
            instrucao.setInt(1, id);
            ResultSet rs = instrucao.executeQuery();
            if (rs.next()) {
            	 int agendamentoIdResultado = rs.getInt("agendamento_id");
                 Agendamentos agendamento = new AgendamentosDAO().buscarPorId(agendamentoIdResultado);

                 pagamento = new Pagamentos(rs.getDouble("valor_total"), rs.getString("metodo_pagamento"), agendamento);
            }
            c.desconectar();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro na busca");
        }
        return pagamento;
    }

    public ArrayList<Pagamentos> emitirRelatorio() {
        Pagamentos pagamento;
        ArrayList<Pagamentos> lista = new ArrayList<>();
        try {
            c.conectar();
            Statement instrucao = c.getConexao().createStatement();
            ResultSet rs = instrucao.executeQuery(REL);
            while (rs.next()) {
            	int agendamentoIdResultado = rs.getInt("agendamento_id");
                Agendamentos agendamento = new AgendamentosDAO().buscarPorId(agendamentoIdResultado);

                pagamento = new Pagamentos(rs.getDouble("valor_total"), rs.getString("metodo_pagamento"), agendamento);
                lista.add(pagamento);
            }
            c.desconectar();
        } catch (Exception e) {
            System.out.println("Erro no relatório");
        }
        return lista;
    }
    
    public ArrayList<Pagamentos> buscarPorAgendamento(int agendamentoId) {
        ArrayList<Pagamentos> lista = new ArrayList<>();
        try {
            c.conectar();
            PreparedStatement instrucao = c.getConexao().prepareStatement(BUS_POR_AGENDAMENTO);
            instrucao.setInt(1, agendamentoId);
            ResultSet rs = instrucao.executeQuery();
            while (rs.next()) {
            	 int agendamentoIdResultado = rs.getInt("agendamento_id");
            	 Agendamentos agendamento = new AgendamentosDAO().buscarPorId(agendamentoIdResultado);

            	 Pagamentos pagamento = new Pagamentos(rs.getDouble("valor_total"), rs.getString("metodo_pagamento"), agendamento);
            	 lista.add(pagamento);
            }
            c.desconectar();
        } catch (Exception e) {
            System.out.println("Erro na busca por agendamento");
        }
        return lista;
    }

    public ArrayList<Pagamentos> buscarPorMetodoPagamento(String metodoPagamento) {
        ArrayList<Pagamentos> lista = new ArrayList<>();
        try {
            c.conectar();
            PreparedStatement instrucao = c.getConexao().prepareStatement(BUS_POR_METPAGAMENTO);
            instrucao.setString(1, metodoPagamento);
            ResultSet rs = instrucao.executeQuery();
            while (rs.next()) {
            	int agendamentoIdResultado = rs.getInt("agendamento_id");
                Agendamentos agendamento = new AgendamentosDAO().buscarPorId(agendamentoIdResultado);

                Pagamentos pagamento = new Pagamentos(rs.getDouble("valor_total"), rs.getString("metodo_pagamento"), agendamento);
                lista.add(pagamento);
            }
            c.desconectar();
        } catch (Exception e) {
            System.out.println("Erro na busca por método de pagamento");
        }
        return lista;
    }
}
