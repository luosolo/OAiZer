//***************************************************************************
// 
// File:         Table.java
// Created:      May 15, 2012
// Author: 		 Sandro La Bruzzo
// Last Changed: May 23, 2013
// Author:       <A HREF="mailto:sandro.labruzzo@isti.cnr.it">sandro</A>
//
// 
//***************************************************************************
package eu.dnetlib.oaizer.db.inference;

import java.util.ArrayList;
import java.util.List;



/**
 * The class that represents the information about a Table.
 * 
 */
public class Table {

	/** The name. */
	private String name;

	/** The columns. */
	private List<Column> columns;

	/** The foreign keys. */
	private List<ForeignKey> foreignKeys;

	/** The primary key. */
	private PrimaryKey primaryKey;

	/**
	 * Gets the name of the table.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the table.
	 *
	 * @param name the name
	 * @return the table itself
	 */
	public Table setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Gets all the info about the  column of the table.
	 *
	 * @return the column info
	 */
	public List<Column> getColumnInfo() {
		return columns;
	}

	/**
	 * Add the info of one column of the table.
	 *
	 * @param info the info
	 */
	public void addColumnInfo(Column info) {
		if (columns == null)
			columns = new ArrayList<Column>();
		columns.add(info);

	}

	/**
	 * Gets the foreign key Info of the table.
	 *
	 * @return the foreign key
	 */
	public List<ForeignKey> getForeignKey() {
		return foreignKeys;
	}

	/**
	 * Adds the foreign key Info of the table.
	 *
	 * @param foreignKeyInfo the foreign key info
	 */
	public void addForeignKey(ForeignKey foreignKeyInfo) {

		for (String fi : foreignKeyInfo.getForeignkeyField()) {
			if (existField(fi) == false) {
				throw new RuntimeException("the key with name " + fi
						+ " does not exist");
			}
		}		
		if (foreignKeys == null)
			foreignKeys = new ArrayList<ForeignKey>();
		foreignKeys.add(foreignKeyInfo);
	}

	/**
	 * Gets the primary key Info of the table.
	 *
	 * @return the primary key
	 */
	public PrimaryKey getPrimaryKey() {
		return primaryKey;
	}

	/**
	 * Sets the primary key Info of the table.
	 *
	 * @param primaryKeyInfo the primary key info
	 * @return the table
	 */
	public Table setPrimaryKey(PrimaryKey primaryKeyInfo) {	
		if(primaryKeyInfo==null)
			System.out.println("è nulla");
		
		for(String cInfo: primaryKeyInfo.getPrimaryKeys())
		{
			if(existField(cInfo)==false) {
				throw new RuntimeException("the key with name " + cInfo
						+ " does not exist");
			}				
		}
		primaryKey= primaryKeyInfo;		
		return this;
	}

	/**
	 * Check if the table contains a column with 
	 * the name specified by fieldName
	 * 
	 *
	 * @param fieldName the field name
	 * @return true, if successful
	 */
	public boolean existField(String fieldName) {
		for (Column ci : columns) {
			if (ci.getName().equals(fieldName))
				return true;
		}
		return false;
	}

}
