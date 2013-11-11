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

import fmi.uni.grading.shared.beans.Problem;
import fmi.uni.grading.shared.exceptions.AbstractServerException;

/**
 * Provides CRUD operations for manipulation of problems and problem data in the
 * repository.
 * 
 * @author Martin Toshev
 */
@Path("/problems")
public interface IProblemService {
	
	/**
	 * <pre>
	 * Retrieves a list of all problems. 
	 * Additional query parameters: <br />
	 * 
	 * format – problem type used to filters the retrieved problems 
	 * categories – comma-separated list of categories used to filter the problems
	 * authors – comma-separated list of authors used to filter the problems
	 * 
	 * <b>format</b> – problem type used to filters the retrieved problems <br />
	 * <b>categories</b> – comma-separated list of categories used to filter the
	 * problems <br />
	 * <b>authors</b> – comma-separated list of authors used to filter the
	 * problems <br />
	 * </pre>
	 * 
	 * @return A list of {@link Problem} instances.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Problem> getProblems() throws AbstractServerException;

	/**
	 * Retrieves a problem.
	 * 
	 * @param id
	 *            The problem ID.
	 * @return An {@link Problem} instance.
	 * @throws AbstractServerException
	 */
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Problem getProblem(@PathParam("id") String id)
			throws AbstractServerException;

	/**
	 * Creates a problem.
	 * 
	 * @param problem
	 *            The problem data
	 * @return An {@link Problem} instance.
	 * @throws AbstractServerException
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Problem createProblem(Problem problem)
			throws AbstractServerException;

	/**
	 * Edits a problem.
	 * 
	 * @param problem
	 *            The problem data
	 * @return An {@link Problem} instance
	 * @throws AbstractServerException
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Problem editProblem(Problem problem) throws AbstractServerException;

	/**
	 * Deletes a problem.
	 * 
	 * @param id
	 *            The problem ID
	 * @throws AbstractServerException
	 */
	@DELETE
	@Path("{id}")
	public void deleteProblem(@PathParam("id") String id)
			throws AbstractServerException;
}
