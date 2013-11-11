package fmi.uni.grading.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import javax.xml.bind.JAXBException;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import fmi.uni.grading.server.configuration.Configuration;
import fmi.uni.grading.shared.util.JaxbManager;

/**
 * Main entry point for the server.
 * 
 * @author Martin Toshev
 */
public class Server {

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
			String serverType = configuration.getServerType();
			if (Configuration.TYPE_MASTER.equalsIgnoreCase(serverType)) {
				startMaster(configuration);
			} else if (Configuration.TYPE_SLAVE.equalsIgnoreCase(serverType)) {
				startSlave(configuration);
			} else {
				LOGGER.info("Unknown server type. Stopping execution ...");
			}
		}

		LOGGER.info("Server stopped.");
	}

	private static void startSlave(Configuration configuration) {

		LOGGER.info("Instance type is SLAVE.");

		SpringBusFactory bf = new SpringBusFactory();
		URL busFile = Server.class.getResource("/" + FILE_CONTEXT);
		Bus bus = bf.createBus(busFile.toString());

		BusFactory.setDefaultBus(bus);

		// CapitalizeService implementor = new CapitalizeService();
		
		// WSDL is at http://localhost:9000/ws?wsdl
		String address = "http://localhost:9000/ws";
		// EndpointImpl endpoint = (EndpointImpl) Endpoint.publish(address,
		// implementor);
		
		// endpoint.getServer().getEndpoint().getInInterceptors()
		// .add(new LoggingInInterceptor());
		// endpoint.getServer().getEndpoint().getOutInterceptors()
		// .add(new LoggingOutInterceptor());

		// RMOutInterceptor rmOut = new RMOutInterceptor();
		// RMInInterceptor rmIn = new RMInInterceptor();
		// RMSoapInterceptor soapInterceptor = new RMSoapInterceptor();
		//
		// System.out.println("Server ready...");
		//
		// Thread.sleep(5 * 60 * 1000);
		// System.out.println("Server exiting");
		// System.exit(0);
	}

	private static void startMaster(Configuration configuration) {

		LOGGER.info("Instance type is MASTER.");

		ServerCache.init(configuration);

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

		// Bus bus = bf.createBus(busFile.toString());
		//
		// BusFactory.setDefaultBus(bus);

		// CapitalizeService implementor = new CapitalizeService();

		// WSDL is at http://localhost:9000/ws?wsdl
		String address = "http://localhost:9000/ws";
		// EndpointImpl endpoint = (EndpointImpl) Endpoint.publish(address,
		// implementor);

		// endpoint.getServer().getEndpoint().getInInterceptors()
		// .add(new LoggingInInterceptor());
		// endpoint.getServer().getEndpoint().getOutInterceptors()
		// .add(new LoggingOutInterceptor());

		// RMOutInterceptor rmOut = new RMOutInterceptor();
		// RMInInterceptor rmIn = new RMInInterceptor();
		// RMSoapInterceptor soapInterceptor = new RMSoapInterceptor();
		//
		// System.out.println("Server ready...");
		//
		// Thread.sleep(5 * 60 * 1000);
		// System.out.println("Server exiting");
		// System.exit(0);
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
