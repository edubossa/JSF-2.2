package br.com.globalcode.dao;

import br.com.globalcode.exception.GlobalcodeException;

public class BaseDAOFactory {
    
    private static DAOFactory factory;
    
    /** Creates a new instance of BaseDAOFactory */
    
    public static DAOFactory getFactory() throws GlobalcodeException{
        if (factory != null)
            return factory;
        else
            throw new GlobalcodeException("DAOFactory não foi inicializada!!");
    }

    public static synchronized void setFactory(DAOFactory factory) {
        BaseDAOFactory.factory = factory;
    }
}
