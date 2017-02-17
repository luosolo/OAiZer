//***************************************************************************
// 
// File:         PrimaryKeyImpl.java
// Created:      May 15, 2012
// Author: 		 Sandro La Bruzzo
// Last Changed: May 15, 2012
// Author:       <A HREF="mailto:sandro.labruzzo@isti.cnr.it">sandro</A>
//
// 
//***************************************************************************
package eu.dnetlib.oaizer.db.inference;

import java.util.ArrayList;
import java.util.List;



/**
 * The class PrimaryKey, represents the information abuot the primary key
 * of the table.
 */
public class PrimaryKey{
	
	/** The table. */
	private Table table;
	
	/** The columns which constitute the primary key. 
	*/
	private List<String> primaryKeyColumns;
	

	/**
	 * Gets the table  which the primary key belong.
	 *
	 * @return the table
	 */
	public Table getTable() {
		return table;
	}

	/**
	 * Sets the table  which the primary key belong.
	 *
	 * @param table the table
	 * @return the primary key
	 */
	public PrimaryKey setTable(Table table) {
		this.table= table;
		return this;
	}

	/**
	 * Gets all the attribute that constitute the primary key.
	 *
	 * @return the primary keys
	 */
	public List<String> getPrimaryKeys() {
		return primaryKeyColumns;
	}

	/**
	 * Adds the primary key.
	 *
	 * @param columnName the column name
	 * @return the primary key
	 */
	public PrimaryKey addPrimaryKey(String columnName) {
		if(primaryKeyColumns==null)
			primaryKeyColumns= new ArrayList<String>();
		primaryKeyColumns.add(columnName);
		return this;
	}

}
