package com.example.ws;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.spring.SpringBusFactory;

import com.example.ws.beans.IMessage;
import com.example.ws.beans.Message;
import com.example.ws.soap.services.ICapitalizeService;

public class Client {

	private static final QName SERVICE_NAME = new QName(
			"http://services.soap.ws.example.com/", "CapitalizeMessage");
	private static final QName PORT_NAME = new QName(
			"http://services.soap.ws.example.com/", "CapitalizeServicePort");

	private Client() {
	}

	public static void main(String args[]) throws Exception {
		SpringBusFactory bf = new SpringBusFactory();
		URL busFile = Client.class.getResource("/client.xml");
		Bus bus = bf.createBus(busFile.toString());
		BusFactory.setDefaultBus(bus);

		bus.getOutInterceptors().add(new MessageLossSimulator());

		Service service = Service.create(SERVICE_NAME);
		// Endpoint Address
		String endpointAddress = "http://localhost:9000/ws";

		// Add a port to the Service
		service.addPort(PORT_NAME, SOAPBinding.SOAP11HTTP_BINDING,
				endpointAddress);

		ICapitalizeService capitalizeService = service.getPort(PORT_NAME,
				ICapitalizeService.class);
		System.out
				.println(capitalizeService.capitalize("string to capitalize"));

		IMessage message = new Message();
		message.setText("message text to be capitalized");
		System.out.println(capitalizeService.capitalizeMessage(message));
	}
}
