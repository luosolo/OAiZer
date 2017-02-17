package eu.dnetlib.session;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;

import eu.dnetlib.oaizer.ermanager.DatabaseParameter;

/**
 * The Class ConnectionInfo, contains all the info about a connection to a
 * database.
 */
public class ConnectionInfo implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The parameter to connect to the database. */
	private DatabaseParameter connectionParameterInfo;

	/** The selected entities. */
	private Set<String> selectedEntities;

	/**
	 * Gets the connection info.
	 * 
	 * @return the connection info
	 */
	public DatabaseParameter getConnectionInfo() {
		return connectionParameterInfo;
	}

	/**
	 * Sets the connection info.
	 * 
	 * @param connectionParameterInfo
	 *            the new connection parameter info
	 */
	public void setConnectionParameterInfo(
			DatabaseParameter connectionParameterInfo) {
		this.connectionParameterInfo = connectionParameterInfo;
	}

	/**
	 * Gets the connection parameter info.
	 * 
	 * @return the connection parameter info
	 */
	public DatabaseParameter getConnectionParameterInfo() {
		return connectionParameterInfo;
	}

	/**
	 * Adds a single entity.
	 * 
	 * @param entity
	 *            the entity
	 */
	public void addEntity(String entity) {
		if (selectedEntities == null)
			selectedEntities = new HashSet<String>();
		selectedEntities.add(entity);
	}

	/**
	 * Adds multiple entities.
	 * 
	 * @param entities
	 *            the entities
	 */
	public void addEntities(List<String> entities) {
		if (entities != null) {
			for (String entity : entities) {
				addEntity(entity);
			}
		}
	}

	/**
	 * Gets the entities.
	 * 
	 * @return the entities
	 */
	public List<String> getEntities() {
		if (selectedEntities == null)
			return null;
		return Lists.newArrayList(selectedEntities);
	}

}
