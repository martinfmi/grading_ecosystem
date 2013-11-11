package fmi.uni.grading.shared.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a programming contest.
 * 
 * @author Martin Toshev
 */
@XmlRootElement(name = "checker")
@Document(collection = "checkers")
@TypeAlias("checker")
// stored in the '_class' attribute instead of the class FQDN \
public class Contest {

	@Id
	private String id;

	private String type;

	private String title;

	private String startTime;

	private int duration;

	private String about;

	private String gradingStyle;

	private List<String> problemOrder;

	private List<String> problemScores;

	@XmlElement(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@XmlElement(name = "title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return The start time of the contest in the format:
	 *         "yyyy-MM-dd hh:mm:ss"
	 */
	@XmlElement(name = "start_time")
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            The start time of the contest
	 * @see Contest#getStartTime()
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return The duration of the contest in seconds
	 */
	@XmlElement(name = "duration")
	public int getDuration() {
		return duration;
	}

	/**
	 * @param duration
	 *            The duration of the contest in seconds
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * @return Information about the contest
	 */
	@XmlElement(name = "about")
	public String getAbout() {
		return about;
	}

	/**
	 * @param about
	 *            Information about the contest
	 */
	public void setAbout(String about) {
		this.about = about;
	}

	/**
	 * @return The grading style for the contest (e.g. "ioi" or "acm")
	 */
	@XmlElement(name = "grading_style")
	public String getGradingStyle() {
		return gradingStyle;
	}

	public void setGradingStyle(String gradingStyle) {
		this.gradingStyle = gradingStyle;
	}

	/**
	 * @return The problem order for the contest (list of problem IDs)
	 */
	@XmlElement(name = "problem_order")
	public List<String> getProblemOrder() {
		return problemOrder;
	}

	/**
	 * @param problemOrder
	 *            The problem order for the contest (list of problem IDs)
	 */
	public void setProblemOrder(List<String> problemOrder) {
		this.problemOrder = problemOrder;
	}

	/**
	 * @return Problem scores. The position of each score is the same of the
	 *         same as the position of the problem in the problem order
	 */
	@XmlElement(name = "problem_scores")
	public List<String> getProblemScores() {
		return problemScores;
	}

	/**
	 * @param problemScores
	 *            Problem scores
	 * @see Contest#getProblemScores()
	 */
	public void setProblemScores(List<String> problemScores) {
		this.problemScores = problemScores;
	}
}
