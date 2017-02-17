//***************************************************************************
// 
// File:         DatabaseInference.java
// Created:      May 15, 2012
// Author: 		 Sandro La Bruzzo
// Last Changed: May 15, 2012
// Author:       <A HREF="mailto:sandro.labruzzo@isti.cnr.it">sandro</A>
//
// 
//***************************************************************************
package eu.dnetlib.oaizer.db.inference;

import java.lang.String;import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import eu.dnetlib.oaizer.ermanager.DatabaseParameter;


/**
 * The Database Inference is responsible to
 * infer the database and give all the information
 * relative to a particular database.
 */
public interface DatabaseInference {
	
	
	/**
	 * Gets the table info.
	 *
	 * @param tableName the table name
	 * @return the table info
	 * @throws SQLException the sQL exception
	 */
	Table getTableInfo(String tableName) throws SQLException;
		
	/**
	 * Gets all the  tables.
	 *
	 * @return the tables
	 * @throws SQLException the sQL exception
	 */
	List<String> getTables() throws SQLException;
	
	
	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 */
	Connection getConnection();

	/**
	 * Sets the connection.
	 *
	 * @param connection the new connection
	 */
	void setConnection(Connection connection);

	/**
	 * Gets the prop connection.
	 *
	 * @return the prop connection
	 */
	DatabaseParameter getPropConnection();

	/**
	 * Sets the prop connection.
	 *
	 * @param propConnection the new prop connection
	 */
	void setPropConnection(DatabaseParameter propConnection);

	/**
	 * Connect.
	 *
	 * @throws SQLException the sQL exception
	 */
	void connect() throws SQLException;

	
	
	/**
	 * check if exist the table with
	 * the name tableName.
	 *
	 * @param tableName the table name
	 * @return true, if exist
	 * @throws SQLException the sQL exception
	 */
	boolean existTable(String tableName) throws SQLException;

}
