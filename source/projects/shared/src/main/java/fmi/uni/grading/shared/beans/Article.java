package fmi.uni.grading.shared.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents an article.
 * 
 * @author Martin Toshev
 */
@XmlRootElement(name = "article")
@Document(collection = "articles")
@TypeAlias("article")
// stored in the '_class' attribute instead of the class FQDN \
public class Article {

	@Id
	private String id;

	private String title;

	private String format;

	private String content;

	private List<String> authors;

	private List<String> categories;

	private boolean isVisible = true;

	private String ref;

	@XmlElement(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return The article title.
	 */
	@XmlElement(name = "title")
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            The title of the article
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return The format of the article.
	 */
	@XmlElement(name = "format")
	public String getFormat() {
		return format;
	}

	/**
	 * @param format
	 *            The format of the article (e.g. pdf, txt)
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * @return The article content (e.g. base64 encoded in case of binary data
	 *         or pure text)
	 */
	@XmlElement(name = "content")
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            The article content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return The article's authors.
	 */
	@XmlElement(name = "authors")
	public List<String> getAuthors() {
		return authors;
	}

	/**
	 * @param authors
	 *            The article's authors.
	 */
	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}

	/**
	 * @return The article's categories
	 */
	@XmlElement(name = "categories")
	public List<String> getCategories() {
		return categories;
	}

	/**
	 * @param categories
	 *            The article's categories
	 */
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	/**
	 * Returns the visibility status on article In case the article is not
	 * visible it is not retrieved from any of the article-related services.
	 * 
	 * @return <b>true</b> if the article is visible and <b>false</b> otherwise.
	 *         Defaults to <b>true</b> if not explicitly set.
	 */
	@XmlElement(name = "visible")
	public boolean isVisible() {
		return isVisible;
	}

	/**
	 * @param isVisible
	 *            The visibility status of the article
	 */
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	/**
	 * @return A reference to an external article (e.g. URL) that corresponds to
	 *         this article entry.
	 */
	@XmlElement(name = "ref")
	public String getRef() {
		return ref;
	}

	/**
	 * @param ref
	 *            Sets a reference to an external article (if any).
	 */
	public void setRef(String ref) {
		this.ref = ref;
	}
}
