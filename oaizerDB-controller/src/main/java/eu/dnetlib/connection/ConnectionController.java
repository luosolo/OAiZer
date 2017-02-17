package eu.dnetlib.connection;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.gson.Gson;

import eu.dnetlib.oaizer.ERManager.DB2ERAnalyzerImpl;
import eu.dnetlib.oaizer.db.inference.DatabaseInference;
import eu.dnetlib.oaizer.dbInformation.inference.postgres.DatabaseInferencePostgresImpl;
import eu.dnetlib.oaizer.ermanager.DB2ERAnalyzer;
import eu.dnetlib.oaizer.ermanager.DatabaseParameter;
import eu.dnetlib.session.ConnectionInfo;
import eu.dnetlib.session.UserSession;

/**
 * The Class ConnectionController.
 */
@Controller
@RequestMapping("/**/connection")
public class ConnectionController {

	/** The session. */
	UserSession session;

	/**
	 * Login.
	 * 
	 * @param model
	 *            the model
	 * @param response
	 *            the response
	 * @param user
	 *            the user
	 */
	@RequestMapping(value = "/connect.do", method = RequestMethod.POST)
	public void Login(ModelMap model, HttpServletResponse response,
			@RequestParam(value = "user") String user) {
		response.setContentType("application/json; charset=UTF-8");

		try {
			response.setContentType("application/json");

			response.getWriter().write(
					"{\"logged\":\"yes\", \"user\":\"" + user + "\"}");
			if (session == null) {
				session = new UserSession();
			}
			session.setUserName(user);
		} catch (IOException e) {
		}
	}

	@RequestMapping(value = "/saveConnection.do", method = RequestMethod.POST)
	public void Filter(Model model, HttpServletResponse response,
			@RequestParam(value = "filter") String filter,
			@RequestParam(value = "connection") String connection) {

		Gson g = new Gson();
		if (session == null) {
			session = new UserSession();
		}

		DatabaseParameter dp = g.fromJson(connection, DatabaseParameter.class);
		String[] filterTables = g.fromJson(filter, String[].class);

		if (filterTables != null && filterTables.length > 0) {

			session.createSession(dp.getConnectionName(), dp);
			ConnectionInfo cInfo = session.getInfoForConnection(dp
					.getConnectionName());
			cInfo.addEntities(Lists.newArrayList(filterTables));
		}
	}

	/**
	 * Connect to a database.
	 * 
	 * @param model
	 *            the model
	 * @param response
	 *            the response
	 * @param parameters
	 *            the parameters
	 * @throws SQLException
	 */
	@RequestMapping(value = "/connectDB.do", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, List<Map<String, String>>> connect2Database(
			@RequestParam(value = "parameters") String parameters)
			throws SQLException {
		try {
			Gson g = new Gson();
			DatabaseParameter dp = g.fromJson(parameters,
					DatabaseParameter.class);
			DatabaseInference di = new DatabaseInferencePostgresImpl();
			di.setPropConnection(dp);
			DB2ERAnalyzer dbAnalyzer = new DB2ERAnalyzerImpl();
			dbAnalyzer.setDbInference(di);

			Map<String, List<Map<String, String>>> s = dbAnalyzer
					.fromDB2ERResponse();
			return s;
		} catch (Exception e) {

			return null;
		}
	}

	/**
	 * Return the list of the name of Connections.
	 * 
	 * @return the list
	 */
	@RequestMapping(value = "/connectionList.do", method = RequestMethod.GET)
	public @ResponseBody
	List<String> connectionList() {
		if (session == null)
			return null;
		return session.getConnectionList();

	}

	@RequestMapping(value = "/loadGraph.do", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, List<Map<String, String>>> loadGraph(
			@RequestParam(value = "connectionName") String connectionName) {
		try {
			if (session == null)
				return null;
			ConnectionInfo connectionInfo = session
					.getInfoForConnection(connectionName);
			DatabaseInference di = new DatabaseInferencePostgresImpl();
			di.setPropConnection(connectionInfo.getConnectionParameterInfo());
			DB2ERAnalyzer dbAnalyzer = new DB2ERAnalyzerImpl();
			dbAnalyzer.setDbInference(di);

			dbAnalyzer.addFilter(connectionInfo.getEntities());
			Map<String, List<Map<String, String>>> s = null;
			s = dbAnalyzer.fromDB2ERResponse();
			return s;
		} catch (SQLException e1) {
		}

		return null;
	}

	@RequestMapping(value = "/exportView.do", method = RequestMethod.POST)
	public @ResponseBody
	String exportView(@RequestParam(value = "view") String view) {
		Gson g = new Gson();

		List<String[]> test = g.fromJson(view, List.class);

		System.out.println(test.size());
		return "OK";

	}
}
