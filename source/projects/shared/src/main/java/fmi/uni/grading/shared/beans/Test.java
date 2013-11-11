package fmi.uni.grading.shared.beans;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@XmlRootElement(name = "test")
@Document(collection = "tests")
@TypeAlias("test")
// stored in the '_class' attribute instead of the class FQDN \
public class Test {

	@Id
	private String id;

	private String problemId;

	private String weight;

	private String content;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return The problem ID for which the test applies
	 */
	public String getProblemId() {
		return problemId;
	}

	public void setProblemId(String problemId) {
		this.problemId = problemId;
	}

	/**
	 * @return The test weight
	 */
	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	/**
	 * @return The test data content
	 */
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
