package fmi.uni.grading.shared.beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a user of the system.
 * 
 * @author Martin Toshev
 */
@XmlRootElement(name = "user")
@Document(collection = "users")
@TypeAlias("user")
// stored in the '_class' attribute instead of the class FQDN \
public class User {

	private enum Role {
		CONTESTANT, TEACHER, ADMIN
	}

	private enum Permissions {
		READ_ONLY, WRITE_ONLY, READ_WRITE
	}

	@Id
	private String id;

	private String handle;

	private String name;

	private String pass;

	private Role role;

	private String details;

	private String rating;

	private Permissions permissions;

	@XmlElement(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return The user handle
	 */
	@XmlElement(name = "handle")
	public String getHandle() {
		return handle;
	}

	public void setHandle(String handle) {
		this.handle = handle;
	}

	/**
	 * @return The user name
	 */
	@XmlElement(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return The user password
	 */
	@XmlElement(name = "pass")
	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	/**
	 * @return The user role
	 */
	@XmlElement(name = "role")
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * @return The user details
	 */
	@XmlElement(name = "details")
	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	/**
	 * @return The user rating
	 */
	@XmlElement(name = "rating")
	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	/**
	 * @return The user persmissions
	 */
	@XmlElement(name = "permissions")
	public Permissions getPermissions() {
		return permissions;
	}

	public void setPermissions(Permissions permissions) {
		this.permissions = permissions;
	}
}
