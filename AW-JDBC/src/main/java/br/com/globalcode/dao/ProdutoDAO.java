package br.com.globalcode.dao;
import br.com.globalcode.model.Categoria;
import br.com.globalcode.model.Produto;
import java.util.List;

public interface ProdutoDAO {
    void delete(Produto produto) throws DAOException;

    List getAll() throws DAOException;

    List getByCategoria(Categoria categoria) throws DAOException;

    List getByMarca(String marca) throws DAOException;

    Produto getByPrimaryKey(Integer id) throws DAOException;

    List getProdutos(String nome, Integer idCategoria, String marca) throws DAOException;

    void save(Produto produto) throws DAOException;
    
}
