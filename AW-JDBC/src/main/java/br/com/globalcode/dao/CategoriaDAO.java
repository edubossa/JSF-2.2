package br.com.globalcode.dao;

import br.com.globalcode.model.Categoria;
import java.util.List;

public interface CategoriaDAO {
    void delete(Categoria categoria) throws DAOException;

    List getAll() throws DAOException;

    Categoria getByPrimaryKey(Integer id) throws DAOException;

    void save(Categoria categoria) throws DAOException;
    
}
