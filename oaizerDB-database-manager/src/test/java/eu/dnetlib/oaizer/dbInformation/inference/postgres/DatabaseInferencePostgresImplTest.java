/**
 * 
 */
package eu.dnetlib.oaizer.dbInformation.inference.postgres;

import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import eu.dnetlib.oaizer.db.inference.Column;
import eu.dnetlib.oaizer.db.inference.DatabaseInference;
import eu.dnetlib.oaizer.db.inference.ForeignKey;
import eu.dnetlib.oaizer.db.inference.Table;
import eu.dnetlib.oaizer.ermanager.DatabaseParameter;

/**
 * The Class DatabaseInferencePostgresImplTest.
 * 
 * @author sandro
 */
public class DatabaseInferencePostgresImplTest {

	/** The di. */
	DatabaseInference di;

	/**
	 * Sets the up.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Before
	public void setUp() throws Exception {
		di = new DatabaseInferencePostgresImpl();
		DatabaseParameter params = new DatabaseParameter("test", "dnet",
				"dnetPwd", "oaizerdb", "localhost");
		di.setPropConnection(params);

	}

	/**
	 * List tablestest.
	 * 
	 * @throws SQLException
	 *             the sQL exception
	 */
	@Test
	public void ListTablestest() throws SQLException {

		List<String> list = di.getTables();
		System.out.println("total number " + list.size());
		for (String s : list) {
			// System.out.println(s);
			TableInfoTest(s);
		}
		assertNotNull(list);
	}

	/**
	 * Table info test.
	 * 
	 * @param name
	 *            the name
	 * @throws SQLException
	 *             the sQL exception
	 */
	private void TableInfoTest(String name) throws SQLException {
		Table t = di.getTableInfo(name);
		assertNotNull(t);
		System.out.println("table :" + t.getName());
		if (t.getColumnInfo() != null)
			for (Column ci : t.getColumnInfo()) {
				System.out.println("Attr : " + ci.getName() + "  type: "
						+ ci.getType() + " size: " + ci.getSize());
			}
		if (t.getPrimaryKey() != null) {
			System.out.print("Primary Keys :");
			for (String s : t.getPrimaryKey().getPrimaryKeys())
				System.out.print(" " + s);
			System.out.println();
		}
		if (t.getForeignKey() != null) {
			for (ForeignKey ff : t.getForeignKey()) {
				System.out.print("Fkey from schedules (");
				for (String sf : ff.getForeignkeyField()) {
					System.out.print(" " + sf + " ");
				}
				System.out.print(") to " + ff.getTableReference() + "( ");
				for (String sf : ff.getPrimaryKeyReference()) {
					System.out.print(" " + sf + ", ");
				}
				System.out.println(")");
			}
		}

	}

}
