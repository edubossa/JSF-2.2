package br.com.globalcode.dao;

import br.com.globalcode.model.Cliente;
import br.com.globalcode.model.Pedido;
import java.util.List;

public interface PedidoDAO {
    void delete(Pedido pedido) throws DAOException;

    List getAll() throws DAOException;

    List getByCliente(Cliente cliente) throws DAOException;

    List getByFormaPagamento(String pagamento);

    List getByPeriodo(String strDataInicio, String strDataFinal) throws DAOException;

    Pedido getByPrimaryKey(Integer numero) throws DAOException;

    void save(Pedido pedido) throws DAOException;
    
}
