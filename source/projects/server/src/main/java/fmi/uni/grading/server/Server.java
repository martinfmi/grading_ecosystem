package fmi.uni.grading.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.spring.SpringBusFactory;

import fmi.uni.grading.shared.util.JaxbManager;

/**
 * Main entry point for the server.
 * 
 * @author Martin Toshev
 */
public class Server {

	private static String FILE_CONTEXT = "spring.xml";

	private static String FILE_CONFIGURATION = "configuration.xml";

	private static final Logger LOGGER = Logger.getLogger(Server.class
			.getName());

	public static void main(String[] args) {

		LOGGER.info("Starting Server ... ");

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

	private static Configuration readConfiguration() {
		Configuration configuration = null;

		InputStream is;
		try {
			is = Server.class.getResource("/" + FILE_CONFIGURATION)
					.openStream();
			configuration = JaxbManager.read(Configuration.class, is);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE,
					"Failed to read server configuration file.", e);
		} catch (JAXBException e) {
			LOGGER.log(Level.SEVERE,
					"Failed to parse server configuration file.", e);
		}

		return configuration;
	}
}
