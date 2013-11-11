package fmi.uni.grading.repository;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXBException;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import fmi.uni.grading.repository.configuration.Configuration;
import fmi.uni.grading.shared.util.JaxbManager;

/**
 * Main entry point for the repository.
 * 
 * @author Martin Toshev
 */
public class Repository {

	private static final String BEAN_ID_JAXRS_SERVER = "jaxrsServer";

	private static String FILE_CONTEXT = "spring.xml";

	private static String FILE_CONFIGURATION = "configuration.xml";

	private static final Logger LOGGER = Logger.getLogger(Repository.class
			.getName());

	private static final String LOCALHOST = "http://localhost";

	public static void main(String[] args) {

		LOGGER.info("Starting repository ... ");

		Configuration configuration = readConfiguration();
		
		if (configuration == null) {
			LOGGER.info("Failed to retrieve repository configuration. Stopping execution.");
		} else {
				startRepository(configuration);
		}

		LOGGER.info("Repository stopped.");
	}
	
	private static void startRepository(Configuration configuration) {

		RepositoryCache.init(configuration);

		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				FILE_CONTEXT);
		// starting the repository - the 'create' method is invoked as an init
		// method in the spring
		// configuration
		JAXRSServerFactoryBean repository = (JAXRSServerFactoryBean) ctx
				.getBean(BEAN_ID_JAXRS_SERVER);
		repository.setAddress(LOCALHOST + ":" + configuration.getPort() + "/"
				+ configuration.getRepositoryRoot());

		if (LOGGER.isDebugEnabled()) {
			repository.getBus().getInInterceptors().add(new LoggingInInterceptor());
			repository.getBus().getOutInterceptors()
					.add(new LoggingOutInterceptor());
		}

		repository.create();

		try {
			LOGGER.info("Press enter to exit server.");
			System.in.read();
		} catch (IOException e) {
			LOGGER.warn("Failed reading console input.", e);
		} finally {
			ctx.close();
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
