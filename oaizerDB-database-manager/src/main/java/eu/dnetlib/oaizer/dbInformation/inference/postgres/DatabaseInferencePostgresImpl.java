//***************************************************************************
// 
// File:         DatabaseInferencePostgresImpl.java
// Created:      May 15, 2012
// Author: 		 Sandro La Bruzzo
// Last Changed: May 15, 2012
// Author:       <A HREF="mailto:sandro.labruzzo@isti.cnr.it">sandro</A>
//
// 
//***************************************************************************
package eu.dnetlib.oaizer.dbInformation.inference.postgres;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import eu.dnetlib.oaizer.db.inference.Column;
import eu.dnetlib.oaizer.db.inference.DatabaseInference;
import eu.dnetlib.oaizer.db.inference.ForeignKey;
import eu.dnetlib.oaizer.db.inference.PrimaryKey;
import eu.dnetlib.oaizer.db.inference.Table;
import eu.dnetlib.oaizer.ermanager.DatabaseParameter;

/**
 * The postgres implementation of DatabaseInference
 */
public class DatabaseInferencePostgresImpl implements DatabaseInference {

	/** The connection. */
	Connection connection;

	/** The metadata info of the database. */
	DatabaseMetaData metadata;

	/** The prop connection. */
	DatabaseParameter propConnection;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.dnetlib.r2o.database.Information.inference.DatabaseInference#getTableInfo
	 * (java.lang.String)
	 */
	@Override
	public Table getTableInfo(String tableName) throws SQLException {
		if (metadata == null) {
			if (connection == null)
				connect();
			metadata = connection.getMetaData();
		}
		// Add column info
		ResultSet rsColumns = null;
		rsColumns = metadata.getColumns(null, null, tableName, null);
		Table t = new Table();
		t.setName(tableName);

		while (rsColumns.next()) {
			String columnName = rsColumns.getString("COLUMN_NAME");
			String columnType = rsColumns.getString("TYPE_NAME");
			int size = rsColumns.getInt("COLUMN_SIZE");
			int nullable = rsColumns.getInt("NULLABLE");
			boolean nullableBol = false;
			if (nullable == DatabaseMetaData.columnNullable) {
				nullableBol = true;
			}
			int position = rsColumns.getInt("ORDINAL_POSITION");
			t.addColumnInfo(new Column().setName(columnName)
					.setType(columnType).setNullable(nullableBol)
					.setPosition(position).setSize(size));
		}
		rsColumns.close();

		// Add primary key Info
		ResultSet rs = metadata.getPrimaryKeys(null, null, tableName);
		PrimaryKey pkey = null;
		while (rs.next()) {
			pkey = new PrimaryKey();
			String columnName = rs.getString("COLUMN_NAME");
			pkey.addPrimaryKey(columnName);
			pkey.setTable(t);
		}

		if (pkey != null)
			t.setPrimaryKey(pkey);
		// Add foreign Key Info
		rs = metadata.getImportedKeys(null, null, tableName);
		while (rs.next()) {
			String fkColumnName = rs.getString("FKCOLUMN_NAME");
			t.addForeignKey(new ForeignKey().addForeignkeyField(fkColumnName)
					.setTableRefer(tableName)
					.addPrimaryKeyReference(rs.getString("PKCOLUMN_NAME"))
					.setTableReference(rs.getString("PKTABLE_NAME")));
		}
		return t;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.dnetlib.r2o.database.Information.inference.DatabaseInference#getTables
	 * ()
	 */
	@Override
	public List<String> getTables() throws SQLException {
		if (metadata == null) {
			if (connection == null)
				connect();
			metadata = connection.getMetaData();
		}
		String[] TABLE_TYPES = { "TABLE" };
		ResultSet rs = metadata.getTables(null, "public", null, TABLE_TYPES);
		List<String> tables = new ArrayList<String>();
		while (rs.next()) {
			tables.add(rs.getString("table_name"));
		}
		rs.close();
		return tables;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.dnetlib.r2o.database.Information.inference.DatabaseInference#getConnection
	 * ()
	 */
	@Override
	public Connection getConnection() {
		return connection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.dnetlib.r2o.database.Information.inference.DatabaseInference#setConnection
	 * (java.sql.Connection)
	 */
	@Override
	public void setConnection(Connection connection) {
		this.connection = connection;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.dnetlib.r2o.database.Information.inference.DatabaseInference#
	 * getPropConnection()
	 */
	@Override
	public DatabaseParameter getPropConnection() {
		return propConnection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.dnetlib.r2o.database.Information.inference.DatabaseInference#
	 * setPropConnection(java.util.Properties)
	 */
	@Override
	public void setPropConnection(DatabaseParameter propConnection) {
		this.propConnection = propConnection;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.dnetlib.r2o.database.Information.inference.DatabaseInference#connect()
	 */
	@Override
	public void connect() throws SQLException {
		// Create properties
		Properties p = new Properties();
		p.setProperty("user", propConnection.getLogin());
		p.setProperty("password", propConnection.getPassword());

		String URLDbconnection = "jdbc:postgresql://"
				+ propConnection.getHostname() + "/"
				+ propConnection.getDatabase();
		connection = DriverManager.getConnection(URLDbconnection, p);

	}

	@Override
	public boolean existTable(String tableName) throws SQLException {
		if (metadata == null) {
			if (connection == null)
				connect();
			metadata = connection.getMetaData();
		}
		// Add column info
		ResultSet rsColumns = null;
		rsColumns = metadata.getColumns(null, null, tableName, null);
		if (rsColumns.next()) {
			rsColumns.close();
			return true;
		}
		rsColumns.close();
		return false;
	}

}
