package fmi.uni.grading.shared.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a problem submission.
 * 
 * @author Martin Toshev
 */
@XmlRootElement(name = "submission")
@Document(collection = "submissions")
@TypeAlias("submission")
// stored in the '_class' attribute instead of the class FQDN \
public class Submission {

	@Id
	private String id;

	private GraderUser user;
	
	private String graderInstanceId;

	private String series;

	private String contest;

	private String problem;

	private String source;

	private String lang;

	private String status;

	private String log;

	@XmlElement(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * @return Grader user for the submission
	 */
	@XmlElement(name="user")
	public GraderUser getUser() {
		return user;
	}
	
	public void setUser(GraderUser user) {
		this.user = user;
	}
	
	/**
	 * @return Grader instance ID for which the submission applies
	 */
	@XmlElement(name = "grader_instance_id")
	public String getGraderInstanceId() {
		return graderInstanceId;
	}

	public void setGraderInstanceId(String graderInstanceId) {
		this.graderInstanceId = graderInstanceId;
	}
	
	/**
	 * @return Series for which the submission applies
	 */
	@XmlElement(name = "series")
	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	/**
	 * @return Contest ID for which the submission applies
	 */
	@XmlElement(name = "contest")
	public String getContest() {
		return contest;
	}

	public void setContest(String contest) {
		this.contest = contest;
	}

	/**
	 * @return Problem ID for which the submission applies
	 */
	@XmlElement(name = "problem")
	public String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}

	/**
	 * @return Source code of the submission
	 */
	@XmlElement(name = "source")
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return Programming language of source of the submission
	 */
	@XmlElement(name = "language")
	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 * @return Status of the submission (e.g. "ok" - OK, "wa" - wrong answer,
	 *         "ce" - compilation error, "pe" - presentation or "tl" - time
	 *         limit exceed in case of "acm" style problem)
	 */
	@XmlElement(name = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return Additional log entry associated with the submission
	 */
	@XmlElement(name = "log")
	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}
}
