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
import fmi.uni.grading.shared.beans.Category;
import fmi.uni.grading.shared.beans.Problem;
import fmi.uni.grading.shared.exceptions.AbstractServerException;

/**
 * Provides CRUD operations for manipulation of problem/article categories in
 * the repository.
 * 
 * @author Martin Toshev
 */
@Path("categories")
public interface ICategoryService {

	/**
	 * Retrieves the top-level categories in the repository
	 * 
	 * @return A list of the available top-level categories
	 * @throws AbstractServerException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Category> getRootCategories() throws AbstractServerException;

	/**
	 * Retrieves a particular category.
	 * 
	 * @param id
	 *            The id of the category.
	 * @return {@link Category} instance.
	 * @throws AbstractServerException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Category getCategory(@PathParam("id") String id)
			throws AbstractServerException;

	/**
	 * Retrieves all child categories of a specified parent category.
	 * 
	 * @param parentId
	 *            The id of the parent category
	 * @return A list of {@link Category} instances.
	 * @throws AbstractServerException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}/categories")
	public List<Category> getChildCategories(@PathParam("id") String parentId)
			throws AbstractServerException;

	/**
	 * Creates a new category in the repository.
	 * 
	 * @param category
	 *            The category data
	 * @return The category data with additional information provided by the
	 *         repository upon creation.
	 * @throws AbstractServerException
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Category createCategory(Category category)
			throws AbstractServerException;

	/**
	 * Edits a category from the repository.
	 * 
	 * @param category
	 *            The changed category data. Category ID must be supplied.
	 * @return The changed category with additional data provided by the
	 *         repository upon update.
	 * @throws AbstractServerException
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Category editCategory(Category category)
			throws AbstractServerException;

	/**
	 * Deletes a category.
	 * 
	 * @param id
	 *            The category ID.
	 * @throws AbstractServerException
	 */
	@DELETE
	@Path("{id}")
	public void deleteCategory(@PathParam("id") String id)
			throws AbstractServerException;

	/**
	 * Retrieves all problems that apply for the category.
	 * 
	 * @param id
	 *            The category ID
	 * @return List of {@link Problem} instances
	 * @throws AbstractServerException
	 */
	@GET
	@Path("{id}/problems")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Problem> getCategoryProblems(@PathParam("id") String id)
			throws AbstractServerException;

	/**
	 * Retrieves all problems that apply for the category.
	 * 
	 * @param id
	 *            The category ID
	 * @return A list of {@link Article} instances
	 * @throws AbstractServerException
	 */
	@GET
	@Path("{id}/articles")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Article> getCategoryArticles(@PathParam("id") String id)
			throws AbstractServerException;

}
