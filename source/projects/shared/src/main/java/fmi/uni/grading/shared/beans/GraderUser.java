package fmi.uni.grading.shared.beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a grader user.
 * 
 * @author Martin Toshev
 */
@XmlRootElement(name = "graderUser")
@Document(collection = "grader_users")
@TypeAlias("grader_user")
public class GraderUser {
	
	@Id
	private String id;
	
	private String handle;
	
	private String pass;

	@XmlElement(name="handle")
	public String getHandle() {
		return handle;
	}
	
	public void setHandle(String handle) {
		this.handle = handle;
	}
	
	@XmlElement(name="pass")
	public String getPass() {
		return pass;
	}
	
	public void setPass(String pass) {
		this.pass = pass;
	}
}
