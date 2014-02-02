package fmi.uni.grading.shared.beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Represents a tutorial entry.
 * 
 * @see Tutorial
 * @author Martin Toshev
 */
@XmlType(name = "tutorialEntry")
// stored in the '_class' attribute instead of the class FQDN \
public class TutorialEntry {

	public enum EntryType {
		ARTICLE, PROBLEM
	};

	private EntryType type;

	private String ref;

	private String name;

	private String location;

	/**
	 * @return The type of the tutorial entry.
	 */
	@XmlElement(name = "type")
	public EntryType getType() {
		return type;
	}

	/**
	 * @param type
	 *            The type of the tutorial entry.
	 */
	public void setType(EntryType type) {
		this.type = type;
	}

	/**
	 * @return The reference to the entry data. For entries of type
	 *         {@link EntryType#ARTICLE} this is a reference to an article entry
	 *         stored in the application server (typically copied from a central
	 *         repository). For entries of type {@link EntryType#PROBLEM} this
	 *         is the id of the problem in the specified grader instance.
	 * 
	 */
	@XmlElement(name = "ref")
	public String getRef() {
		return ref;
	}

	/**
	 * @param ref
	 *            The reference to data of the tutorial entry.
	 * @see TutorialEntry#getRef()
	 */
	public void setRef(String ref) {
		this.ref = ref;
	}

	/**
	 * @return The name of the tutorial entry ('title' for articles and 'name'
	 *         for problems)
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            The tutorial entry name
	 * @see TutorialEntry#getName()
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return The location that provides the data for the tutorial entry. For
	 *         entries of type {@link EntryType#PROBLEM} this is the grader
	 *         instance id where the problem resides and for entries of type
	 *         {@link EntryType#ARTICLE} this is the repository where the
	 *         articles resides.
	 */
	@XmlElement(name = "location")
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            The location of the tutorial entry
	 * @see TutorialEntry#getGraderInstanceId()
	 */
	public void setLocation(String location) {
		this.location = location;
	}
}
