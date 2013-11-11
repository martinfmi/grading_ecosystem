package fmi.uni.grading.shared.beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Represents an article.
 * 
 * @author Martin Toshev
 */
@XmlType(name = "tutorial_entry")
// stored in the '_class' attribute instead of the class FQDN \
public class TutorialEntry {

	private enum EntryType {
		ARTICLE, PROBLEM
	};

	private EntryType type;

	private String ref;

	private String graderInstanceId;

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
	@XmlElement(name="ref")
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
	 * @return The grader instance that provides the data for the tutorial
	 *         entry. Applies only for entries of type {@link EntryType#PROBLEM}
	 */
	@XmlElement(name="grader_instance_id")
	public String getGraderInstanceId() {
		return graderInstanceId;
	}

	/**
	 * @param graderInstanceId
	 *            The grader instance ID
	 * @see TutorialEntry#getGraderInstanceId()
	 */
	public void setGraderInstanceId(String graderInstanceId) {
		this.graderInstanceId = graderInstanceId;
	}
}
