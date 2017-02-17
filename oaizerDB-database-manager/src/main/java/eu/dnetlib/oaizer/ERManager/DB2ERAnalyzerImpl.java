//***************************************************************************
// 
// File:         DB2ERAnalyzerImpl.java
// Created:      May 16, 2013
// Author: 		 Sandro La Bruzzo
// Last Changed: May 15, 2012
// Author:       <A HREF="mailto:sandro.labruzzo@isti.cnr.it">sandro</A>
//
// 
//***************************************************************************
package eu.dnetlib.oaizer.ERManager;

import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.mxgraph.layout.mxGraphLayout;
import com.mxgraph.layout.mxOrganicLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;

import eu.dnetlib.oaizer.db.inference.DatabaseInference;
import eu.dnetlib.oaizer.db.inference.ForeignKey;
import eu.dnetlib.oaizer.db.inference.Table;
import eu.dnetlib.oaizer.ermanager.DB2ERAnalyzer;
import eu.dnetlib.oaizer.ermanager.ERNode;
import eu.dnetlib.oaizer.ermanager.Entity;
import eu.dnetlib.oaizer.ermanager.Relation;

// TODO: Auto-generated Javadoc
/**
 * The Class DB2ERAnalyzerImpl.
 */
public class DB2ERAnalyzerImpl implements DB2ERAnalyzer {

	/** The db inference. */
	DatabaseInference dbInference;

	/** The ER nodes. */
	Map<String, ERNode> ERNodes;

	/** The filter tables. */
	List<String> filterTables;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.dnetlib.R2O.ERManager.DB2ERAnalyzer#setDbInference(eu.dnetlib.R2O.
	 * dbInformation.inference.DatabaseInference)
	 */
	@Override
	public void setDbInference(DatabaseInference dbInference) {
		this.dbInference = dbInference;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.dnetlib.R2O.ERManager.DB2ERAnalyzer#getDbInference()
	 */
	@Override
	public DatabaseInference getDbInference() {
		return this.dbInference;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.dnetlib.R2O.ERManager.DB2ERAnalyzer#getFilterDatabase()
	 */
	@Override
	public List<String> getFilterDatabase() {
		return this.filterTables;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.dnetlib.R2O.ERManager.DB2ERAnalyzer#addFilter(java.util.List)
	 */
	@Override
	public void addFilter(List<String> filter) throws SQLException {
		for (String s : filter) {
			if (dbInference.existTable(s) == false)
				throw new RuntimeException("Error the tables" + s
						+ " in the filter does not exist");
		}
		this.filterTables = filter;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.dnetlib.R2O.ERManager.DB2ERAnalyzer#fromDB2ER()
	 */
	@Override
	public Map<String, ERNode> fromDB2ER() throws SQLException {
		if (ERNodes == null) {
			ERNodes = new HashMap<String, ERNode>();
		} else

			ERNodes.clear();
		mxGraph graph = new mxGraph();
		Object parent = graph.getDefaultParent();
		graph.getModel().beginUpdate();
		Rectangle2D.Double square = new Rectangle2D.Double(0, 0, 10240, 10240);
		mxGraphLayout layout = new mxOrganicLayout(graph, square);
		layout.setUseBoundingBox(true);
		List<ForeignKey> fkey = new ArrayList<ForeignKey>();
		Map<String, mxCell> nodeGraph = new HashMap<String, mxCell>();
		for (String s : dbInference.getTables()) {

			if (filterTables != null && filterTables.size() > 0) {
				if (filterTables.contains(s)) {
					Entity e = new Entity();
					e.setName(s);
					getInfoFromtable(e, fkey);
					ERNodes.put(s, e);
					nodeGraph.put(e.getName(), (mxCell) graph.insertVertex(
							parent, e.getName(), null, 0, 0, 208, 176));
				}
			} else {
				Entity e = new Entity();
				e.setName(s);
				getInfoFromtable(e, fkey);
				ERNodes.put(s, e);
				nodeGraph.put(e.getName(), (mxCell) graph.insertVertex(parent,
						e.getName(), null, 0, 0, 208, 176));
			}

		}
		layout.execute(parent);
		graph.getModel().endUpdate();
		for (String nodeName : nodeGraph.keySet()) {
			mxCell currentNode = nodeGraph.get(nodeName);
			double x = graph.getModel().getGeometry(currentNode).getX();
			double y = graph.getModel().getGeometry(currentNode).getY();
			Point p = new Point();
			p.setLocation(x, y);
			ERNode e = ERNodes.get(nodeName);
			e.setPosition(p);
		}

		normalize();

		for (ForeignKey ff : fkey) {
			Entity from = (Entity) ERNodes.get(ff.getTableRefer());
			Entity to = (Entity) ERNodes.get(ff.getTableReference());
			if (from != null && to != null) {
				Relation relation = new Relation();
				relation.addRelation(from, to);
				relation.setName(ff.getForeignkeyField().get(0));
				from.addRelationFromMe(relation);
				to.addRelationToMe(relation);
				ERNodes.put("rel::" + relation.getName(), relation);
			}

		}
		return ERNodes;
	}

	/**
	 * Gets the info fromtable.
	 * 
	 * @param node
	 *            the node
	 * @param fkeys
	 *            the fkeys
	 * @return the info fromtable
	 * @throws SQLException
	 *             the sQL exception
	 */
	private void getInfoFromtable(Entity node, List<ForeignKey> fkeys)
			throws SQLException {
		Table t = dbInference.getTableInfo(node.getName());
		node.setAttributes(t.getColumnInfo());
		if (t.getPrimaryKey() != null)
			node.setPrimaryKey(t.getPrimaryKey().getPrimaryKeys());
		if (t.getForeignKey() != null)
			for (ForeignKey ff : t.getForeignKey())
				fkeys.add(ff);
	}

	/**
	 * Normalize.
	 */
	private void normalize() {
		Point min = null;
		for (String sName : ERNodes.keySet()) {
			ERNode node = ERNodes.get(sName);
			if (node.getPosition() != null) {
				if (min == null)
					min = node.getPosition();
				else {
					if (min.getX() > node.getPosition().getX())
						min.setLocation(node.getPosition().getX(), min.getY());
					if (min.getY() > node.getPosition().getY())
						min.setLocation(min.getX(), node.getPosition().getY());
				}
			}
		}
		for (String sName : ERNodes.keySet()) {
			ERNode node = ERNodes.get(sName);
			if (node.getPosition() != null) {
				node.getPosition().setLocation(
						node.getPosition().getX() - min.getX(),
						node.getPosition().getY() - min.getY());
			}

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.dnetlib.oaizer.ermanager.DB2ERAnalyzer#fromDB2ERJson()
	 */
	@Override
	public Map<String, List<Map<String, String>>> fromDB2ERResponse()
			throws SQLException {
		Map<String, ERNode> fromDB2ER = this.fromDB2ER();
		Map<String, List<Map<String, String>>> results = Maps.newHashMap();

		results.put("entities", new ArrayList<Map<String, String>>());
		results.put("relations", new ArrayList<Map<String, String>>());

		for (String s : fromDB2ER.keySet()) {
			ERNode sn = fromDB2ER.get(s);
			if (sn instanceof Entity) {
				results.get("entities").add(sn.toJson());
			} else if (sn instanceof Relation) {
				results.get("relations").add(sn.toJson());
			}
		}
		return results;
	}

}
