//***************************************************************************
//
//File:         AbstractERNode.java
//Created:      May 16, 2012
//Author: 		 Sandro La Bruzzo
//Last Changed: May 23, 2013
//Author:       <A HREF="mailto:sandro.labruzzo@isti.cnr.it">sandro</A>
//
//
//***************************************************************************
package eu.dnetlib.oaizer.ermanager;
import java.awt.Point;
import java.io.Serializable;
import java.util.List;

import eu.dnetlib.oaizer.db.inference.Column;




/**
 * The Class AbstractERNode represent a typical Node of the ER-schema,
 * which should be an Entity or a Relation.
 * 
 */
public abstract class AbstractERNode implements Serializable, ERNode {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4400601531254287090L;

	/** The name of the Node. */
	protected String name;
	
	/** The position of the node, useful for its graphic representation. */
	protected Point position;
	
	/** The attributes which constitute the the Node. */
	protected List<Column> attributes;

	
	
	/* (non-Javadoc)
	 * @see eu.dnetlib.R2O.ERManager.SimpleNode#getName()
	 */
	public String getName() {
		return name;
	}

	
	/* (non-Javadoc)
	 * @see eu.dnetlib.R2O.ERManager.SimpleNode#setName(java.lang.String)
	 */
	public void setName(String name) {
		this.name = name;
	}

	
	/* (non-Javadoc)
	 * @see eu.dnetlib.R2O.ERManager.SimpleNode#getPosition()
	 */
	public Point getPosition() {
		return position;
	}

	
	/* (non-Javadoc)
	 * @see eu.dnetlib.R2O.ERManager.SimpleNode#setPosition(java.awt.Point)
	 */
	public void setPosition(Point position) {
		this.position = position;
	}

	
	/* (non-Javadoc)
	 * @see eu.dnetlib.R2O.ERManager.SimpleNode#getAttributes()
	 */
	public List<Column> getAttributes() {
		return attributes;
	}

	
	/* (non-Javadoc)
	 * @see eu.dnetlib.R2O.ERManager.SimpleNode#setAttributes(java.util.List)
	 */
	public void setAttributes(List<Column> attributes) {
		this.attributes = attributes;
	}	
	
	
}
