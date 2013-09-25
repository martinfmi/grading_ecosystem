package fmi.uni.grading.server.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Configuration bean for a database (MongoDB) instance.
 * 
 * @author Martin Toshev
 */
@XmlRootElement(name = "instance")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class MongoInstance {

	private String host;

	private int port = 27017;

	public void setHost(String host) {
		this.host = host;
	}

	@XmlElement(name = "host")
	public String getHost() {
		return host;
	}

	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return The port on which the instance runs. Defaults to 27017.
	 */
	@XmlElement(name = "port")
	public int getPort() {
		return port;
	}
}
