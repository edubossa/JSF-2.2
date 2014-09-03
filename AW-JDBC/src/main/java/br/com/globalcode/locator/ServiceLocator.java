package br.com.globalcode.locator;

import javax.naming.*;
import javax.sql.*;
import java.sql.*;

import br.com.globalcode.exception.GlobalcodeException;
import br.com.globalcode.util.Logger;

public class ServiceLocator {

	private String databaseURL = "jdbc:mysql://localhost:3306/aw";

	private String databasePassword = "123456";

	private String databaseUser = "root";

	private boolean useDatasource = false;

	private final String POOLING_NAME = "AW-Pooling1";

	private static InitialContext initCtx = null;

	private static ServiceLocator instance = new ServiceLocator();

	/**
	 * @return Returns the databasePassword.
	 */
	public String getDatabasePassword() {
		return databasePassword;
	}

	/**
	 * @param databasePassword
	 *          The databasePassword to set.
	 */
	public void setDatabasePassword(String databasePassword) {
		this.databasePassword = databasePassword;
	}

	/**
	 * @return Returns the databaseURL.
	 */
	public String getDatabaseURL() {
		return databaseURL;
	}

	/**
	 * @param databaseURL
	 *          The databaseURL to set.
	 */
	public void setDatabaseURL(String databaseURL) {
		this.databaseURL = databaseURL;
	}

	/**
	 * @return Returns the databaseUser.
	 */
	public String getDatabaseUser() {
		return databaseUser;
	}

	/**
	 * @param databaseUser
	 *          The databaseUser to set.
	 */
	public void setDatabaseUser(String databaseUser) {
		this.databaseUser = databaseUser;
	}

	/**
	 * @return Returns the useDatasource.
	 */
	public boolean isUseDatasource() {
		return useDatasource;
	}

	/**
	 * @param useDatasource
	 *          The useDatasource to set.
	 */
	public void setUseDatasource(boolean useDatasource) {
		this.useDatasource = useDatasource;
	}

	static {
		try {
			initCtx = new InitialContext();
		} catch (NamingException e) {
			System.err.println(e.getMessage());
			System.err
					.println("Erro fatal, sem comunicacao com InitialContext JNDI");
		}
	}

	private ServiceLocator() {
	}

	public static ServiceLocator getInstance() {
		return instance;
	}

	public Connection getConnection() throws GlobalcodeException {
		if (useDatasource)
			return getConnectionByPool();
		else
			return getConnectionManual();
	}

	private Connection getConnectionManual() throws GlobalcodeException {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(databaseURL, databaseUser,
					databasePassword);
		} catch (Exception e) {
			Logger.log("Erro fatal ao obter conexao com DriverManager: "
					+ e.getMessage(), 1);
			throw new GlobalcodeException(
					"Erro ao obter conexao via DriverManager: "
							+ e.getMessage());
		}
		return conn;
	}

	private Connection getConnectionByPool() throws GlobalcodeException {
		Connection conn = null;
		DataSource ds = null;
		try {
			ds = (DataSource) initCtx.lookup("java:/comp/env/" + POOLING_NAME);
			if (ds == null) {
				System.err.println("Recurso " + POOLING_NAME
						+ " nao configurado no Web Container.");
				throw new GlobalcodeException(
						"Erro ao obter conexao via JNDI: " + POOLING_NAME);
			} else {
				conn = ds.getConnection();
			}
		} catch (Exception e) {
			System.err.println("Erro fatal ao obter conexao com datasource: "
					+ e.getMessage());
			e.printStackTrace();
			throw new GlobalcodeException("Erro ao obter conexao via JNDI: "
					+ POOLING_NAME);
		}
		return conn;
	}
}
