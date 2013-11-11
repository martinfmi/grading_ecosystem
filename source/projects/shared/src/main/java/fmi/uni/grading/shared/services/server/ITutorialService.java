package fmi.uni.grading.shared.services.server;

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
import fmi.uni.grading.shared.beans.Problem;
import fmi.uni.grading.shared.beans.Tutorial;
import fmi.uni.grading.shared.exceptions.AbstractServerException;

/**
 * Provides operations managing the set of tutorials in the current application
 * server instance.
 * 
 * @author Martin Toshev
 */
@Path("tutorials")
public interface ITutorialService {

	/**
	 * Retrieves a list of all tutorials in the application server.
	 * 
	 * @return A list of {@link Tutorial} instances.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Tutorial> getTutorials() throws AbstractServerException;

	/**
	 * Retrieves a tutorial.
	 * 
	 * @param id
	 *            The tutorial ID.
	 * @return An {@link Tutorial} instance.
	 * @throws AbstractServerException
	 */
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Tutorial getTutorial(@PathParam("id") String id)
			throws AbstractServerException;

	/**
	 * Creates a tutorial.
	 * 
	 * @param tutorial
	 *            The tutorial data
	 * @return A {@link Tutorial} instance.
	 * @throws AbstractServerException
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Tutorial createTutorial(Tutorial tutorial)
			throws AbstractServerException;

	/**
	 * Edits a tutorial.
	 * 
	 * @param tutorial
	 *            The tutorial data
	 * @return An {@link Tutorial} instance
	 * @throws AbstractServerException
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Tutorial editArticle(Tutorial tutorial)
			throws AbstractServerException;

	/**
	 * Deletes a tutorial.
	 * 
	 * @param id
	 *            The tutorial ID
	 * @throws AbstractServerException
	 */
	@DELETE
	@Path("{id}")
	public void deleteTutorial(@PathParam("id") String id)
			throws AbstractServerException;

	/**
	 * Retrieves a tutorial article.
	 * 
	 * @param id
	 *            The tutorial ID.
	 * @param articleId
	 *            The article ID.
	 * @return An {@link Article} instance.
	 * @throws AbstractServerException
	 */
	@GET
	@Path("{id}/articles/{articleId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Article getTutorialArticle(@PathParam("id") String id,
			@PathParam("articleId") String articleId)
			throws AbstractServerException;

	/**
	 * Creates a tutorial article.
	 * 
	 * @param id
	 *            The tutorial ID
	 * @param article
	 *            The article data
	 * @return A {@link Article} instance.
	 * @throws AbstractServerException
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}/articles")
	public Article createTutorialArticle(@PathParam("id") String id,
			Article article) throws AbstractServerException;

	/**
	 * Edits a tutorial article.
	 * 
	 * @param id
	 *            The tutorial ID
	 * @param article
	 *            The article data
	 * @return A {@link Article} instance.
	 * @throws AbstractServerException
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}/articles")
	public Article editTutorialArticle(@PathParam("id") String id,
			Article article) throws AbstractServerException;

	/**
	 * Deletes a tutorial article.
	 * 
	 * @param id
	 *            The tutorial ID
	 * @param articleId
	 *            The article ID
	 * 
	 * @throws AbstractServerException
	 */
	@DELETE
	@Path("{id}/articles/{articleId}")
	public void deleteTutorialArticle(@PathParam("id") String id,
			@PathParam("articleId") String articleId)
			throws AbstractServerException;

	/**
	 * Retrieves a tutorial problem.
	 * 
	 * @param id
	 *            The tutorial ID
	 * @param problemId
	 *            The problem ID
	 * @return A {@link Problem} instance
	 * @throws AbstractServerException
	 */
	@GET
	@Path("{id}/problems/{problemId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Problem getTutorialProblem(@PathParam("id") String id,
			@PathParam("problemId") String problemId)
			throws AbstractServerException;

	/**
	 * Creates a tutorial problem.
	 * 
	 * @param id
	 *            The tutorial ID
	 * @param problem
	 *            The problem data
	 * @return A {@link Problem} instance.
	 * @throws AbstractServerException
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}/problems")
	public Problem createTutorialProblem(@PathParam("id") String id,
			Problem problem) throws AbstractServerException;

	/**
	 * Edits a tutorial problem.
	 * 
	 * @param id
	 *            The tutorial ID
	 * @param article
	 *            The problem data
	 * @return A {@link Problem} instance.
	 * @throws AbstractServerException
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}/problems")
	public Problem editTutorialProblem(@PathParam("id") String id,
			Problem problem) throws AbstractServerException;

	/**
	 * Deletes a tutorial problem.
	 * 
	 * @param id
	 *            The tutorial ID
	 * @param problemId
	 *            The problem ID
	 * 
	 * @throws AbstractServerException
	 */
	@DELETE
	@Path("{id}/problems/{problemId}")
	public void deleteTutorialProblem(@PathParam("id") String id,
			@PathParam("problemId") String problemId)
			throws AbstractServerException;
}
