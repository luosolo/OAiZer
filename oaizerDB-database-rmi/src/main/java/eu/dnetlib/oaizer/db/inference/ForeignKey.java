//***************************************************************************
// 
// File:         ForeignKeyImpl.java
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
 * The class ForeignKey represent information 
 * relative to a Foreign key in a Table of the database.
 * Each Foreign Key has a list of field which are connected 
 * to a list of primary key of a particular table
 */
public class ForeignKey  {
	
	/** The fields. */
	private List<String> fields;
	
	/** The table referred. */
	private String tableReferred;
	
	/** The table which is defined the foreign key. */
	private String tableRefer;
	
	/** The primary keys. */
	private List<String> primaryKeys;
	
	
	
	

	/**
	 * Gets the list of the attributes which are the foreign key field.
	 *
	 * @return the foreign key field
	 */
	public List<String> getForeignkeyField() {
		return this.fields;
	}

	/**
	 * Add a column which is a foreign key field.
	 *
	 * @param foreignkeyField the foreign key field
	 * @return the foreign key info itself
	 */
	public ForeignKey addForeignkeyField(String foreignkeyField) {
		if(fields== null)
			fields= new ArrayList<String>();
		fields.add(foreignkeyField);
		return this;
	}

	/**
	 * Gets the table which the foreign key refers.
	 *
	 * @return the table reference
	 */
	public String getTableReference() {
		return this.tableReferred;
	}

	/**
	 * Sets the table reference.
	 *
	 * @param tableReference the table reference
	 * @return the foreign key itself
	 */
	public ForeignKey setTableReference(String tableReference) {
		this.tableReferred= tableReference;
		return this;
	}

	/**
	 * Gets the primary key of the table which the foreign keys refer.
	 *
	 * @return the primary key reference
	 */
	public List<String> getPrimaryKeyReference() {
		return primaryKeys;
	}

	/**
	 * Sets the primary key reference.
	 *
	 * @param primaryKeyReference the primary key reference
	 * @return the foreign key info
	 */
	public ForeignKey addPrimaryKeyReference(String primaryKeyReference) {
		if(this.primaryKeys== null)
			this.primaryKeys= new ArrayList<String>();
		this.primaryKeys.add(primaryKeyReference);
		return this;
	}

	
	/**
	 * Gets the table which is defined the
	 *	foreign key 
	 *
	 * @return the table refer
	 */
	public String getTableRefer() {
		return tableRefer;
	}

	
	
	/**
	 * Sets the table which is defined the
	 * foreign key.
	 *
	 * @param tableRefer the new table refer
	 * @return the foreign key
	 */
	public ForeignKey setTableRefer(String tableRefer) {
		this.tableRefer = tableRefer;
		return this;
	}

}
