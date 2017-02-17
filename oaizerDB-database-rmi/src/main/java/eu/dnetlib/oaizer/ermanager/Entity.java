//***************************************************************************
// 
// File:         Entity.java
// Created:      May 16, 2012
// Author: 		 Sandro La Bruzzo
// Last Changed: May 15, 2012
// Author:       <A HREF="mailto:sandro.labruzzo@isti.cnr.it">sandro</A>
//
// 
//***************************************************************************
package eu.dnetlib.oaizer.ermanager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;



/**
 * The Class Entity.
 */
public class Entity extends AbstractERNode {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7734621821028359550L;
	
	
	/** The relation from. */
	List<Relation> relationFromMe;
	
	/** The relation to. */
	List<Relation> relationToMe;
	
	/** The primary key. */
	private List<String> primaryKey;
	
	
	
	/**
	 * Convert into relation.
	 *
	 * @return the relation
	 */
	public Relation convertIntoRelation()
	{
		return new Relation();
	}

	
	/**
	 * Gets the relation from the relative Entity.
	 * 
	 * @return the relation from
	 */
	public List<Relation> getRelationFromMe() {
		return relationFromMe;
	}

 	
	/**
	  * Sets the relation from the relative Entity.
	  *
	  * @param relationFrom the new relation from
	  */
 	public void addRelationFromMe(Relation relationFrom) {
		if(this.relationFromMe==null)
			this.relationFromMe= new ArrayList<Relation>();
		 this.relationFromMe.add(relationFrom);
	}

 
	
 	/**
	 * Gets the relation to the relative Entity.
	 *
	 * @return the relation to
	 */
	public List<Relation> getRelationToMe() {
		return relationToMe;
	}

	
	/**
	 * Add the relation to the relative Entity.
	 *
	 * @param relationTo the new relation to
	 */
	public void addRelationToMe(Relation relationTo) {
		if(this.relationToMe==null)
			this.relationToMe= new ArrayList<Relation>();
		this.relationToMe.add(relationTo);
	}

	
	/**
	 * Gets the primary key.
	 *
	 * @return the primaryKey
	 */
	public List<String> getPrimaryKey() {
		return primaryKey;
	}

	/**
	 * Sets the primary key.
	 *
	 * @param primaryKey the primaryKey to set
	 */
	public void setPrimaryKey(List<String> primaryKey) {
		this.primaryKey = primaryKey;
	}	
	
	public Map<String,String> toJson(){		
		Map<String, String> result= Maps.newHashMap();
		result.put("name", getName());
		result.put("positionX", Integer.toString((int)getPosition().getX()));
		result.put("positionY", Integer.toString((int)getPosition().getY()));		
		return result;
	}
	
}
