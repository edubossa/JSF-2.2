package br.com.globalcode.dao;

public interface DAOFactory {
    
    public ClienteDAO getClienteDAO();
    public CategoriaDAO getCategoriaDAO();
    public BancoDAO getBancoDAO();
    public BandeiraCartaoCreditoDAO getBandeiraCartaoCreditoDAO();
    public PagamentoDAO getPagamentoDAO();
    public PedidoDAO getPedidoDAO();
    public ProdutoDAO getProdutoDAO();
    
    
}
