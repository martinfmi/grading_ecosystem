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

import fmi.uni.grading.shared.beans.AuthorSolution;
import fmi.uni.grading.shared.beans.Checker;
import fmi.uni.grading.shared.beans.Problem;
import fmi.uni.grading.shared.beans.Test;
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

	/**
	 * Retrieves all tests for the specified problem.
	 */
	@GET
	@Path("{id}/tests")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Test> getTests(@PathParam("id") String id)
			throws AbstractServerException;

	/**
	 * Retrieves a particular test for the specified problem.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}/tests/{testId}")
	public Test getTest(@PathParam("id") String id,
			@PathParam("testId") String testId) throws AbstractServerException;

	/**
	 * Adds a test for the specified problem.
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}/tests")
	public Test addTest(@PathParam("id") String id, Test test)
			throws AbstractServerException;

	/**
	 * Edits a test for the problem.
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}/tests")
	public Test editTest(@PathParam("id") String id, Test test)
			throws AbstractServerException;

	/**
	 * Deletes a test for the problem.
	 */
	@DELETE
	@Path("{id}/tests/{testId}")
	public void deleteTest(@PathParam("id") String id,
			@PathParam("testId") String testId) throws AbstractServerException;

	/**
	 * Retrieves all author solutions for the specified problem.
	 */
	@GET
	@Path("{id}/author_solutions")
	@Produces(MediaType.APPLICATION_JSON)
	public List<AuthorSolution> getAuthorSolutions(@PathParam("id") String id)
			throws AbstractServerException;

	/**
	 * Retrieves an author solution for the specified problem.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}/author_solutions/{solutionId}")
	public AuthorSolution getAuthorSolution(@PathParam("id") String id,
			@PathParam("solutionId") String solutionId)
			throws AbstractServerException;

	/**
	 * Adds an author solution to the specified problem.
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}/author_solutions")
	public AuthorSolution addAuthorSolution(@PathParam("id") String id,
			AuthorSolution solution) throws AbstractServerException;

	/**
	 * Edits an author solution.
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}/author_solutions")
	public AuthorSolution editAuthorSolution(@PathParam("id") String id,
			AuthorSolution solution) throws AbstractServerException;

	/**
	 * Deletes an author solution.
	 */
	@DELETE
	@Path("{id}/author_solutions/{solutionId}")
	public void deleteAuthorSolution(@PathParam("id") String id,
			@PathParam("solutionId") String solutionId)
			throws AbstractServerException;

	/**
	 * Retrieves all checkers for the specified problem.
	 */
	@GET
	@Path("{id}/checkers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Checker> getCheckers(@PathParam("id") String id)
			throws AbstractServerException;

	/**
	 * Retrieves a particular checker for the specified problem.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}/checkers/{checkerid}")
	public Checker getChecker(@PathParam("id") String id,
			@PathParam("checkerId") String checkerId)
			throws AbstractServerException;

	/**
	 * Adds a checker for the specified problem.
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}/checkers")
	public Checker addChecker(@PathParam("id") String id, Checker checker)
			throws AbstractServerException;

	/**
	 * Edits a checker.
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}/checkers")
	public Checker editChecker(@PathParam("id") String id, Checker checker)
			throws AbstractServerException;

	/**
	 * Deletes a checker.
	 */
	@DELETE
	@Path("{id}/checkers/{checkerId}")
	public void deleteChecker(@PathParam("id") String id,
			@PathParam("checkerId") String checkerId)
			throws AbstractServerException;

}
