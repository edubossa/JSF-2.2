package br.com.globalcode.dao;

import br.com.globalcode.model.Banco;
import java.util.List;

public interface BancoDAO {
    void delete(Banco banco);

    List getAll();

    Banco getByPrimaryKey(Integer id);

    void save(Banco banco);
    
}
