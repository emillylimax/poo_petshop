package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import dominio.*;

public class GatosDAO {
    private Conexao c;
    private String REL = "SELECT * FROM Gatos";
    private String BUS = "SELECT * FROM Gatos WHERE id=?";
    private String INC = "INSERT INTO Gatos (nome, idade, cor, peso, tipo_pelo, historico_saude, cliente_cpf) VALUES (?,?,?,?,?,?,?)";
    private String DEL = "DELETE FROM Gatos WHERE id = ?";
    private String ALT = "UPDATE Gatos SET nome=?, idade=?, cor=?, peso=?, tipo_pelo=?, historico_saude=?, cliente_cpf=? WHERE id=?";
    private String REL_CLIENTE = "SELECT * FROM Gatos WHERE cliente_cpf=?";
    private String BUS_NOME = "SELECT * FROM Gatos WHERE nome=?";
    private String BUS_ID=  "SELECT id FROM Gatos WHERE nome = ? AND cliente_cpf = ?";
    private String BUS_NOME_E_CLIENTE = "SELECT * FROM gatos WHERE nome = ? AND cliente_cpf = ?";

    public GatosDAO() {
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
            System.out.println("Erro na exclusão do gato");
        }
    }

    public void incluir(Gatos gato) {
        try {
            c.conectar();
            PreparedStatement instrucao = c.getConexao().prepareStatement(INC);
            instrucao.setString(1, gato.getNome());
            instrucao.setInt(2, gato.getIdade());
            instrucao.setString(3, gato.getCor());
            instrucao.setDouble(4, gato.getPeso());
            instrucao.setString(5, gato.getTipoPelo());
            instrucao.setString(6, gato.getHistoricoSaude());
            instrucao.setString(7, gato.getCliente().getCpf());
            instrucao.executeUpdate();

            c.desconectar();
        } catch (Exception e) {
            System.out.println("Erro na inclusão do gato");
        }
    }

    public void alterar(Gatos gato) {
        try {
            c.conectar();
            PreparedStatement instrucao = c.getConexao().prepareStatement(ALT);
            instrucao.setString(1, gato.getNome());
            instrucao.setInt(2, gato.getIdade());
            instrucao.setString(3, gato.getCor());
            instrucao.setDouble(4, gato.getPeso());
            instrucao.setString(5, gato.getTipoPelo());
            instrucao.setString(6, gato.getHistoricoSaude());
            instrucao.setString(7, gato.getCliente().getCpf());
            instrucao.setInt(8, gato.getId());
            instrucao.executeUpdate();
            c.desconectar();
        } catch (Exception e) {
            System.out.println("Erro na alteração do gato");
        }
    }

    public Gatos buscar(int id) {
        Gatos gato = null;
        try {
            c.conectar();
            PreparedStatement instrucao = c.getConexao().prepareStatement(BUS);
            instrucao.setInt(1, id);
            ResultSet rs = instrucao.executeQuery();
            if (rs.next()) {
                String clienteCpf = rs.getString("cliente_cpf");
                ClientesDAO clientesDAO = new ClientesDAO();
                Clientes clienteAssociado = clientesDAO.buscar(clienteCpf);

                gato = new Gatos(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getInt("idade"),
                    rs.getString("cor"),
                    rs.getDouble("peso"),
                    rs.getString("tipo_pelo"),
                    rs.getString("historico_saude"),
                    clienteAssociado
                );
            }
            c.desconectar();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro na busca do gato");
        }
        return gato;
    }
    public int buscarIdPorNome(String nome, String cpfClienteLogado) {
        int id = -1; //

        try {
            c.conectar();
            PreparedStatement instrucao = c.getConexao().prepareStatement(BUS_ID);
            instrucao.setString(1, nome);
            instrucao.setString(2, cpfClienteLogado);
            ResultSet rs = instrucao.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id");
            }

            c.desconectar();
        } catch (Exception e) {
            System.out.println("Erro ao buscar ID do gato por nome");
        }

        return id;
    }

    public ArrayList<Gatos> listarGatos() {
        ArrayList<Gatos> listaGatos = new ArrayList<>();
        try {
            c.conectar();
            Statement instrucao = c.getConexao().createStatement();
            ResultSet rs = instrucao.executeQuery(REL);
            while (rs.next()) {
                String clienteCpf = rs.getString("cliente_cpf");

                ClientesDAO clientesDAO = new ClientesDAO();
                Clientes clienteAssociado = clientesDAO.buscar(clienteCpf);

                Gatos gato = new Gatos(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getInt("idade"),
                    rs.getString("cor"),
                    rs.getDouble("peso"),
                    rs.getString("tipo_pelo"),
                    rs.getString("historico_saude"),
                    clienteAssociado
                );
                
                listaGatos.add(gato);
            }
            c.desconectar();
        } catch (Exception e) {
            System.out.println("Erro na listagem de gatos");
        }
        return listaGatos;
    }

    public ArrayList<Gatos> listarGatosPorCliente(String cpfCliente) {
        ArrayList<Gatos> listaGatos = new ArrayList<>();
        try {
            c.conectar();
            PreparedStatement instrucao = c.getConexao().prepareStatement(REL_CLIENTE);
            instrucao.setString(1, cpfCliente);
            ResultSet rs = instrucao.executeQuery();
            while (rs.next()) {
                String clienteCpf = rs.getString("cliente_cpf");
                ClientesDAO clientesDAO = new ClientesDAO();
                Clientes clienteAssociado = clientesDAO.buscar(clienteCpf);

                Gatos gato = new Gatos(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getInt("idade"),
                    rs.getString("cor"),
                    rs.getDouble("peso"),
                    rs.getString("tipo_pelo"),
                    rs.getString("historico_saude"),
                    clienteAssociado
                );
                
                listaGatos.add(gato);
            }
            c.desconectar();
        } catch (Exception e) {
            System.out.println("Erro ao listar gatos por cliente");
        }
        return listaGatos;
    }

    public Gatos buscarPorNome(String nome) {
        Gatos gato = null;
        try {
            c.conectar();
            PreparedStatement instrucao = c.getConexao().prepareStatement(BUS_NOME);
            instrucao.setString(1, nome);
            ResultSet rs = instrucao.executeQuery();
            if (rs.next()) {
                String clienteCpf = rs.getString("cliente_cpf");
                ClientesDAO clientesDAO = new ClientesDAO();
                Clientes clienteAssociado = clientesDAO.buscar(clienteCpf);
                gato = new Gatos(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getInt("idade"),
                    rs.getString("cor"),
                    rs.getDouble("peso"),
                    rs.getString("tipo_pelo"),
                    rs.getString("historico_saude"),
                    clienteAssociado
                );
            }
            c.desconectar();
        } catch (Exception e) {
            System.out.println("Erro na busca do gato por nome");
        }
        return gato;
    }
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Gatos buscarPorCaracteristicas(String nome, int idade, String cor, double peso, 
            String tipoPelo, String histSaude, Clientes cliente) {
    	Gatos gatoEncontrado = null;
    	try {
            c.conectar();
            PreparedStatement instrucao = c.getConexao().prepareStatement(REL + " WHERE nome=? AND idade=? AND cor=? AND peso=? AND tipo_pelo=? AND historico_saude=? AND cliente_cpf=?");
            instrucao.setString(1, nome);
            instrucao.setInt(2, idade);
            instrucao.setString(3, cor);
            instrucao.setDouble(4, peso);
            instrucao.setString(5, tipoPelo);
            instrucao.setString(6, histSaude);
            instrucao.setString(7, cliente.getCpf());

            ResultSet rs = instrucao.executeQuery();
            if (rs.next()) {
                ClientesDAO clientesDAO = new ClientesDAO();
                Clientes clienteAssociado = clientesDAO.buscar(cliente.getCpf());

                gatoEncontrado = new Gatos(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getInt("idade"),
                    rs.getString("cor"),
                    rs.getDouble("peso"),
                    rs.getString("tipo_pelo"),
                    rs.getString("historico_saude"),
                    clienteAssociado
                );
            }
            c.desconectar();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro na busca por características semelhantes");
        }
        return gatoEncontrado;
    }
    
    public Gatos buscarGatoPorNomeECliente(String nome, Clientes cliente) {
        Gatos gato = null;
        try {
            c.conectar();
            PreparedStatement instrucao = c.getConexao().prepareStatement(BUS_NOME_E_CLIENTE);
            instrucao.setString(1, nome);
            instrucao.setString(2, cliente.getCpf());
            ResultSet rs = instrucao.executeQuery();
            if (rs.next()) {
                gato = new Gatos(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getInt("idade"),
                    rs.getString("cor"),
                    rs.getDouble("peso"),
                    rs.getString("tipo_pelo"),
                    rs.getString("historico_saude"),
                    cliente
                );
            }
            c.desconectar();
        } catch (Exception e) {
            System.out.println("Erro na busca do gato por nome e cliente");
            e.printStackTrace();
        }
        return gato;
    }
    
}
