package fmi.uni.grading.shared.beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a problem checker.
 * 
 * @author Martin Toshev
 */
@XmlRootElement(name = "checker")
@Document(collection = "checkers")
@TypeAlias("checker")
//stored in the '_class' attribute instead of the class FQDN \
public class Checker {

	@Id
	private String id;
	
	private String problemId;
	
	private String source;
	
	private String lang;
	
	private String binary;


	@XmlElement(name="id")
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@XmlElement(name="problem_id")
	public String getProblemId() {
		return problemId;
	}
	
	public void setProblemId(String problemId) {
		this.problemId = problemId;
	}

	/**
	 * @return Checker source code
	 */
	@XmlElement(name="source")
	public String getSource() {
		return source;
	}
	
	public void setSource(String source) {
		this.source = source;
	}
	
	/**
	 * @return Programming language of checker source code
	 */
	@XmlElement(name="lang")
	public String getLang() {
		return lang;
	}
	
	public void setLang(String lang) {
		this.lang = lang;
	}
	
	/**
	 * @return Checker binary
	 */
	@XmlElement(name="binary")
	public String getBinary() {
		return binary;
	}
	
	public void setBinary(String binary) {
		this.binary = binary;
	}
}
