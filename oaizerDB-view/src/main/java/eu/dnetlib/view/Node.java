package eu.dnetlib.view;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class Node.
 */
public class Node {

	/** The name. */
	private String name;

	/** The children. */
	private List<Relation> children;

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	public void addChild(Relation r) {
		if (children == null)
			children = new ArrayList<Relation>();
		children.add(r);
	}

	/**
	 * Gets the children.
	 * 
	 * @return the children
	 */
	public List<Relation> getChildren() {
		return children;
	}

	/**
	 * Sets the children.
	 * 
	 * @param children
	 *            the new children
	 */
	public void setChildren(List<Relation> children) {
		this.children = children;
	}

}
