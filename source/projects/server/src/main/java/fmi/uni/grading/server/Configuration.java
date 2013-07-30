package fmi.uni.grading.server;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Configuration bean for storing the various configurable server properties (as
 * specified in the server configuration file).
 * 
 * @author Martin Toshev
 */
@XmlRootElement(name = "server")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Configuration {

	public static final String TYPE_MASTER = "MASTER";

	public static final String TYPE_SLAVE = "SLAVE";

	private String serverType = TYPE_MASTER;

	private int port = 3434;

	public void setServerType(String serverType) {
		this.serverType = serverType;
	}

	@XmlElement(name = "type")
	public String getServerType() {
		return serverType;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@XmlElement(name = "port")
	public int getPort() {
		return port;
	}
}
