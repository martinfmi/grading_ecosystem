package fmi.uni.grading.server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import fmi.uni.grading.server.configuration.Configuration;
import fmi.uni.grading.server.db.GraderDAO;
import fmi.uni.grading.server.grader.Grader;
import fmi.uni.grading.shared.beans.GraderInstance;
import fmi.uni.grading.shared.util.JaxbManager;

/**
 * Main entry point for the server.
 * 
 * @author Martin Toshev
 */
public class Server {

	@Autowired
	private static GraderDAO graderDAO;

	private static final String BEAN_ID_JAXRS_SERVER = "jaxrsServer";

	private static String FILE_CONTEXT = "spring.xml";

	private static String FILE_CONFIGURATION = "configuration.xml";

	private static final Logger LOGGER = Logger.getLogger(Server.class
			.getName());

	private static final String LOCALHOST = "http://localhost";

	public static void main(String[] args) {

		LOGGER.info("Starting server ... ");

		Configuration configuration = readConfiguration();

		if (configuration == null) {
			LOGGER.info("Failed to retrieve server configuration. Stopping execution.");
		} else {
			startMaster(configuration);
		}

		LOGGER.info("Server stopped.");
	}

	private static void startMaster(Configuration configuration) {

		LOGGER.info("Configuring server ... ");
		ServerCache.init(configuration);

		LOGGER.info("Loading applications ... ");
		loadApplications();

		LOGGER.info("Setting up grader instances from configuration ...");
		loadGraderInstances();

		LOGGER.info("Setting up services ... ");
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				FILE_CONTEXT);
		// starting the server - the 'create' method is invoked as an init
		// method in the spring
		// configuration
		JAXRSServerFactoryBean server = (JAXRSServerFactoryBean) ctx
				.getBean(BEAN_ID_JAXRS_SERVER);
		server.setAddress(LOCALHOST + ":" + configuration.getPort() + "/"
				+ configuration.getServerRoot());

		if (LOGGER.isDebugEnabled()) {
			server.getBus().getInInterceptors().add(new LoggingInInterceptor());
			server.getBus().getOutInterceptors()
					.add(new LoggingOutInterceptor());
		}

		server.create();

		try {
			LOGGER.info("Press enter to exit server.");
			System.in.read();
		} catch (IOException e) {
			LOGGER.warn("Failed reading console input.", e);
		} finally {
			ctx.close();
		}
	}

	private static void loadGraderInstances() {

		// load from configuration and database
		List<GraderInstance> instances = ServerCache.getConfiguration()
				.getGraderInstances();
		instances.addAll(graderDAO.getGraderInstances());
		
		for (GraderInstance instance : instances) {
			if (ServerCache.getGrader(instance.getType()) == null) {
				throw new ServerError(
						"No grader with type: "
								+ instance.getType()
								+ " is present in system."
								+ "Please check the grader instances in the configuration.");
			}

			if (instance.getId() == null) {
				throw new ServerError(
						"No grader instance id provided. Please check your configuration.");
			}

			if (instance.getName() == null) {
				throw new ServerError(
						"No grader name provided. Please check your configuration.");
			}

			if (instance.getUrl() == null) {
				throw new ServerError(
						"No grader URL. Please check your configuration.");
			}

			ServerCache.addGraderInstance(instance);
		}
	}

	private static void loadApplications() {

		URL appsDir = Server.class.getResource("/" + Configuration.APPS_DIR);
		if (appsDir == null) {
			throw new ServerError("No " + Configuration.APPS_DIR
					+ " directory found in the server root.");
		}

		File file;
		try {
			file = new File(appsDir.toURI());
		} catch (URISyntaxException e) {
			throw new ServerError(e);
		}
		if (!file.isDirectory()) {
			throw new ServerError(Configuration.APPS_DIR
					+ " is not a directory.");
		}

		File[] apps = file.listFiles();
		for (File app : apps) {
			AppClassLoader loader = null;
			URL appUrl = null;
			try {
				appUrl = app.toURI().toURL();
				loader = new AppClassLoader(app.toURI().toURL());
				Grader grader = loader.loadGrader();
				if (grader.getGraderType() == null) {
					throw new ServerError(
							"Grader type must not be null for app at URL: "
									+ appUrl.toString());
				}

				ServerCache.addGrader(grader.getGraderType(), grader);

			} catch (MalformedURLException e) {
				throw new ServerError(
						"Failed to retrieve URL from app file with name: "
								+ file.getName());
			} finally {
				if (loader != null) {
					try {
						loader.close();
					} catch (IOException e) {
						throw new ServerError(
								"Failed to close app classloader for application with URL: "
										+ appUrl.toString());
					}
				}
			}

		}

	}

	private static Configuration readConfiguration() {
		Configuration configuration = null;

		InputStream is;
		try {
			is = Server.class.getResource("/" + FILE_CONFIGURATION)
					.openStream();
			configuration = JaxbManager.read(Configuration.class, is);
		} catch (IOException e) {
			LOGGER.error("Failed to read server configuration file.", e);
		} catch (JAXBException e) {
			LOGGER.error("Failed to parse server configuration file.", e);
		}

		return configuration;
	}
}
