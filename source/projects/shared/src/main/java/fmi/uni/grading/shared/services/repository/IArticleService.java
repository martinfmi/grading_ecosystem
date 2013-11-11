package fmi.uni.grading.shared.services.repository;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import fmi.uni.grading.shared.beans.Article;
import fmi.uni.grading.shared.exceptions.AbstractServerException;

/**
 * Provides CRUD operations for manipulation of articles in the repository.
 * 
 * @author Martin Toshev
 */
@Path("articles")
public interface IArticleService {

	/**
	 * <pre>
	 * Retrieves a list of all articles. 
	 * Additional query parameters: <br />
	 * <b>format</b> – format of the articles <br />
	 * <b>categories</b> – comma-separated list of categories used to filter the
	 * articles <br />
	 * <b>authors</b> – comma-separated list of authors used to filter the
	 * articles <br />
	 * </pre>
	 * 
	 * @return A list of {@link Article} instances.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Article> getArticles() throws AbstractServerException;

	/**
	 * Retrieves a particular article.
	 * 
	 * @param id
	 *            The article ID.
	 * @return An {@link Article} instance.
	 * @throws AbstractServerException
	 */
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Article getArticle(@PathParam("id") String id)
			throws AbstractServerException;

	/**
	 * Creates a particular article.
	 * 
	 * @param article
	 *            The article data
	 * @return An {@link Article} instance.
	 * @throws AbstractServerException
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Article createArticle(Article article)
			throws AbstractServerException;

	/**
	 * Edits a particular article.
	 * 
	 * @param article
	 *            The article data
	 * @return An {@link Article} instance
	 * @throws AbstractServerException
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Article editArticle(Article article) throws AbstractServerException;

	/**
	 * Deletes a particular article.
	 * 
	 * @param id
	 *            The article ID
	 * @throws AbstractServerException
	 */
	@DELETE
	@Path("{id}")
	public void deleteArticle(@PathParam("id") String id)
			throws AbstractServerException;
}
