package br.com.globalcode.dao.jdbc;

import br.com.globalcode.dao.ClienteDAO;
import br.com.globalcode.dao.DAOException;
import br.com.globalcode.dao.DAOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.globalcode.model.Cliente;

public class ClienteJDBC extends BaseDAOJDBC implements ClienteDAO {
    
    private static String SQL_INSERT_CLIENTE =
            "insert into clientes ( nome, email, senha, telefone, endereco_rua, endereco_numero, endereco_bairro, endereco_cep, " +
            "endereco_cidade, endereco_estado, ativo ) " +
            "values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
    
    private static String SQL_UPDATE_CLIENTE =
            "update clientes set nome = ?, email = ?, senha = ?, telefone = ?, endereco_rua = ?, endereco_numero = ?, " +
            "endereco_bairro = ?, endereco_cep = ?, endereco_cidade = ?, endereco_estado = ?, ativo = ? " +
            "where id = ?";
    
    private static String SQL_DELETE_CLIENTE = "delete from clientes where id = ? ";
    
    
    private static ClienteJDBC instance = new ClienteJDBC();
    
    protected ClienteJDBC(){
    }
    
    public static ClienteJDBC getInstance() {
        return instance;
    }
    
    protected List getManyByCriteria(String sql) throws DAOException {
        
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        List alista = new ArrayList();
        
        try {
            conn = getConnection();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            
            while (rs.next()) {
                
                boolean ativo = rs.getInt("ativo") == 1;
                
                Cliente cliente = new Cliente(
                        new Integer(rs.getInt("id")),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getString("telefone"),
                        ativo,
                        rs.getString("endereco_rua"),
                        rs.getString("endereco_numero"),
                        rs.getString("endereco_bairro"),
                        rs.getString("endereco_cep"),
                        rs.getString("endereco_cidade"),
                        rs.getString("endereco_estado"));
                alista.add(cliente);
                
            }
            
            return alista;
            
        } catch (Exception e) {
            throw new DAOException("[ClienteJDBC - getManyByCriteria] " + e.getMessage(), e);
            
        } finally {
            closeResources(conn, st, rs);
        }
        
    }
    
    public List getAll() throws DAOException{
        String sql = "select * from clientes ";
        return this.getManyByCriteria(sql);
    }
    
    public Cliente getByPrimaryKey(Integer id) throws DAOException{
        String sql = "select * from clientes where id = " + id;
        List lista = this.getManyByCriteria(sql);
        if (lista != null && lista.size() > 0)
            return (Cliente) lista.get(0);
        else
            return null;
    }
    
    public List getByNome(String nome) throws DAOException{
        String sql = "select * from clientes where nome like '" + nome + "%'";
        return this.getManyByCriteria(sql);
    }
    
    public Cliente getByEmail(String email) throws DAOException{
        String sql = "select * from clientes where email = '" + email +"'";
        List lista = this.getManyByCriteria(sql);
        if (lista != null && lista.size() > 0)
            return (Cliente) lista.get(0);
        else
            return null;
    }
    
    public void delete(Cliente cliente) throws DAOException{
        
        Connection conn = null;
        PreparedStatement pst = null;
        
        try {
            conn = getConnection();
            pst = conn.prepareStatement(SQL_DELETE_CLIENTE);
            
            pst.setInt(1, cliente.getId().intValue());
            
            pst.executeUpdate();
            
        } catch (Exception e) {
            throw new DAOException("[ClienteJDBC - delete] " + e.getMessage(), e);
            
        } finally {
            closeResources(conn, pst);
        }
    }
    
    public Cliente save(Cliente cliente) throws DAOException{
        
        Connection conn = null;
        PreparedStatement pst = null;
        
        try {
            conn = getConnection();
            if (cliente.getId() == null) {
                System.out.println("[ClienteJDBC] novo cliente vai ser incluido...");
                pst = conn.prepareStatement(SQL_INSERT_CLIENTE);
            } else {
                System.out.println("[ClienteJDBC] cliente ja existente vai ser regravado...");
                pst = conn.prepareStatement(SQL_UPDATE_CLIENTE);
                pst.setInt   (12, cliente.getId().intValue());                
            }
            
            pst.setString(1, cliente.getNome());
            pst.setString(2, cliente.getEmail());
            pst.setString(3, cliente.getSenha());
            pst.setString(4, cliente.getTelefone());
            pst.setString(5, cliente.getEnderecoRua());
            pst.setString(6, cliente.getEnderecoNumero());
            pst.setString(7, cliente.getEnderecoBairro());
            pst.setString(8, cliente.getEnderecoCep());
            pst.setString(9, cliente.getEnderecoCidade());
            pst.setString(10, cliente.getEnderecoEstado());
            pst.setInt   (11, cliente.isAtivo()?1:0);
            
            pst.execute();
            
            //Recupera a chave primaria gerada no caso de uma insercao
            if(cliente.getId()==null) {
                ResultSet rs = pst.getGeneratedKeys();
                if(rs.next()) {
                    Integer idGerado = new Integer(rs.getInt(1));
                    cliente.setId(idGerado);
                }
            }
            
            return cliente;
            
        } catch (Exception e) {
            throw new DAOException("[ClienteJDBC - save] " + e.getMessage(), e);
            
        } finally {
            closeResources(conn, pst);
        }
        
    }
    
}

