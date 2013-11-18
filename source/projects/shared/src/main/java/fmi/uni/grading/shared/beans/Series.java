package fmi.uni.grading.shared.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a series (a course that encapsulates contests and may contain
 * sub-courses).
 * 
 * @author Martin Toshev
 */
@XmlRootElement(name = "series")
@Document(collection = "series")
@TypeAlias("series")
// stored in the '_class' attribute instead of the class FQDN \
public class Series {

	/**
	 * Default root series
	 */
	public static final Series ROOT = new Series();
	static {
		ROOT.setId("_ROOT_");
		ROOT.setTitle("ROOT");
		ROOT.setAbout("System default series");
	}

	@Id
	private String id;

	private String title;

	private String about;

	private List<String> notes;

	private List<String> contestOrder;

	private List<String> problemOrder;

	private String parent;

	@XmlElement(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement(name = "title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return Description of the series
	 */
	@XmlElement(name = "about")
	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	/**
	 * @return Additional sensitive information about the series
	 */
	public List<String> getNotes() {
		return notes;
	}

	public void setNotes(List<String> notes) {
		this.notes = notes;
	}

	/**
	 * @return The order of contests that are in the series
	 */
	public List<String> getContestOrder() {
		return contestOrder;
	}

	public void setContestOrder(List<String> contestOrder) {
		this.contestOrder = contestOrder;
	}

	/**
	 * @return The order of problem IDs that are in the series
	 */
	@XmlElement(name = "problem_order")
	public List<String> getProblemOrder() {
		return problemOrder;
	}

	public void setProblemOrder(List<String> problemOrder) {
		this.problemOrder = problemOrder;
	}

	@XmlElement(name = "parent")
	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}
}
