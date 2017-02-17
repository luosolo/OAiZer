package eu.dnetlib.view;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class View.
 */
public class View {

	/** The root. */
	private Node root;

	public void createViewByFollowRelations(List<Relation> relations,
			String rootName) {
		this.root = new Node();
		this.root.setName(rootName);
		List<String> nodeTocheck = new ArrayList<String>();
		nodeTocheck.add(rootName);
		while (relations.size() > 0) {
			String node = nodeTocheck.get(0);
			List<Relation> relsToAdd = new ArrayList<Relation>();
			for (Relation r : relations) {

			}
		}

	}

	/**
	 * Gets the root.
	 * 
	 * @return the root
	 */
	public Node getRoot() {
		return root;
	}

	/**
	 * Sets the root.
	 * 
	 * @param root
	 *            the new root
	 */
	public void setRoot(Node root) {
		this.root = root;
	}

}
