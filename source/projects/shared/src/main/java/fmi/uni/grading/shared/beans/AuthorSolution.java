package fmi.uni.grading.shared.beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents an author solution for a problem.
 * 
 * @author Martin Toshev
 */
@XmlRootElement(name = "author_solution")
@Document(collection = "author_solutions")
@TypeAlias("author_solution")
// stored in the '_class' attribute instead of the class FQDN \
public class AuthorSolution {

	@Id
	private String id;

	private String problemId;

	private String source;

	private String lang;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement(name = "problemId")
	public String getProblemId() {
		return problemId;
	}

	public void setProblemId(String problemId) {
		this.problemId = problemId;
	}

	/**
	 * @return The source code of the author's solution.
	 */
	@XmlElement(name = "source")
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return The programming language of the author's solution.
	 */
	public String getLang() {
		return lang;
	}

	@XmlElement(name = "lang")
	public void setLang(String lang) {
		this.lang = lang;
	}
}
