package com.example.ws.beans;

import javax.xml.bind.annotation.XmlType;

/**
 * @author Martin
 */
@XmlType(name="message")
public class Message implements IMessage {
	
	private String text;
	
	private String author;
	
	public String getText() {
		return text;
	}
	
	public void setText(String name) {
		this.text = name;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
}
