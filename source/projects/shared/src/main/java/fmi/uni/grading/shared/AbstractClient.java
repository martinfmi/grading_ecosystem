package fmi.uni.grading.shared;

import java.util.LinkedList;
import java.util.List;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.client.Client;
import org.apache.cxf.jaxrs.client.ClientConfiguration;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.provider.AbstractJAXBProvider;
import org.apache.cxf.jaxrs.provider.json.JSONProvider;
import org.apache.log4j.Logger;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;

import fmi.uni.grading.shared.exceptions.handlers.client.ClientErrorHandler;

/**
 * This abstract class provides common logic for all server clients.
 * 
 * @param <T>
 *            The service interface type.
 * @author Martin Toshev
 */
public class AbstractClient<T> {

	private static final Logger LOGGER = Logger.getLogger(AbstractClient.class
			.getName());

	// private static final String FILE_CONTEXT = "spring.xml";

	// static {
	// SpringBusFactory bf = new SpringBusFactory();
	// URL busFile = AbstractClient.class.getResource("/" + FILE_CONTEXT);
	// Bus bus = bf.createBus(busFile.toString());
	// BusFactory.setDefaultBus(bus);
	// }

	private T service;

	private String authHeader;

	/**
	 * @param url
	 *            Service endpoint base address.
	 * @param clazz
	 *            Service interface class.
	 */
	public AbstractClient(String url, Class<T> clazz, String user,
			String password) {

		List<Object> providers = new LinkedList<Object>();
		providers.add(new JacksonJaxbJsonProvider());
		providers.add(new ClientErrorHandler());
		
		service = JAXRSClientFactory.create(url, clazz, providers, false);
		authHeader = "Basic "
				+ org.apache.cxf.common.util.Base64Utility
				.encode((user + ":" + password).getBytes());

		Client client = WebClient.client(service);
		client.header("Authorization", authHeader);
		// must be explicitly set or otherwise defaults to keep-alive
		client.header("connection", "close");
		
		if (LOGGER.isDebugEnabled()) {
			ClientConfiguration config = WebClient.getConfig(client);
			
			config.getOutInterceptors().add(new LoggingOutInterceptor());
			config.getInInterceptors().add(new LoggingInInterceptor());
		}
	}

	public T getService() {
		return service;
	}

	public String getAuthHeader() {
		return authHeader;
	}
}
