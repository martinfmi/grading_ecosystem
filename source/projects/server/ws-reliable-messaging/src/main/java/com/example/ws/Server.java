package com.example.ws;

import java.net.URL;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.EndpointImpl;

import com.example.ws.soap.services.CapitalizeService;

/**
 * @author Martin
 */
public class Server {

	public static void main(String[] args) throws InterruptedException {

		System.out.println("Starting Server ... ");

		SpringBusFactory bf = new SpringBusFactory();
		URL busFile = Server.class.getResource("/server.xml");
		Bus bus = bf.createBus(busFile.toString());

		BusFactory.setDefaultBus(bus);

		CapitalizeService implementor = new CapitalizeService();

		// WSDL is at http://localhost:9000/ws?wsdl
		String address = "http://localhost:9000/ws";
		EndpointImpl endpoint = (EndpointImpl) Endpoint.publish(address,
				implementor);

		endpoint.getServer().getEndpoint().getInInterceptors()
				.add(new LoggingInInterceptor());
		endpoint.getServer().getEndpoint().getOutInterceptors()
				.add(new LoggingOutInterceptor());

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
}
