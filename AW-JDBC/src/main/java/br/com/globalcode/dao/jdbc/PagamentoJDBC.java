package br.com.globalcode.dao.jdbc;

import br.com.globalcode.dao.BancoDAO;
import br.com.globalcode.dao.BandeiraCartaoCreditoDAO;
import br.com.globalcode.dao.DAOException;
import br.com.globalcode.dao.PagamentoDAO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.globalcode.model.Banco;
import br.com.globalcode.model.BandeiraCartaoCredito;
import br.com.globalcode.model.Pagamento;
import br.com.globalcode.model.PagamentoBoleto;
import br.com.globalcode.model.PagamentoCartaoCredito;

public class PagamentoJDBC extends BaseDAOJDBC implements PagamentoDAO {
    
    private static String SQL_INSERT_PAGAMENTO =
            "insert into pagamentos ( data_pagamento, valor) " +
            "values ( ?, ? )";
    private static String SQL_UPDATE_PAGAMENTO =
            "update pagamentos set data_pagamento = ?, valor = ? " +
            "where id = ?";
    private static String SQL_DELETE_PAGAMENTO = "delete from pagamentos where id = ? ";
    
    private static String SQL_INSERT_PAGAMENTO_BOLETO =
            "insert into pagamentos_boleto ( id_banco, cedente, sacado, codigo_barras, data_vencimento, id) " +
            "values ( ?, ?, ?, ?, ?, ?)";
    private static String SQL_UPDATE_PAGAMENTO_BOLETO =
            "update pagamentos_boleto set id_banco = ?, cedente = ?, sacado = ?, codigo_barras = ?, " +
            "data_vencimento = ? where id = ?";
    private static String SQL_DELETE_PAGAMENTO_BOLETO = "delete from pagamentos_boleto where id = ? ";
    
    private static String SQL_INSERT_PAGAMENTO_CARTAO =
            "insert into pagamentos_cartao_credito ( id_bandeira_cartao_credito, nome_titular, numero_cartao, numero_confirmacao, " +
            "data_validade_cartao, id) " +
            "values ( ?, ?, ?, ?, ?, ?)";
    private static String SQL_UPDATE_PAGAMENTO_CARTAO =
            "update pagamentos_cartao_credito set id_bandeira_cartao_credito = ?, nome_titular = ?, numero_cartao = ?, " +
            "numero_confirmacao = ?, data_validade_cartao = ? where id = ?";
    private static String SQL_DELETE_PAGAMENTO_CARTAO = "delete from pagamentos where id = ? ";
    
    
    private static PagamentoJDBC instance = new PagamentoJDBC();
    
    protected PagamentoJDBC(){
        
    }
    
    public static PagamentoJDBC getInstance() {
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
                
                int idBanco  = rs.getInt("id_banco");
                int idCartao = rs.getInt("id_bandeira_cartao_credito");
                int tipoPagamento = 0;
                if(idBanco != 0)
                    tipoPagamento = 1;
                else if(idCartao != 0)
                    tipoPagamento = 2;
                
                Pagamento pagamento = null;
                
                switch(tipoPagamento){
                    case 1:
                        Banco banco = BancoJDBC.getInstance().getByPrimaryKey(
                                new Integer(rs.getInt("id_banco")));
                        pagamento = new PagamentoBoleto(
                                new Integer(rs.getInt("id")),
                                rs.getDate("data_pagamento"),
                                rs.getDouble("valor"),
                                rs.getString("cedente"),
                                rs.getString("sacado"),
                                rs.getString("codigo_barras"),
                                rs.getDate("data_vencimento"),
                                banco);
                        break;
                    case 2:
                        BandeiraCartaoCredito bandeiraCartaoCredito =
                                BandeiraCartaoCreditoJDBC.getInstance().getByPrimaryKey(
                                new Integer(rs.getInt("id_bandeira_cartao_credito")));
                        pagamento = new PagamentoCartaoCredito(
                                new Integer(rs.getInt("id")),
                                rs.getDate("data_pagamento"),
                                rs.getDouble("valor"),
                                bandeiraCartaoCredito,
                                rs.getString("nome_titular"),
                                rs.getString("numero_cartao"),
                                rs.getLong("numero_confirmacao"),
                                rs.getDate("data_validade_cartao"));
                }
                alista.add(pagamento);
                
            }
            
            return alista;
            
        } catch (Exception e) {
            throw new DAOException("[PagamentoJDBC - getManyByCriteria] " + e.getMessage(), e);
            
        } finally {
            closeResources(conn, st, rs);
        }
        
    }
    
    public List getAll() throws DAOException{
        String sql = "select * from pagamentos " +
                "left join pagamentos_boleto on pagamentos.id = pagamentos_boleto.id " +
                "left join pagamentos_cartao_credito on pagamentos.id = pagamentos_cartao_credito.id";
        return this.getManyByCriteria(sql);
    }
    
    public Pagamento getByPrimaryKey(Integer id) throws DAOException{
        String sql = "select * from pagamentos " +
                "left join pagamentos_boleto on pagamentos.id = pagamentos_boleto.id " +
                "left join pagamentos_cartao_credito on pagamentos.id = pagamentos_cartao_credito.id " +
                "where pagamentos.id = " + id;
        List lista = this.getManyByCriteria(sql);
        if (lista != null && lista.size() > 0)
            return (Pagamento) lista.get(0);
        else
            return null;
    }
    
    public void delete(Pagamento pagamento) throws DAOException{
        
        Connection conn = null;
        PreparedStatement pst = null;
        
        try {
            conn = getConnection();
            pst = conn.prepareStatement(SQL_DELETE_PAGAMENTO);
            pst.setInt(1, pagamento.getId().intValue());
            pst.executeUpdate();
            
            if(pagamento instanceof PagamentoBoleto)
                pst = conn.prepareStatement(SQL_DELETE_PAGAMENTO_BOLETO);
            else if(pagamento instanceof PagamentoCartaoCredito)
                pst = conn.prepareStatement(SQL_DELETE_PAGAMENTO_CARTAO);
            pst.setInt(1, pagamento.getId().intValue());
            pst.executeUpdate();
            
        } catch (Exception e) {
            throw new DAOException("[PagamentoJDBC - delete] " + e.getMessage(), e);
            
        } finally {
            closeResources(conn, pst);
        }
        
    }
    
    public Pagamento save(Pagamento pagamento) throws DAOException{
        
        Connection conn = null;
        PreparedStatement pst = null;
        
        try {
            conn = getConnection();
            if (pagamento.getId() == null) {
                pst = conn.prepareStatement(SQL_INSERT_PAGAMENTO);
            } else {
                pst = conn.prepareStatement(SQL_UPDATE_PAGAMENTO);
                pst.setInt   (3, pagamento.getId().intValue());
            }
            pst.setDate  (1, new Date(pagamento.getDataPagamento().getTime()));
            pst.setDouble(2, pagamento.getValor());
            
            pst.execute();
            
            //Recupera a chave primaria gerada no caso de uma insercao
            Integer idGerado = null;
            if(pagamento.getId()==null) {
                ResultSet rs = pst.getGeneratedKeys();
                if(rs.next()) {
                    idGerado = new Integer(rs.getInt(1));
                }
            }
            
            if(pagamento instanceof PagamentoBoleto) {
                if (pagamento.getId() == null) {
                    pagamento.setId(idGerado);
                    pst = conn.prepareStatement(SQL_INSERT_PAGAMENTO_BOLETO);
                } else {
                    pst = conn.prepareStatement(SQL_UPDATE_PAGAMENTO_BOLETO);
                }
                pst.setInt   (1, ((PagamentoBoleto)pagamento).getBanco().getId().intValue());
                pst.setString(2, ((PagamentoBoleto)pagamento).getCedente());
                pst.setString(3, ((PagamentoBoleto)pagamento).getSacado());
                pst.setString(4, ((PagamentoBoleto)pagamento).getNumeroCodigoBarras());
                pst.setDate  (5, new Date(((PagamentoBoleto)pagamento).getDataVencimento().getTime()));
                pst.setInt   (6, pagamento.getId().intValue());
                
            }else if(pagamento instanceof PagamentoCartaoCredito){
                if (pagamento.getId() == null) {
                    pst = conn.prepareStatement(SQL_INSERT_PAGAMENTO_CARTAO);
                    pagamento.setId(idGerado);                    
                } else {
                    pst = conn.prepareStatement(SQL_UPDATE_PAGAMENTO_CARTAO);
                }
                pst.setInt   (1, ((PagamentoCartaoCredito)pagamento).getBandeiraCartaoCredito().getId().intValue());
                pst.setString(2, ((PagamentoCartaoCredito)pagamento).getNomeTitular());
                pst.setString(3, ((PagamentoCartaoCredito)pagamento).getNumeroCartao());
                pst.setLong  (4, ((PagamentoCartaoCredito)pagamento).getNumeroConfirmacao());
                pst.setDate  (5, new Date(((PagamentoCartaoCredito)pagamento).getDataValidadeCartao().getTime()));
                pst.setInt   (6, pagamento.getId().intValue());
                
            }
            pst.execute();
            
            return pagamento;
            
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("[PagamentoJDBC - save] " + e.getMessage(), e);
            
        } finally {
            closeResources(conn, pst);
        }
        
    }
    
}
