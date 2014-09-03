package br.com.globalcode.dao.jdbc;

import br.com.globalcode.dao.BandeiraCartaoCreditoDAO;
import br.com.globalcode.dao.DAOException;
import java.util.ArrayList;
import java.util.List;

import br.com.globalcode.model.BandeiraCartaoCredito;

public class BandeiraCartaoCreditoJDBC extends BaseDAOJDBC implements BandeiraCartaoCreditoDAO {
    
    private List bandeiras = new ArrayList();
    
    private static BandeiraCartaoCreditoJDBC instance = new BandeiraCartaoCreditoJDBC();
    
    protected BandeiraCartaoCreditoJDBC(){
        bandeiras.add(new BandeiraCartaoCredito(new Integer(1),"Visa"));
        bandeiras.add(new BandeiraCartaoCredito(new Integer(2),"Mastercard"));
        bandeiras.add(new BandeiraCartaoCredito(new Integer(3),"American Express"));
        bandeiras.add(new BandeiraCartaoCredito(new Integer(4),"Dinners"));
    }
    
    public static BandeiraCartaoCreditoJDBC getInstance() {
        return instance;
    }
    
    protected List getManyByCriteria(String sql) throws DAOException {
        return null;
    }
    
    public List getAll(){
        return bandeiras;
    }
    
    public BandeiraCartaoCredito getByPrimaryKey(Integer id){
        BandeiraCartaoCredito bandeira = null;
        for(int i=0; i<bandeiras.size(); i++){
            BandeiraCartaoCredito bandeiraDaLista = (BandeiraCartaoCredito) bandeiras.get(i);
            if(bandeiraDaLista.getId().equals(id))
                bandeira = bandeiraDaLista;
        }
        return bandeira;
    }
    
    public void delete(BandeiraCartaoCredito bandeira){
    }
    
    public void save(BandeiraCartaoCredito bandeira){
    }
    
}