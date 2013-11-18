package fmi.uni.grading.shared.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a programming tutorial as a collection of article/problem entries.
 * 
 * @author Martin Toshev
 */
@XmlRootElement(name = "tutorial")
@Document(collection = "tutorials")
@TypeAlias("tutorial")
// stored in the '_class' attribute instead of the class FQDN \
public class Tutorial {

	@Id
	private String id;

	private String name;

	private List<TutorialEntry> entries;
	
	@XmlElement(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@XmlElement(name="name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return The content of the tutorial (list of {@link TutorialEntry}
	 *         instances)
	 */
	@XmlElement(name="entries")
	public List<TutorialEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<TutorialEntry> entries) {
		this.entries = entries;
	}
}
