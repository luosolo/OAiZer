package eu.dnetlib.oaizer.ermanager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.google.gson.Gson;



/**
 * The Class Relation.
 */
public class Relation extends AbstractERNode{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -703345336108791360L;

	/** The entities. */
	List<Entity> entities;	
	
	/** The is complex relation. */
	boolean isComplexRelation;
	
	
	/**
	 * Adds the entities involved in the relation.
	 *
	 * @param from the from
	 * @param to the to
	 */
	public void addRelation(Entity from, Entity to)
	{
		if(entities==null)
			entities= new ArrayList<Entity>();
		
		this.entities.add(from);
		this.entities.add(to);		
	}
	
	
	/**
	 * Gets the entities involved in the relation..
	 *
	 * @return the entities
	 */
	public List<Entity> getEntities()
	{
		return entities;
	}

	
	/**
	 * Checks if is complex relation, I.E. is
	 * a table transformed in a relation
	 *
	 * @return true, if is complex relation
	 */
	public boolean isComplexRelation() {
		return isComplexRelation;
	}

	
	/**
	 * Sets if the relation is a complex relation.
	 *
	 * @param isComplexRelation the new complex relation
	 */
	public void setComplexRelation(boolean isComplexRelation) {
		this.isComplexRelation = isComplexRelation;
	}


	@Override
	public Map<String, String> toJson() {
		Gson g= new Gson();
		Map<String, String> result= Maps.newHashMap();	
		result.put("name", getName());
		List<String>entitiesName= new ArrayList<String>();
		for(Entity e: entities)
			entitiesName.add(e.getName());	
		result.put("relations", g.toJson(entitiesName));
		return result;
	}
	
	
	
}
