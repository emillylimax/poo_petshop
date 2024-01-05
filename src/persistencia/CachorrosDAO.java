package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import dominio.*;

public class CachorrosDAO {
    private Conexao c;
    private String REL = "SELECT * FROM Cachorros";
    private String BUS = "SELECT * FROM Cachorros WHERE id=?";
    private String INC = "INSERT INTO Cachorros (nome, idade, cor, peso, raca, porte, historico_saude, cliente_cpf) VALUES (?,?,?,?,?,?,?,?)";
    private String DEL = "DELETE FROM Cachorros WHERE id = ?";
    private String ALT = "UPDATE Cachorros SET nome=?, idade=?, cor=?, peso=?, raca=?, porte=?, historico_saude=?, cliente_cpf=? WHERE id=?";
    private String REL_CLIENTE = "SELECT * FROM Cachorros WHERE cliente_cpf=?";
    private String BUS_NOME = "SELECT * FROM Cachorros WHERE nome=?";
    private String BUS_ID=  "SELECT id FROM Cachorros WHERE nome = ? AND cliente_cpf = ?";

    public CachorrosDAO() {
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
            System.out.println("Erro na exclusão do Cachorro");
        }
    }

    public void incluir(Cachorros Cachorro) {
        try {
            c.conectar();
            PreparedStatement instrucao = c.getConexao().prepareStatement(INC);
            instrucao.setString(1, Cachorro.getNome());
            instrucao.setInt(2, Cachorro.getIdade());
            instrucao.setString(3, Cachorro.getCor());
            instrucao.setDouble(4, Cachorro.getPeso());
            instrucao.setString(5, Cachorro.getRaca());
            instrucao.setString(6, Cachorro.getPorte());
            instrucao.setString(7, Cachorro.getHistoricoSaude());
            instrucao.setString(8, Cachorro.getCliente().getCpf());
            instrucao.executeUpdate();
            
            c.desconectar();
        } catch (Exception e) {
        	e.printStackTrace();
            System.out.println("Erro na inclusão do Cachorro");
        }
    }

    public void alterar(Cachorros Cachorro) {
        try {
            c.conectar();
            PreparedStatement instrucao = c.getConexao().prepareStatement(ALT);
            instrucao.setString(1, Cachorro.getNome());
            instrucao.setInt(2, Cachorro.getIdade());
            instrucao.setString(3, Cachorro.getCor());
            instrucao.setDouble(4, Cachorro.getPeso());
            instrucao.setString(5, Cachorro.getRaca());
            instrucao.setString(6, Cachorro.getPorte());
            instrucao.setString(7, Cachorro.getHistoricoSaude());
            instrucao.setString(8, Cachorro.getCliente().getCpf());
            instrucao.setInt(9, Cachorro.getId());
            instrucao.executeUpdate();
            c.desconectar();
        } catch (Exception e) {
            System.out.println("Erro na alteração do Cachorro");
        }
    }

    public Cachorros buscar(int id) {
        Cachorros cachorro = null;
        try {
            c.conectar();
            PreparedStatement instrucao = c.getConexao().prepareStatement(BUS);
            instrucao.setInt(1, id);
            ResultSet rs = instrucao.executeQuery();
            if (rs.next()) {
                String cpfCliente = rs.getString("cliente_cpf");

                ClientesDAO clientesDAO = new ClientesDAO();
                Clientes clienteAssociado = clientesDAO.buscar(cpfCliente);

                cachorro = new Cachorros(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getInt("idade"),
                    rs.getString("cor"),
                    rs.getDouble("peso"),
                    rs.getString("raca"),
                    rs.getString("porte"),
                    rs.getString("historico_saude"),
                    clienteAssociado
                );
            }
            c.desconectar();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro na busca do Cachorro");
        }
        return cachorro;
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
            System.out.println("Erro ao buscar ID do cachorro por nome");
        }

        return id;
    }


    public ArrayList<Cachorros> listarCachorros() {
        ArrayList<Cachorros> listaCachorros = new ArrayList<>();
        try {
            c.conectar();
            Statement instrucao = c.getConexao().createStatement();
            ResultSet rs = instrucao.executeQuery(REL);
            while (rs.next()) {
                String cpfCliente = rs.getString("cliente_cpf");
                ClientesDAO clientesDAO = new ClientesDAO();
                Clientes clienteAssociado = clientesDAO.buscar(cpfCliente);

                Cachorros cachorro = new Cachorros(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getInt("idade"),
                    rs.getString("cor"),
                    rs.getDouble("peso"),
                    rs.getString("raca"),
                    rs.getString("porte"),
                    rs.getString("historico_saude"),
                    clienteAssociado
                );
                
                listaCachorros.add(cachorro);
            }
            c.desconectar();
        } catch (Exception e) {
            System.out.println("Erro na listagem de Cachorros");
        }
        return listaCachorros;
    }
    

    public ArrayList<Cachorros> listarCachorrosPorCliente(String cpfCliente) {
        ArrayList<Cachorros> listaCachorros = new ArrayList<>();
        try {
            c.conectar();
            PreparedStatement instrucao = c.getConexao().prepareStatement(REL_CLIENTE);
            instrucao.setString(1, cpfCliente);
            ResultSet rs = instrucao.executeQuery();
            while (rs.next()) {
                String clienteCpf = rs.getString("cliente_cpf");

                ClientesDAO clientesDAO = new ClientesDAO();
                Clientes clienteAssociado = clientesDAO.buscar(clienteCpf);

                Cachorros cachorro = new Cachorros(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getInt("idade"),
                    rs.getString("cor"),
                    rs.getDouble("peso"),
                    rs.getString("raca"),
                    rs.getString("porte"),
                    rs.getString("historico_saude"),
                    clienteAssociado
                );
                
                listaCachorros.add(cachorro);
            }
            c.desconectar();
        } catch (Exception e) {
            System.out.println("Erro ao listar Cachorros por cliente");
        }
        return listaCachorros;
    }

    public Cachorros buscarPorNome(String nome) {
        Cachorros cachorro = null;
        try {
            c.conectar();
            PreparedStatement instrucao = c.getConexao().prepareStatement(BUS_NOME);
            instrucao.setString(1, nome);
            ResultSet rs = instrucao.executeQuery();
            if (rs.next()) {
                String clienteCpf = rs.getString("cliente_cpf");

                ClientesDAO clientesDAO = new ClientesDAO();
                Clientes clienteAssociado = clientesDAO.buscar(clienteCpf);

                cachorro = new Cachorros(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getInt("idade"),
                    rs.getString("cor"),
                    rs.getDouble("peso"),
                    rs.getString("raca"),
                    rs.getString("porte"),
                    rs.getString("historico_saude"),
                    clienteAssociado
                );
            }
            c.desconectar();
        } catch (Exception e) {
            System.out.println("Erro na busca do Cachorro por nome");
        }
        return cachorro;
    }
    
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Cachorros buscarPorCaracteristicas(String nome, int idade, String cor, double peso,
                                             String raca, String porte, String histSaude, Clientes cliente) {
        Cachorros cachorroEncontrado = null;
        try {
            c.conectar();
            PreparedStatement instrucao = c.getConexao().prepareStatement(REL + " WHERE nome=? AND idade=? AND cor=? AND peso=? AND raca=? AND porte=? AND historico_saude=? AND cliente_cpf=?");
            instrucao.setString(1, nome);
            instrucao.setInt(2, idade);
            instrucao.setString(3, cor);
            instrucao.setDouble(4, peso);
            instrucao.setString(5, raca);
            instrucao.setString(6, porte);
            instrucao.setString(7, histSaude);
            instrucao.setString(8, cliente.getCpf());

            ResultSet rs = instrucao.executeQuery();
            if (rs.next()) {
                ClientesDAO clientesDAO = new ClientesDAO();
                Clientes clienteAssociado = clientesDAO.buscar(cliente.getCpf());

                cachorroEncontrado = new Cachorros(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getInt("idade"),
                    rs.getString("cor"),
                    rs.getDouble("peso"),
                    rs.getString("raca"),
                    rs.getString("porte"),
                    rs.getString("historico_saude"),
                    clienteAssociado
                );
            }
            c.desconectar();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro na busca por características semelhantes");
        }
        return cachorroEncontrado;
    }
    
    ///////////////////////////////////////////////////////////////////////////////
    public Cachorros buscarCachorroPorNomeECliente(String nome, Clientes cliente) {
        Cachorros cachorro = null;
        try {
            c.conectar();
            String sql = "SELECT * FROM cachorros WHERE nome = ? AND cliente_cpf = ?";
            PreparedStatement instrucao = c.getConexao().prepareStatement(sql);
            instrucao.setString(1, nome);
            instrucao.setString(2, cliente.getCpf());
            ResultSet rs = instrucao.executeQuery();
            if (rs.next()) {
                cachorro = new Cachorros(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getInt("idade"),
                        rs.getString("cor"),
                        rs.getDouble("peso"),
                        rs.getString("raca"),
                        rs.getString("porte"),
                        rs.getString("historico_saude"),
                        cliente
                );
            }
            c.desconectar();
        } catch (Exception e) {
            System.out.println("Erro na busca do cachorro por nome e cliente");
            e.printStackTrace();
        }
        return cachorro;
    }

}