package fmi.uni.grading.shared.beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a grader instance.
 * 
 * @author Martin Toshev
 */
@XmlRootElement(name = "graderInstance")
@Document(collection = "grader_instances")
@TypeAlias("grader_instance")
// stored in the '_class' attribute instead of the class FQDN \
public class GraderInstance {

	@Id
	private String id;

	private String type;

	private String name;

	private String url;

	private Boolean adminEnabled;

	private Boolean requresPass = true;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return The type of the grader instance
	 */
	@XmlElement(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return The name of the grader instance.
	 */
	@XmlElement(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return The root URL of the grader instance
	 */
	@XmlElement(name = "url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return <b>true</b> if administration for the grader instance is enabled
	 *         and false otherwise
	 */
	@XmlElement(name = "adminEnabled")
	public Boolean isAdminEnabled() {
		return adminEnabled;
	}

	public void setAdminEnabled(Boolean adminEnabled) {
		this.adminEnabled = adminEnabled;
	}

	@XmlElement(name = "requiresPass")
	public Boolean getRequresPass() {
		return requresPass;
	}

	public void setRequresPass(Boolean requresPass) {
		this.requresPass = requresPass;
	}
}
