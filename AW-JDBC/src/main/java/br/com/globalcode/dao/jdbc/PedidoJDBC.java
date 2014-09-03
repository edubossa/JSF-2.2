package br.com.globalcode.dao.jdbc;

import br.com.globalcode.dao.DAOException;
import br.com.globalcode.dao.PedidoDAO;
import br.com.globalcode.model.Banco;
import br.com.globalcode.model.BandeiraCartaoCredito;
import br.com.globalcode.model.Categoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import br.com.globalcode.util.FormatHelper;
import br.com.globalcode.model.Cliente;
import br.com.globalcode.model.ItemCompra;
import br.com.globalcode.model.Pagamento;
import br.com.globalcode.model.PagamentoBoleto;
import br.com.globalcode.model.PagamentoCartaoCredito;
import br.com.globalcode.model.Pedido;
import br.com.globalcode.model.Produto;
import br.com.globalcode.model.ProdutoDigital;
import br.com.globalcode.model.ProdutoMaterial;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PedidoJDBC extends BaseDAOJDBC implements PedidoDAO {
    
    private static String SQL_INSERT_PEDIDO =
            "insert into pedidos ( data, id_cliente, cliente_IP, cliente_browser, id_pagamento, status) "+
            "values ( ?, ?, ?, ?, ?, ?)";
    
    private static String SQL_UPDATE_PEDIDO =
            "update pedidos set data = ?, id_cliente = ?, cliente_IP = ?, cliente_browser = ?, id_pagamento = ?, " +
            "status = ? where id = ?";
    
    private static String SQL_DELETE_PEDIDO = "delete from pedidos where numero = ? ";
    
    private static String SQL_INSERT_PEDIDO_ITEM =
            "insert into pedidos_itens ( numero_pedido, quantidade, desconto, id_produto) "+
            "values ( ?, ?, ?, ?)";
    
    private static String SQL_UPDATE_PEDIDO_ITEM =
            "update pedidos_itens set numero_pedido = ?, quantidade = ?, desconto = ?, id_produto = ? " +
            "where id = ?";
    
    private static String SQL_DELETE_PEDIDO_ITENS = "delete from pedidos_itens where numero_pedido = ? ";
    
    
    private static PedidoJDBC instance = new PedidoJDBC();
    
    protected PedidoJDBC(){
    }
    
    public static PedidoJDBC getInstance() {
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
                
                Cliente cliente = getCliente(rs);
                
                Pagamento pagamento = getPagamento(rs);
                
                List itens = getItens(rs.getLong("numero"));
                
                Pedido pedido = new Pedido(new Long(rs.getLong("ped.numero")),
                                           cliente,
                                           rs.getString("ped.cliente_browser"),
                                           rs.getString("ped.cliente_IP"),
                                           rs.getDate("ped.data"),
                                           itens,
                                           pagamento,
                                           rs.getString("ped.status"));
                alista.add(pedido);
                
            }
            
            return alista;
            
        } catch (Exception e) {
            throw new DAOException("[PedidoJDBC - getManyByCriteria] " + e.getMessage(), e);
            
        } finally {
            closeResources(conn, st, rs);
        }
        
    }
    
    private Cliente getCliente(ResultSet rs) throws SQLException{
        Cliente cliente = new Cliente(new Integer(rs.getInt("cli.id")),
                                      rs.getString("cli.nome"),
                                      rs.getString("cli.email"),
                                      rs.getString("cli.senha"),
                                      rs.getString("cli.telefone"),
                                      rs.getInt("cli.ativo") == 1,
                                      rs.getString("cli.endereco_rua"),
                                      rs.getString("cli.endereco_numero"),
                                      rs.getString("cli.endereco_bairro"),
                                      rs.getString("cli.endereco_cep"),
                                      rs.getString("cli.endereco_cidade"),
                                      rs.getString("cli.endereco_estado"));
        return cliente;
    }
    private Pagamento getPagamento(ResultSet rs) throws SQLException{
        Pagamento pagamento = null;
        int idBanco  = rs.getInt("pagb.id_banco");
        int idCartao = rs.getInt("pagc.id_bandeira_cartao_credito");
        int tipoPagamento = 0;
        if(idBanco != 0)
            tipoPagamento = 1;
        else if(idCartao != 0)
            tipoPagamento = 2;
        switch(tipoPagamento){
            case 1:
                Banco banco = BancoJDBC.getInstance().getByPrimaryKey(
                        new Integer(rs.getInt("pagb.id_banco")));
                pagamento = new PagamentoBoleto(
                        new Integer(rs.getInt("pag.id")),
                        rs.getDate("pag.data_pagamento"),
                        rs.getDouble("pag.valor"),
                        rs.getString("pagb.cedente"),
                        rs.getString("pagb.sacado"),
                        rs.getString("pagb.codigo_barras"),
                        rs.getDate("pagb.data_vencimento"),
                        banco);
                break;
            case 2:
                BandeiraCartaoCredito bandeiraCartaoCredito =
                        BandeiraCartaoCreditoJDBC.getInstance().getByPrimaryKey(
                        new Integer(rs.getInt("pagc.id_bandeira_cartao_credito")));
                pagamento = new PagamentoCartaoCredito(
                        new Integer(rs.getInt("pag.id")),
                        rs.getDate("pag.data_pagamento"),
                        rs.getDouble("pag.valor"),
                        bandeiraCartaoCredito,
                        rs.getString("pagc.nome_titular"),
                        rs.getString("pagc.numero_cartao"),
                        rs.getLong("pagc.numero_confirmacao"),
                        rs.getDate("pagc.data_validade_cartao"));
        }
        return pagamento;
    }
    private Categoria getCategoria(ResultSet rs) throws SQLException{
        boolean ativo = rs.getInt("c.ativo") == 1;
        Categoria categoria = new Categoria(new Integer(rs.getInt("c.id")),
                                            rs.getString("c.descricao"),
                                            ativo);
        return categoria;
    }
    private Produto getProduto(ResultSet rs) throws SQLException{
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
        return produto;
    }
    private List getItens(long numero) throws DAOException {
        
        String sql = "select * from pedidos_itens i " +
                "join produtos p on i.id_produto = p.id " +
                "join categorias c on p.id_categoria = c.id "+
                "where i.numero_pedido = "+numero;

        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        List alista = new ArrayList();
        
        try {
            conn = getConnection();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            
            while (rs.next()) {
                
                Produto produto = getProduto(rs);
                ItemCompra item = new ItemCompra(new Integer(rs.getInt("id")),
                        produto,
                        rs.getInt("quantidade"),
                        rs.getInt("desconto"));
                alista.add(item);
                
            }
            
            return alista;
            
        } catch (Exception e) {
            throw new DAOException("[PedidoJDBC - getItens] " + e.getMessage(), e);
            
        } finally {
            closeResources(conn, st, rs);
        }
        
    }
    
    public List getAll() throws DAOException{
        String sql = "select * from pedidos ped " +
                     "join clientes cli on cli.id = ped.id_cliente " +
                     "left join pagamentos pag on pag.id = ped.id_pagamento " +
                     "left join pagamentos_boleto pagb on pagb.id = pag.id " +
                     "left join pagamentos_cartao_credito pagc on pagc.id = pag.id ";
        return this.getManyByCriteria(sql);
    }
    
    public Pedido getByPrimaryKey(Integer numero) throws DAOException{
        String sql = "select * from pedidos ped " +
                     "join clientes cli on cli.id = ped.id_cliente " +
                     "left join pagamentos pag on pag.id = ped.id_pagamento " +
                     "left join pagamentos_boleto pagb on pagb.id = pag.id " +
                     "left join pagamentos_cartao_credito pagc on pagc.id = pag.id " +
                     "where ped.numero = " + numero;
        List lista = this.getManyByCriteria(sql);
        if (lista != null && lista.size() > 0)
            return (Pedido) lista.get(0);
        else
            return null;
    }
    
    public List getByCliente(Cliente cliente) throws DAOException{
        String sql = "select * from pedidos ped " +
                     "join clientes cli on cli.id = ped.id_cliente " +
                     "left join pagamentos pag on pag.id = ped.id_pagamento " +
                     "left join pagamentos_boleto pagb on pagb.id = pag.id " +
                     "left join pagamentos_cartao_credito pagc on pagc.id = pag.id " +
                     "where ped.id_cliente = " + cliente.getId();
        return this.getManyByCriteria(sql);
    }
    
    public List getByPeriodo(String strDataInicio, String strDataFinal) throws DAOException{
        String sql = "select * from pedidos ped " +
                     "join clientes cli on cli.id = ped.id_cliente " +
                     "left join pagamentos pag on pag.id = ped.id_pagamento " +
                     "left join pagamentos_boleto pagb on pagb.id = pag.id " +
                     "left join pagamentos_cartao_credito pagc on pagc.id = pag.id " +
                     "where ";
        boolean dataInicio = false;
        if(strDataInicio != null && !(strDataInicio.equals(""))){
            strDataInicio = FormatHelper.getInstance().converteData(strDataInicio + " 00:00");
            sql += " ped.data >= '" + strDataInicio + "'";
            dataInicio = true;
        }
        if(strDataFinal != null && !(strDataFinal.equals(""))){
            strDataFinal = FormatHelper.getInstance().converteData(strDataFinal + " 23:59");
            if(dataInicio) 
                sql += " and ";
            sql += " ped.data <= '" + strDataFinal + "'";
        }
        return this.getManyByCriteria(sql);
    }
    
    public List getByFormaPagamento(String pagamento){
        List itens = null;
        List lista = new ArrayList();
        //lista.add(new Pedido(new Long(1),new Cliente(),"IE","192.168.0.1",new java.util.Date(),itens,new Pagamento(),"ok"));
        //lista.add(new Pedido(new Long(2),new Cliente(),"IE","192.168.0.2",new java.util.Date(),itens,new Pagamento(),"ok"));
        //lista.add(new Pedido(new Long(3),new Cliente(),"IE","192.168.0.3",new java.util.Date(),itens,new Pagamento(),"ok"));
        return lista;
    }
    
    public void delete(Pedido pedido) throws DAOException{
        
        Connection conn = null;
        PreparedStatement pst = null;
        
        try {
            conn = getConnection();
            pst = conn.prepareStatement(SQL_DELETE_PEDIDO);
            pst.setLong(1, pedido.getNumero().longValue());
            pst.executeUpdate();
            
            pst = conn.prepareStatement(SQL_DELETE_PEDIDO_ITENS);
            pst.setLong(1, pedido.getNumero().longValue());
            pst.executeUpdate();
            
        } catch (Exception e) {
            throw new DAOException("[PedidoJDBC - delete] " + e.getMessage(), e);
            
        } finally {
            closeResources(conn, pst);
        }
        
    }
    
    public void save(Pedido pedido) throws DAOException{
        
        Connection conn = null;
        PreparedStatement pst = null;
        
        try {
            conn = getConnection();
            if (pedido.getNumero() == null) {
                pst = conn.prepareStatement(SQL_INSERT_PEDIDO);
            } else {
                pst = conn.prepareStatement(SQL_UPDATE_PEDIDO);
                pst.setLong  (7, pedido.getNumero().longValue());
            }
            
            pst.setDate  (1, new Date(pedido.getData().getTime()));
            pst.setInt   (2, pedido.getCliente().getId().intValue());
            pst.setString(3, pedido.getClienteIP());
            pst.setString(4, pedido.getClienteBrowser());
            pst.setInt   (5, pedido.getPagamento().getId().intValue());
            pst.setString(6, pedido.getStatus());
            
            pst.execute();
            
            //Recupera a chave primaria gerada no caso de uma insercao
            if(pedido.getNumero()==null) {
                ResultSet rs = pst.getGeneratedKeys();
                if(rs.next()) {
                    Long idGerado = new Long(rs.getLong(1));
                    pedido.setNumero(idGerado);
                }
            }
            
            if(pedido.getItensPedido() != null && pedido.getItensPedido().size()>0){
                for(int i=0; i<pedido.getItensPedido().size(); i++){
                    ItemCompra item = (ItemCompra)pedido.getItensPedido().get(i);
                    
                    if (item.getId() == null) {
                        pst = conn.prepareStatement(SQL_INSERT_PEDIDO_ITEM);
                    } else {
                        pst = conn.prepareStatement(SQL_UPDATE_PEDIDO_ITEM);
                        pst.setInt(5, item.getId().intValue());
                    }
                    
                    pst.setLong(1, pedido.getNumero().longValue());
                    pst.setInt(2, item.getQuantidade());
                    pst.setInt(3, item.getDesconto());
                    pst.setInt(4, item.getProduto().getId().intValue());
                    
                    pst.execute();
                    
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("[PedidoJDBC - save] :" + e.getMessage(), e);
            
        } finally {
            closeResources(conn, pst);
        }
        
    }
   
    public List getVendasPorMarca(String strDataInicio, String strDataFinal) throws DAOException {
        
        String sql = "select pro.marca, count(it.quantidade) as quantidade, " +
                "(it.quantidade * pro.preco) as valor "+
                "from pedidos_itens it "+
                "inner join produtos pro on it.id_produto = pro.id " +
                "inner join pedidos ped on it.numero_pedido = ped.numero ";
        
        String sqlDatas = "";
        if(strDataInicio != null && !(strDataInicio.equals(""))){
            strDataInicio = FormatHelper.getInstance().converteData(strDataInicio + " 00:00");
            sqlDatas += " ped.data >= '" + strDataInicio + "'";
        }
        if(strDataFinal != null && !(strDataFinal.equals(""))){
            strDataFinal = FormatHelper.getInstance().converteData(strDataFinal + " 23:59");
            if(!sqlDatas.equals("")) 
                sqlDatas += " and ";
            sqlDatas += " ped.data <= '" + strDataFinal + "'";
        }
        if(!sqlDatas.equals("")) 
            sql += " where " + sqlDatas;
        sql += " group by pro.marca ";
        
        System.out.println("[PedidoJDBC - getVendasPorMarca] sql = "+sql);
           
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        List alista = new ArrayList();
        
        try {
            conn = getConnection();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            
            while (rs.next()) {
                
                Map map = new HashMap();
                map.put("marca",rs.getString("pro.marca"));
                map.put("quantidade",new Integer(rs.getInt("quantidade")));
                map.put("valor",new Double(rs.getDouble("valor")));
                
                alista.add(map);
                
            }
            
            return alista;
            
        } catch (Exception e) {
            throw new DAOException("[PedidoJDBC - getVendasPorMarca] " + e.getMessage(), e);
            
        } finally {
            closeResources(conn, st, rs);
        }
        
    }
    
}

