package br.com.globalcode.dao.jdbc;

import br.com.globalcode.dao.CategoriaDAO;
import br.com.globalcode.dao.DAOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.globalcode.model.Categoria;

public class CategoriaJDBC extends BaseDAOJDBC implements CategoriaDAO {
    
    private static String SQL_INSERT_CATEGORIA =
            "insert into categorias ( descricao, ativo ) values ( ?, ? )";
    
    private static String SQL_UPDATE_CATEGORIA =
            "update categorias set descricao = ?, ativo = ? where id = ?";
    
    private static String SQL_DELETE_CATEGORIA = "delete from categorias where id = ? ";
    
    
    private static CategoriaJDBC instance = new CategoriaJDBC();
    
    protected CategoriaJDBC(){
    }
    
    public static CategoriaJDBC getInstance() {
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
                
                Categoria categoria = new Categoria(
                        new Integer(rs.getInt("id")),
                        rs.getString("descricao"),
                        ativo);
                alista.add(categoria);
                
            }
            
            return alista;
            
        } catch (Exception e) {
            throw new DAOException("[CategoriaJDBC - getManyByCriteria] : Handled Exception  ", e);
            
        } finally {
            closeResources(conn, st, rs);
        }
        
    }
    
    public List getAll() throws DAOException{
        String sql = "select * from categorias ";
        return this.getManyByCriteria(sql);
    }
    
    public Categoria getByPrimaryKey(Integer id) throws DAOException{
        String sql = "select * from categorias where id = " + id;
        List lista = this.getManyByCriteria(sql);
        if (lista != null && lista.size() > 0)
            return (Categoria) lista.get(0);
        else
            return null;
    }
    
    public void delete(Categoria categoria) throws DAOException{
        
        
        Connection conn = null;
        PreparedStatement pst = null;
        
        try {
            conn = getConnection();
            pst = conn.prepareStatement(SQL_DELETE_CATEGORIA);
            
            pst.setInt(1, categoria.getId().intValue());
            
            pst.executeUpdate();
            
        } catch (Exception e) {
            throw new DAOException("[CategoriaJDBC - delete] : Handled Exception  ", e);
            
        } finally {
            closeResources(conn, pst);
        }
    }
    
    public void save(Categoria categoria) throws DAOException{
        Connection conn = null;
        PreparedStatement pst = null;
        
        try {
            conn = getConnection();
            if (categoria.getId() == null) {
                System.out.println("[CategoriaJDBC] nova categoria vai ser incluida...");
                pst = conn.prepareStatement(SQL_INSERT_CATEGORIA);
            } else {
                System.out.println("[CategoriaJDBC] categoria ja existente vai ser regravada...");
                pst = conn.prepareStatement(SQL_UPDATE_CATEGORIA);
                pst.setInt (3, categoria.getId().intValue());
            }
            
            pst.setString(1, categoria.getDescricao());
            pst.setInt   (2, categoria.isAtivo()?1:0);
            
            pst.execute();
            
        } catch (Exception e) {
            throw new DAOException("[CategoriaJDBC - save] : Handled Exception  ", e);
            
        } finally {
            closeResources(conn, pst);
        }
        
    }
    
}
