package fmi.uni.grading.shared.beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a problem/article category (e.g. "dynamic programming" or
 * "graph theory").
 * 
 * @author Martin Toshev
 */
@XmlRootElement(name = "category")
@Document(collection = "categories")
@TypeAlias("category")
// stored in the '_class' attribute instead of the class FQDN \
public class Category {

	@Id
	private String id;

	private String name;

	private String decription;

	private String parent;

	@XmlElement(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(name = "description")
	public String getDecription() {
		return decription;
	}

	public void setDecription(String decription) {
		this.decription = decription;
	}

	@XmlElement(name = "parent")
	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}
}
