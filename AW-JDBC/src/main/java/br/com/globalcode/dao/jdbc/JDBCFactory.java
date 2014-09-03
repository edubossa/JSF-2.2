package br.com.globalcode.dao.jdbc;

import br.com.globalcode.dao.BancoDAO;
import br.com.globalcode.dao.BandeiraCartaoCreditoDAO;
import br.com.globalcode.dao.CategoriaDAO;
import br.com.globalcode.dao.ClienteDAO;
import br.com.globalcode.dao.DAOFactory;
import br.com.globalcode.dao.PagamentoDAO;
import br.com.globalcode.dao.PedidoDAO;
import br.com.globalcode.dao.ProdutoDAO;

public class JDBCFactory implements DAOFactory{
    
    /** Creates a new instance of JDBCFactory */
    public JDBCFactory() {
    }

    public ClienteDAO getClienteDAO() {
        return ClienteJDBC.getInstance();
    }

    public CategoriaDAO getCategoriaDAO() {
        return CategoriaJDBC.getInstance();
    }

    public BancoDAO getBancoDAO() {
        return BancoJDBC.getInstance();
    }

    public BandeiraCartaoCreditoDAO getBandeiraCartaoCreditoDAO() {
        return BandeiraCartaoCreditoJDBC.getInstance();
    }

    public PagamentoDAO getPagamentoDAO() {
        return PagamentoJDBC.getInstance();
    }

    public PedidoDAO getPedidoDAO() {
        return PedidoJDBC.getInstance();
    }

    public ProdutoDAO getProdutoDAO() {
        return ProdutoJDBC.getInstance();
    }
    
}
