package fmi.uni.grading.shared.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a programming problem.
 * 
 * @author Martin Toshev
 */
@XmlRootElement(name = "problem")
@Document(collection = "problems")
@TypeAlias("problem")
// stored in the '_class' attribute instead of the class FQDN \
public class Problem {

	@Id
	private String id;

	private String type;

	private String title;

	private String description;

	private Long timeLimit;

	private Long memoryLimit;

	private String origin;

	private List<String> categories;

	private List<String> authors;

	/**
	 * Retrieves the problem ID once the problem is persisted in the repository.
	 * 
	 * @return The problem ID.
	 */
	@XmlElement(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Sets the type of the problem. The type is provided by the expected input.
	 * Standard algorithm problems that require full problem submissions might
	 * have a type of "standard" while quiz problem might have a type of "quiz".
	 * Other problem types might be derived from the particular artifact being
	 * submitted - e.g. "block", "method", "class". Types are not restricted by
	 * the system.
	 * 
	 * @return The type of the problem.
	 */
	@XmlElement(name = "type")
	public String getType() {
		return type;
	}

	/**
	 * Sets the type of a problem.
	 * 
	 * @param type
	 *            Problem type.
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return The title of the problem.
	 */
	@XmlElement(name = "title")
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title of a problem.
	 * 
	 * @param title
	 *            Problem title.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return The problem description with (possibly) HTML markup for display.
	 */
	@XmlElement(name = "description")
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the problem's description. Might contain HTML markup for display.
	 * 
	 * @param description
	 *            The problem description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return The time limit for the problem in milliseconds.
	 */
	@XmlElement(name = "time_limit")
	public Long getTimeLimit() {
		return timeLimit;
	}

	/**
	 * Sets the time limit for a problem.
	 * 
	 * @param timeLimit
	 *            Time limit in milliseconds.
	 */
	public void setTimeLimit(Long timeLimit) {
		this.timeLimit = timeLimit;
	}

	/**
	 * @return The memory limit for the problem in megabytes.
	 */
	@XmlElement(name = "memory_limit")
	public Long getMemoryLimit() {
		return memoryLimit;
	}

	/**
	 * Sets the memory limit of a problem.
	 * 
	 * @param memoryLimit
	 *            Memory limit for the problem in megabytes.
	 */
	public void setMemoryLimit(long memoryLimit) {
		this.memoryLimit = memoryLimit;
	}

	/**
	 * @return The origin of the problem.
	 */
	@XmlElement(name = "origin")
	public String getOrigin() {
		return origin;
	}

	/**
	 * Sets the origin of a problem. This could be a particular grading system,
	 * book or other source from which the problem originated.
	 * 
	 * @param origin
	 *            Problem origin.
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * @return The problem' categories (e.g. "graphs", "dynamic programming").
	 */
	@XmlElement(name = "categories")
	public List<String> getCategories() {
		return categories;
	}

	/**
	 * @param categories
	 *            Sets the problem's categories.
	 */
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	/**
	 * @return Retrieves the problem's authors.
	 */
	@XmlElement(name = "authors")
	public List<String> getAuthors() {
		return authors;
	}

	/**
	 * Sets the problem's authors.
	 * 
	 * @param authors
	 *            Problem's authors.
	 */
	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}

	@Override
	public String toString() {
		String result = "[type = " + type + ",\\n" //
				+ "title = " + title + ",\\n" //
				+ "description = " + description + ",\\n" //
				+ "timeLimit = " + timeLimit + ",\\n" //
				+ "memoryLimit = " + memoryLimit + ",\\n" //
				+ "origin = " + memoryLimit + ",\\n" //
				+ "categories = " + categories + ",\\n" //
				+ "authors = " + categories + "]\\n"; //

		return result;
	}
}
