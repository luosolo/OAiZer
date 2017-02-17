package eu.dnetlib.oaizer.ERManager;

import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import eu.dnetlib.oaizer.db.inference.DatabaseInference;
import eu.dnetlib.oaizer.dbInformation.inference.postgres.DatabaseInferencePostgresImpl;
import eu.dnetlib.oaizer.ermanager.DB2ERAnalyzer;
import eu.dnetlib.oaizer.ermanager.DatabaseParameter;

public class DB2ERAnalyzerImplTest {

	static Logger log = Logger.getLogger(DB2ERAnalyzerImplTest.class.getName());
	DB2ERAnalyzer dbAnalyzer;

	@Before
	public void initialize() {
		log.setLevel(Level.INFO);
		DatabaseInference di = new DatabaseInferencePostgresImpl();
		DatabaseParameter params = new DatabaseParameter("test", "dnet",
				"dnetPwd", "oaizerdb", "localhost");
		di.setPropConnection(params);
		dbAnalyzer = new DB2ERAnalyzerImpl();
		dbAnalyzer.setDbInference(di);

	}

	@Test
	public void test() throws SQLException {
		assertNotNull(dbAnalyzer);
		List<String> filter = Lists.newArrayList("article", "uses");
		dbAnalyzer.addFilter(filter);
		// dbAnalyzer.fromDB2ERJson();
		// System.out.println(out);
	}

}
