package eu.dnetlib.oaizer.ermanager;

import java.awt.Point;
import java.util.List;
import java.util.Map;

import eu.dnetlib.oaizer.db.inference.Column;

// TODO: Auto-generated Javadoc
/**
 * The Interface ERNode.
 */
public interface ERNode {
	
	
	/**
	 * Gets the name of the Node.
	 *
	 * @return the name
	 */
	public String getName();

	/**
	 * Sets the name of the Node.
	 *
	 * @param name the new name
	 */
	public void setName(String name);

	/**
	 * Gets the position of the Node.
	 *
	 * @return the position
	 */
	public Point getPosition();

	/**
	 * Sets the position of the Node.
	 *
	 * @param position the new position
	 */
	void setPosition(Point position);

	/**
	 * Gets the attributes of the Node.
	 *
	 * @return the attributes
	 */
	List<Column> getAttributes();

	/**
	 * Sets the attributes of the Node.
	 *
	 * @param attributes the new attributes
	 */
	void setAttributes(List<Column> attributes);
	
	
	/**
	 * Return a  json version of the ER Node.
	 *
	 * @return the string
	 */
	Map<String, String>toJson();
	
	
	

}
