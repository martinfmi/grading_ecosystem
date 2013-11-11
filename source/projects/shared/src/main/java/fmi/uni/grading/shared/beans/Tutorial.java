package fmi.uni.grading.shared.beans;

import java.util.List;

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

	private List<TutorialEntry> content;

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
	public List<TutorialEntry> getContent() {
		return content;
	}

	public void setContent(List<TutorialEntry> content) {
		this.content = content;
	}
}
