package br.com.globalcode.dao.jdbc;

import br.com.globalcode.dao.BancoDAO;
import br.com.globalcode.dao.DAOException;
import java.util.ArrayList;
import java.util.List;

import br.com.globalcode.model.Banco;

public class BancoJDBC extends BaseDAOJDBC implements BancoDAO {
    
    private List bancos = new ArrayList();
    
    private static BancoJDBC instance = new BancoJDBC();
    
    protected BancoJDBC(){
        bancos.add(new Banco(new Integer(1),"Banco do Brasil"));
        bancos.add(new Banco(new Integer(2),"Bradesco"));
        bancos.add(new Banco(new Integer(3),"Itaú"));
    }
    
    public static BancoJDBC getInstance() {
        return instance;
    }
    
    protected List getManyByCriteria(String sql) throws DAOException {
        return null;
    }
    
    public List getAll(){
        return bancos;
    }
    
    public Banco getByPrimaryKey(Integer id){
        Banco banco = null;
        for(int i=0; i<bancos.size(); i++){
            Banco bancoDaLista = (Banco) bancos.get(i);
            if(bancoDaLista.getId().equals(id))
                banco = bancoDaLista;
        }
        return banco;
    }
    
    public void delete(Banco banco){
    }
    
    public void save(Banco banco){
    }
    
}