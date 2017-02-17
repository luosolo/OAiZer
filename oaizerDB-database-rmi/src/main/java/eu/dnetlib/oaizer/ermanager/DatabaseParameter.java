package eu.dnetlib.oaizer.ermanager;

import java.io.Serializable;

/**
 * The Class DatabaseParameter. contains all necessary information to access and
 * connect to database
 */
public class DatabaseParameter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6875741617571630344L;

	/** The connection name. */
	private String connectionName;

	/** The login. */
	private String login;

	/** The password. */
	private String password;

	/** The database. */
	private String database;

	/** The hostname. */
	private String hostname;

	/**
	 * Instantiates a new database parameter.
	 */
	public DatabaseParameter() {

	}

	/**
	 * Instantiates a new database parameter.
	 * 
	 * @param connectionName
	 *            the connection name
	 * @param login
	 *            the login
	 * @param password
	 *            the password
	 * @param database
	 *            the database
	 * @param hostname
	 *            the hostname
	 */
	public DatabaseParameter(String connectionName, String login,
			String password, String database, String hostname) {
		super();
		this.connectionName = connectionName;
		this.login = login;
		this.password = password;
		this.database = database;
		this.hostname = hostname;
	}

	/**
	 * Gets the connection name.
	 * 
	 * @return the connection name
	 */
	public String getConnectionName() {
		return connectionName;
	}

	/**
	 * Sets the connection name.
	 * 
	 * @param connectionName
	 *            the new connection name
	 */
	public void setConnectionName(String connectionName) {
		this.connectionName = connectionName;
	}

	/**
	 * Gets the login.
	 * 
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Sets the login.
	 * 
	 * @param login
	 *            the new login
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Gets the password.
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 * 
	 * @param password
	 *            the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the database.
	 * 
	 * @return the database
	 */
	public String getDatabase() {
		return database;
	}

	/**
	 * Sets the database.
	 * 
	 * @param database
	 *            the new database
	 */
	public void setDatabase(String database) {
		this.database = database;
	}

	/**
	 * Gets the hostname.
	 * 
	 * @return the hostname
	 */
	public String getHostname() {
		return hostname;
	}

	/**
	 * Sets the hostname.
	 * 
	 * @param hostname
	 *            the new hostname
	 */
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

}
