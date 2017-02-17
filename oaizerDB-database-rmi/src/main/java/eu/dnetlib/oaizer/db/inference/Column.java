//***************************************************************************
// 
// File:         Column.java
// Created:      May 15, 2012
// Author: 		 Sandro La Bruzzo
// Last Changed: May 15, 2012
// Author:       <A HREF="mailto:sandro.labruzzo@isti.cnr.it">sandro</A>
//
// 
//***************************************************************************

package eu.dnetlib.oaizer.db.inference;


/**
 * The Column represents information 
 * of columns from a table in a database.
 * 
 */
public class Column {
	
	/** The name. */
	private String name;
	
	/** The Type. */
	private String type;
	
	/** The size. */
	private int size;
	
	/** The nullable. */
	private boolean nullable;
	
	/** The position. */
	private int position;
	

	/**
	 * Gets the name of the Column.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name of the Column.
	 *
	 * @param name the name
	 * @return the column info itself
	 */
	public Column setName(String name) {
		this.name= name;
		return this;
	}

	/**
	 * Gets the sql type of the column.
	 *
	 * @return the sql type
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the type
	 * @return the column itself
	 */
	public Column setType(String type) {
		this.type= type;
		return this;
	}


	/**
	 * Gets the size of the type of the column. 
	 * Typically useful when the type is varchar.
	 *
	 * @return the size
	 */
	public int getSize() {
		return this.size;
	}

	/**
	 * Sets the size of the type of the Column.
	 *Typically useful when the type is varchar.
	 *
	 * @param size the size
	 * @return the column info itself
	 */
	public Column setSize(int size) {
		this.size= size;
		return this;
	}

	/**
	 * Checks if the field is nullable.
	 *
	 * @return true, if is nullable
	 */
	public boolean isNullable() {
		return this.nullable;
	}

	/**
	 * Sets the field nullable.
	 *
	 * @param nullable the nullable
	 * @return the column info itself
	 */
	public Column setNullable(boolean nullable) {
		this.nullable= nullable;
		return this;
	}

	/**
	 * Gets the order position of the column in the table .
	 *
	 * @return the position
	 */
	public int getPosition() {
		return this.position;
	}

	/**
	 * Sets the order position of the column in the table .
	 *
	 * @param position the position
	 * @return the column info
	 */
	public Column setPosition(int position) {
		this.position= position;
		return this;
	}

}
