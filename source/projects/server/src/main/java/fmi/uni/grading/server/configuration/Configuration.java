package fmi.uni.grading.server.configuration;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import fmi.uni.grading.shared.beans.GraderInstance;

/**
 * Configuration bean for storing the various configurable server properties (as
 * specified in the server configuration file).
 * 
 * @author Martin Toshev
 */
@XmlRootElement(name = "server")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Configuration {

	public static final String APPS_DIR = "apps";

	public static final String AUTH_TYPE_BASIC = "Basic";

	private int port = 3434;

	private String authType = "Basic";

	private String serverRoot = "/services";

	private String dbName = "Grading_System";

	private List<MongoInstance> mongoInstances;

	private List<GraderInstance> graderInstances;
	
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

	public void setServerRoot(String serverRoot) {
		this.serverRoot = serverRoot;
	}

	@XmlElement(name = "serverRoot")
	public String getServerRoot() {
		return serverRoot;
	}

	@XmlElementWrapper(name = "mongoReplica")
	@XmlElement(name = "instance")
	public List<MongoInstance> getMongoInstances() {
		return mongoInstances;
	}

	public void setMongoInstances(List<MongoInstance> mongoInstances) {
		this.mongoInstances = mongoInstances;
	}

	@XmlElementWrapper(name = "graderInstances")
	@XmlElement(name = "instance")
	public List<GraderInstance> getGraderInstances() {
		return graderInstances;
	}

	public void setGraderInstances(List<GraderInstance> graderInstances) {
		this.graderInstances = graderInstances;
	}
}
