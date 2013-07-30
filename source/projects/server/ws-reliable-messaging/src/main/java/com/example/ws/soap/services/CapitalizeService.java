package com.example.ws.soap.services;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.example.ws.beans.IMessage;

@WebService(endpointInterface = "com.example.ws.soap.services.ICapitalizeService",
serviceName = "CapitalizeMessage")
public class CapitalizeService implements ICapitalizeService {

	public String capitalize(@WebParam(name = "text") String text) {
		return text.toUpperCase();
	}

	public String capitalizeMessage(IMessage message) {
		return message.getText().toUpperCase();
	}
}

