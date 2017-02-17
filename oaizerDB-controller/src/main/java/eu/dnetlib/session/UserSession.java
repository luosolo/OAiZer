package eu.dnetlib.session;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import eu.dnetlib.oaizer.ermanager.DatabaseParameter;

// TODO: Auto-generated Javadoc
/**
 * The Class UserSession.
 */
public class UserSession implements Serializable {

	/** Serial version ID. */
	private static final long serialVersionUID = 8592410412031931757L;

	/** The user name. */
	private String userName;

	/** The sessions. */
	private Map<String, ConnectionInfo> sessions;

	/**
	 * Creates the session.
	 * 
	 * @param connectionName
	 *            the name of the session
	 * @param params
	 *            the parameters of the connection
	 */
	public void createSession(String connectionName, DatabaseParameter params) {

		if (sessions == null) {
			sessions = new HashMap<String, ConnectionInfo>();
		}

		ConnectionInfo newConnection = new ConnectionInfo();
		newConnection.setConnectionParameterInfo(params);
		sessions.put(connectionName, newConnection);
	}

	/**
	 * Gets the list of connection name.
	 * 
	 * @return the connection name list
	 */
	public List<String> getConnectionList() {
		if (sessions == null)
			return null;
		if (sessions.keySet() == null)
			return null;
		return Lists.newArrayList(sessions.keySet());
	}

	/**
	 * Gets the info for connection.
	 * 
	 * @param connectionName
	 *            the connection name
	 * @return the info for connection
	 */
	public ConnectionInfo getInfoForConnection(String connectionName) {
		if (sessions == null) {
			return null;
		}
		return sessions.get(connectionName);

	}

	/**
	 * Gets the user name.
	 * 
	 * @return the user name
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the user name.
	 * 
	 * @param userName
	 *            the new user name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
