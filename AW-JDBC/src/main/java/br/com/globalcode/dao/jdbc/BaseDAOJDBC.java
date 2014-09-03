package br.com.globalcode.dao.jdbc;

import br.com.globalcode.dao.DAOException;
import br.com.globalcode.exception.GlobalcodeException;
import java.sql.*;
import java.util.List;

import br.com.globalcode.locator.ServiceLocator;
import br.com.globalcode.util.Logger;

public abstract class BaseDAOJDBC {
    
    protected BaseDAOJDBC() {
    }
    
    public Connection getConnection() throws GlobalcodeException {
        return ServiceLocator.getInstance().getConnection();
    }
    
    public void closeResources(Connection conn, Statement st) {
        closeResources(conn, st, null);
    }
    
    public void closeResources(Connection conn, Statement st, ResultSet rs) {
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            Logger.log("Erro ao fechar ResultSet. " + e.getMessage(), 7);
        }
        try {
            if (st != null)
                st.close();
        } catch (SQLException e) {
            Logger.log("Erro ao fechar Statement. " + e.getMessage(), 7);
        }
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            Logger.log("Erro ao fechar Connection. " + e.getMessage(), 1);
        }
    }
    
    // captura o código (PK) do registro recém-incluído na tabela
    public long getUltimoCodigo(PreparedStatement pst) throws DAOException {
        ResultSet rs = null;
        try {
            rs = pst.getGeneratedKeys();
            long codigo = 0;
            while (rs.next()) {
                codigo = rs.getLong(1);
            }
            return codigo;
        } catch (Exception e) {
            throw new DAOException("[BaseDAOJDBC - getUltimoCodigo] : Handled Exception  ", e);
        }
    }
    
    protected abstract List getManyByCriteria(String sql) throws DAOException;
    
    
}
