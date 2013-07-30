package com.example.ws.soap.services;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.example.ws.beans.IMessage;

@WebService
public interface ICapitalizeService {

	public String capitalize(@WebParam(name = "text") String text);

	/*
	 * Advanced use case of passing an interface. Special XmlAdapter classes
	 * need to be written in order to handle interfaces since JAX-WS/JAXB does
	 * not handle them.
	 */
	public String capitalizeMessage(IMessage message);
}
