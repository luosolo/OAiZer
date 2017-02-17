package eu.dnetlib.view;

/**
 * The Class Relation.
 */
public class Relation {

	/** The name. */
	private String name;

	/** The node. */
	private Node nodeFrom;

	/** The node to. */
	private Node nodeTo;

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

	/**
	 * Gets the node from.
	 * 
	 * @return the node from
	 */
	public Node getNodeFrom() {
		return nodeFrom;
	}

	/**
	 * Sets the node from.
	 * 
	 * @param nodeFrom
	 *            the new node from
	 */
	public void setNodeFrom(Node nodeFrom) {
		this.nodeFrom = nodeFrom;
	}

	/**
	 * Gets the node to.
	 * 
	 * @return the node to
	 */
	public Node getNodeTo() {
		return nodeTo;
	}

	/**
	 * Sets the node to.
	 * 
	 * @param nodeTo
	 *            the new node to
	 */
	public void setNodeTo(Node nodeTo) {
		this.nodeTo = nodeTo;
	}

}
