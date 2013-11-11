package fmi.uni.grading.repository.configuration;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
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

	public static final String AUTH_TYPE_BASIC = "Basic";

	private String serverType = TYPE_MASTER;

	private int port = 3434;

	private String authType = "Basic";

	private String repositoryRoot = "/services";

	private String dbName = "Grading_System";

	private List<MongoInstance> mongoInstances;
	
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

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	@XmlElement(name = "auth")
	public String getAuthType() {
		return authType;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	@XmlElement(name = "dbName")
	public String getDbName() {
		return dbName;
	}

	public void setRepositoryRoot(String repositoryRoot) {
		this.repositoryRoot = repositoryRoot;
	}

	@XmlElement(name = "repositoryRoot")
	public String getRepositoryRoot() {
		return repositoryRoot;
	}

	@XmlElementWrapper(name = "mongoReplica")
	@XmlElement(name = "instance")
	public List<MongoInstance> getMongoInstances() {
		return mongoInstances;
	}

	public void setMongoInstances(List<MongoInstance> mongoInstances) {
		this.mongoInstances = mongoInstances;
	}
}
