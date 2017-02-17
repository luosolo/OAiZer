//***************************************************************************
// 
// File:         DB2ERAnalyzer.java
// Created:      May 16, 2012
// Author: 		 Sandro La Bruzzo
// Last Changed: May 15, 2012
// Author:       <A HREF="mailto:sandro.labruzzo@isti.cnr.it">sandro</A>
//
// 
//***************************************************************************
package eu.dnetlib.oaizer.ermanager;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import eu.dnetlib.oaizer.db.inference.DatabaseInference;

/**
 * The Interface DB2ERAnalyzer. DB2ERAnalyzer defines the common operation for
 * transform an sql Database Schema, or a subset of it by the introduction of a
 * particular Filter, into an ER-Schema.
 * 
 */
public interface DB2ERAnalyzer {

	/**
	 * Sets the dbinference, a module that give the input Database schema for
	 * the DB2ERAnalyzer.
	 * 
	 * @param dbInference
	 *            the new db inference
	 */
	void setDbInference(DatabaseInference dbInference);

	/**
	 * Gets the dbinference , a module that give the input Database schema for
	 * the DB2ERAnalyzer.
	 * 
	 * @return the db inference
	 */
	DatabaseInference getDbInference();

	/**
	 * Gets the filter database.
	 * 
	 * @return the filter database
	 */
	List<String> getFilterDatabase();

	/**
	 * Adds the filter.
	 * 
	 * @param filter
	 *            the filter
	 */
	void addFilter(List<String> filter) throws SQLException;

	/**
	 * Transfrom a Database into an ER representation.
	 * 
	 * @return the list of the ER-Nodes and Relations
	 */
	Map<String, ERNode> fromDB2ER() throws SQLException;

	/**
	 * Transfrom a Database into an ER representation, but return it in a JSON
	 * format
	 * 
	 * 
	 * @return the list of the ER-Nodes and Relations
	 */
	Map<String, List<Map<String, String>>> fromDB2ERResponse()
			throws SQLException;

}
