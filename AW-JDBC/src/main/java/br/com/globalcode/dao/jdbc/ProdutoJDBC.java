package br.com.globalcode.dao.jdbc;

import br.com.globalcode.dao.DAOException;
import br.com.globalcode.dao.ProdutoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.globalcode.model.Categoria;
import br.com.globalcode.model.Produto;
import br.com.globalcode.model.ProdutoDigital;
import br.com.globalcode.model.ProdutoMaterial;
import java.sql.SQLException;

public class ProdutoJDBC extends BaseDAOJDBC implements ProdutoDAO {
    
    private static String SQL_INSERT_PRODUTO =
            "insert into produtos ( nome, preco, marca, id_categoria, ativo, tipo_produto, taxa_entrega, nome_arquivo, "+
            "tamanho_arquivo ) " +
            "values ( ?, ?, ?, ?, ?, ?, ?, ?, ? )";
    
    private static String SQL_UPDATE_PRODUTO =
            "update produtos set nome = ?, preco = ?, marca = ?, id_categoria = ?, ativo = ?, tipo_produto = ?, " +
            "taxa_entrega = ?, nome_arquivo = ?, tamanho_arquivo = ? " +
            "where id = ?";
    
    private static String SQL_DELETE_PRODUTO = "delete from produtos where id = ? ";
    
    
    private static ProdutoJDBC instance = new ProdutoJDBC();
    
    protected ProdutoJDBC(){
        
    }
    
    public static ProdutoJDBC getInstance() {
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
                
                boolean ativo = rs.getInt("p.ativo") == 1;
                char tipoProduto = rs.getString("p.tipo_produto").charAt(0);
                
                Categoria categoria = getCategoria(rs);
                
                Produto produto = null;
                
                switch (tipoProduto){
                    case 'D': {
                        produto = new ProdutoDigital(
                                new Integer(rs.getInt("p.id")),
                                categoria,
                                rs.getString("p.marca"),
                                rs.getString("p.nome"),
                                rs.getDouble("p.preco"),
                                ativo,
                                rs.getString("p.nome_arquivo"),
                                rs.getInt("p.tamanho_arquivo"));
                        break;
                    }
                    case 'M': {
                        produto = new ProdutoMaterial(
                                new Integer(rs.getInt("p.id")),
                                categoria,
                                rs.getString("p.marca"),
                                rs.getString("p.nome"),
                                rs.getDouble("p.preco"),
                                ativo,
                                rs.getDouble("p.taxa_entrega"));
                        break;
                    }
                }
                alista.add(produto);
                
            }
            
            return alista;
            
        } catch (Exception e) {
            throw new DAOException("[ProdutoJDBC - getManyByCriteria] : Handled Exception  ", e);
            
        } finally {
            closeResources(conn, st, rs);
        }
        
    }
    
    private Categoria getCategoria(ResultSet rs) throws SQLException{
        boolean ativo = rs.getInt("c.ativo") == 1;
        Categoria categoria = new Categoria(new Integer(rs.getInt("c.id")),
                                            rs.getString("c.descricao"),
                                            ativo);
        return categoria;
    }

    public List getAll() throws DAOException{
        String sql = "select * from produtos p " +
                "join categorias c on p.id_categoria = c.id";
        return this.getManyByCriteria(sql);
    }
    
    public Produto getByPrimaryKey(Integer id) throws DAOException{
        String sql = "select * from produtos p " +
                "join categorias c on p.id_categoria = c.id " +
                "where p.id = " + id.intValue();
        List lista = this.getManyByCriteria(sql);
        if (lista != null && lista.size() > 0)
            return (Produto) lista.get(0);
        else
            return null;
    }
    
    public List getByMarca(String marca) throws DAOException{
        String sql = "select * from produtos p " +
                "join categorias c on p.id_categoria = c.id " +
                "where p.marca like '" + marca + "%'";
        return this.getManyByCriteria(sql);
    }
    
    public List getByCategoria(Categoria categoria) throws DAOException{
        String sql = "select * from produtos p " +
                "join categorias c on p.id_categoria = c.id " +
                "where p.id_categoria = " + categoria.getId().intValue();
        return this.getManyByCriteria(sql);
    }
    
    public List getProdutos(String nome, Integer idCategoria, String marca) throws DAOException{
        
        String conector = " where ";
        String sql = "select * from produtos p " +
                "join categorias c on p.id_categoria = c.id ";
        
        if (nome != null && !nome.equals("")) {
            sql += conector + " p.nome like '" + nome + "%' ";
            if(conector.trim().equals("where")){
                conector = " and ";
            }
        }
        if (idCategoria != null && idCategoria.intValue() != 0) {
            sql += conector + " p.id_categoria = " + idCategoria.intValue() + " ";
            if(conector.trim().equals("where")){
                conector = " and ";
            }
        }
        if (marca != null && !marca.equals("")) {
            sql += conector + " p.marca like '" + marca + "%' ";
            if(conector.trim().equals("where")){
                conector = " and ";
            }
        }
        
        return this.getManyByCriteria(sql);
        
    }
    
    public void delete(Produto produto) throws DAOException{
        
        Connection conn = null;
        PreparedStatement pst = null;
        
        try {
            conn = getConnection();
            pst = conn.prepareStatement(SQL_DELETE_PRODUTO);
            
            pst.setInt(1, produto.getId().intValue());
            
            pst.executeUpdate();
            
        } catch (Exception e) {
            throw new DAOException("[ProdutoJDBC - delete] : Handled Exception  ", e);
            
        } finally {
            closeResources(conn, pst);
        }
        
    }
    
    public void save(Produto produto) throws DAOException{
        
        Connection conn = null;
        PreparedStatement pst = null;
        
        try {
            conn = getConnection();
            if (produto.getId() == null) {
                System.out.println("[ProdutoJDBC] novo produto vai ser incluido...");
                pst = conn.prepareStatement(SQL_INSERT_PRODUTO);
            } else {
                System.out.println("[ProdutoJDBC] produto ja existente vai ser regravado...");
                pst = conn.prepareStatement(SQL_UPDATE_PRODUTO);
                pst.setInt(10, produto.getId().intValue());
            }
            
            pst.setString(1, produto.getNome());
            pst.setDouble(2, produto.getPreco());
            pst.setString(3, produto.getMarca());
            pst.setInt   (4, produto.getCategoria().getId().intValue());
            pst.setInt   (5, produto.isAtivo()?1:0);
            if(produto instanceof ProdutoMaterial) {
                pst.setString(6, "M");
                pst.setDouble(7, ((ProdutoMaterial)produto).getTaxaEntrega());
                pst.setString(8, "");
                pst.setInt   (9, 0);
            }else if(produto instanceof ProdutoDigital) {
                pst.setString(6, "D");
                pst.setDouble(7, 0);
                pst.setString(8, ((ProdutoDigital)produto).getNomeArquivo());
                pst.setInt   (9, ((ProdutoDigital)produto).getTamanho());
            }
            
            pst.execute();
            
        } catch (Exception e) {
            throw new DAOException("[ProdutoJDBC - save] : Handled Exception  ", e);
            
        } finally {
            closeResources(conn, pst);
        }
        
    }
    
}
