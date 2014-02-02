package fmi.uni.grading.shared.services.server;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import fmi.uni.grading.shared.beans.Contest;
import fmi.uni.grading.shared.beans.GraderInstance;
import fmi.uni.grading.shared.beans.Problem;
import fmi.uni.grading.shared.beans.Series;
import fmi.uni.grading.shared.beans.Submission;
import fmi.uni.grading.shared.exceptions.AbstractServerException;

/**
 * Provides operations for interacting with the various grader instances
 * supported by the application server.
 * 
 * @author Martin Toshev
 */
@Path("graders")
public interface IGraderService {

	/**
	 * Retrieve all available grader types
	 * 
	 * @return A list of the grader types available in the application server.
	 * @throws AbstractServerException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("types")
	public Set<String> getGraderTypes() throws AbstractServerException;

	/**
	 * Retrieve all registered grader instances.
	 * 
	 * @return A list of {@link GraderInstance} instances.
	 * @throws AbstractServerException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<GraderInstance> getGraderInstances()
			throws AbstractServerException;

	/**
	 * Retrieves a particular grader instance
	 * 
	 * @param name
	 *            The unique grader instance name
	 * @return A {@link GraderInstance} instance
	 * @throws AbstractServerException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{name}")
	public GraderInstance getGraderInstance(@PathParam("name") String name)
			throws AbstractServerException;

	/**
	 * Registers a grader instance to the application server.
	 * 
	 * @param instance
	 *            The grader instance data with additional information provided
	 *            by the application server upon creation.
	 * @return The registered grader instance
	 * @throws AbstractServerException
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public GraderInstance registerGraderInstance(GraderInstance instance)
			throws AbstractServerException;

	/**
	 * Edits a grader instance.
	 * 
	 * @param instance
	 *            The grader instance data
	 * @return The grader instance data with additional information provided by
	 *         the application server upon creation.
	 * @throws AbstractServerException
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public GraderInstance editGraderInstance(GraderInstance instance)
			throws AbstractServerException;

	/**
	 * Deletes a grader instance.
	 * 
	 * @param name
	 *            The name of the grader instance
	 * @throws AbstractServerException
	 */
	@DELETE
	@Path("{name}")
	public void deleteGraderInstance(@PathParam("name") String name)
			throws AbstractServerException;
	
	/**
	 * Retrieves all top-level series in the specified grader instance.
	 * 
	 * @param series
	 *            The grader instance ID
	 * @return A list of {@link Series} instances
	 * @throws AbstractServerException
	 */
	@GET
	@Path("{id}/series")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Series> getRootSeries(@PathParam("id") String id)
			throws AbstractServerException;

	/**
	 * Retrieves all child series for a target series in a particular grader
	 * instance.
	 * 
	 * @param id
	 *            The grader instance ID
	 * @param seriesId
	 *            The series ID
	 * @return A list of {@link Series} instances
	 * @throws AbstractServerException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}/series/{seriesId}/series")
	public List<Series> getChildSeries(@PathParam("id") String id,
			@PathParam("seriesId") String seriesId)
			throws AbstractServerException;

	/**
	 * Retrieve a particular series in a grader instance.
	 * 
	 * @param id
	 *            The grader instance ID
	 * @param seriesId
	 *            The series ID
	 * @return A {@link Series} instance
	 * @throws AbstractServerException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}/series/{seriesId}")
	public Series getSeries(@PathParam("id") String id,
			@PathParam("seriesId") String seriesId)
			throws AbstractServerException;

	/**
	 * Creates a new series in the specified grader instance.
	 * 
	 * @param id
	 *            The grader instance ID
	 * @param series
	 *            The series data
	 * @return The series data with additional information provided by the
	 *         application server upon creation.
	 * @throws AbstractServerException
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}/series")
	public Series createSeries(@PathParam("id") String id, Series series)
			throws AbstractServerException;

	/**
	 * Edits a series in the specified grader instance.
	 * 
	 * @param id
	 *            The grader instance ID
	 * @param series
	 *            The series data
	 * @return The series data with additional information provided by the
	 *         application server upon update.
	 * @throws AbstractServerException
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}/series")
	public Series editSeries(@PathParam("id") String id, Series series)
			throws AbstractServerException;

	/**
	 * Deletes a series
	 * 
	 * @param id
	 *            The grader instance ID
	 * @param seriesId
	 *            The series ID
	 * @throws AbstractServerException
	 */
	@DELETE
	@Path("{id}/series/{seriesId}")
	public void deleteSeries(@PathParam("id") String id,
			@PathParam("seriesId") String seriesId)
			throws AbstractServerException;

	/**
	 * Retrieves all contests in a series in a grader instance.
	 * 
	 * @param id
	 *            Grader instance ID
	 * @param seriesId
	 *            Series ID
	 * @return A list of {@link Contest} instances
	 * @throws AbstractServerException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}/series/{seriesId}/contests")
	public List<Contest> getContests(@PathParam("id") String id,
			@PathParam("seriesId") String seriesId)
			throws AbstractServerException;

	/**
	 * Retrieves a contest in a series in a grader instance.
	 * 
	 * @param id
	 *            The grader instance ID
	 * @param seriesId
	 *            The series ID
	 * @param contestId
	 *            The contest ID
	 * @return A {@link Contest} instance
	 * @throws AbstractServerException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}/series/{seriesId}/contests/{contestId}")
	public Contest getContest(@PathParam("id") String id,
			@PathParam("seriesId") String seriesId,
			@PathParam("contestId") String contestId)
			throws AbstractServerException;

	/**
	 * Creates a contests in a series in a grader instance.
	 * 
	 * @param id
	 *            Grader instance ID
	 * @param seriesId
	 *            Series ID
	 * @return The contest data with additional information provided by the
	 *         application server upon creation.
	 * @throws AbstractServerException
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}/series/{seriesId}/contests")
	public Contest createContest(@PathParam("id") String id,
			@PathParam("seriesId") String seriesId, Contest contest)
			throws AbstractServerException;

	/**
	 * Edits a contests in a series in a grader instance.
	 * 
	 * @param id
	 *            Grader instance ID
	 * @param seriesId
	 *            Series ID
	 * @return The contest data with additional information provided by the
	 *         application server upon update.
	 * @throws AbstractServerException
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}/series/{seriesId}/contests")
	public Contest editContest(@PathParam("id") String id,
			@PathParam("seriesId") String seriesId, Contest contest)
			throws AbstractServerException;

	/**
	 * Deletes a contest from a series in a grader instance.
	 * 
	 * @param id
	 *            The grader instance ID
	 * @param seriesId
	 *            The series ID
	 * @param contestId
	 *            the contest ID
	 * @throws AbstractServerException
	 */
	@DELETE
	@Path("{id}/series/{seriesId}/contests/{contestId}")
	public void deleteContest(@PathParam("id") String id,
			@PathParam("seriesId") String seriesId,
			@PathParam("contestId") String contestId)
			throws AbstractServerException;

	/**
	 * Retrieves all problems in a series in a grader instance.
	 * 
	 * @param id
	 *            The grader instance ID
	 * @return A list of {@link Problem} instances
	 * @throws AbstractServerException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}/series/{seriesId}/problems")
	public List<Problem> getProblems(@PathParam("id") String id,
			@PathParam("seriesId") String seriesId)
			throws AbstractServerException;

	/**
	 * Retrieves all problems in a contest in a grader instance.
	 * 
	 * @param id
	 *            The contest instance ID
	 * @param contestId
	 *            The contest ID
	 * @return A list of {@link Problem} instances
	 * @throws AbstractServerException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}/contests/{constestId}/problems")
	public List<Problem> getContestProblems(@PathParam("id") String id,
			@PathParam("contestId") String contestId)
			throws AbstractServerException;

	/**
	 * Retrieves a problem in a contest in a grader instance.
	 * 
	 * @param id
	 *            The contest instance ID
	 * @param contestId
	 *            The contest ID
	 * @param problemId
	 *            The problem ID
	 * @return A list of {@link Problem} instances
	 * @throws AbstractServerException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}/contests/{constestId}/problems/{problemId}")
	public Problem getContestProblem(@PathParam("id") String id,
			@PathParam("contestId") String contestId,
			@PathParam("problemId") String problemId)
			throws AbstractServerException;
	
	/**
	 * Creates a problem in a contest in a grader instance.
	 * 
	 * @param id
	 *            The grader instance ID
	 * @param contestId
	 *            The contest ID
	 * @return A {@link Problem} instance
	 * @throws AbstractServerException
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}/contests/{constestId}/problems")
	public Problem createContestProblem(@PathParam("id") String id,
			@PathParam("contestId") String contestId, Problem problem)
			throws AbstractServerException;

	/**
	 * Edits a problem in a contest in a grader instance.
	 * 
	 * @param id
	 *            The grader instance ID
	 * @param contestId
	 *            The contest ID
	 * @return A {@link Problem} instance
	 * @throws AbstractServerException
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}/contests/{constestId}/problems")
	public Problem editContestProblem(@PathParam("id") String id,
			@PathParam("contestId") String contestId, Problem problem)
			throws AbstractServerException;

	/**
	 * Deletes a problem from a contest in a grader instance.
	 * 
	 * @param id
	 *            The grader instance ID
	 * @param contestId
	 *            The contest ID
	 * @param problemId
	 *            The problem ID
	 * @return A {@link Problem} instance
	 * @throws AbstractServerException
	 */
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}/contests/{constestId}/problems/{problemId}")
	public void deleteContestProblem(@PathParam("id") String id,
			@PathParam("contestId") String contestId,
			@PathParam("problemId") String problemId)
			throws AbstractServerException;

	/**
	 * Retrieves all submissions for a problem in a grader instance.
	 * 
	 * @param id
	 *            The contest ID
	 * @param problemId
	 *            The problem ID
	 * @param contestId
	 *            The contest ID
	 * @return A list of {@link Submission} instances
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}/problems/{problemId}/submissions")
	public List<Submission> getSubmissions(@PathParam("id") String id,
			@PathParam("problemId") String problemId)
			throws AbstractServerException;

	/**
	 * Retrieves a submission for a problem in a grader instance.
	 * 
	 * @param id
	 *            The contest ID
	 * @param problemId
	 *            The problem ID
	 * @return A list of {@link Submission} instances
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}/problems/{problemId}/submissions/{submissionId}")
	public Submission getSubmission(@PathParam("id") String id,
			@PathParam("problemId") String problemId,
			@PathParam("submissionId") String submissionId)
			throws AbstractServerException;

	/**
	 * Sends a submission for a problem in a grader instance.
	 * 
	 * @param id
	 *            The grader instance ID
	 * @param problemId
	 *            The problem ID
	 * @return A {@link Submission} instance that contains the result of
	 *         submission. Note that in case the submission is not judged prior
	 *         to submission a status of "waiting" is returned.
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}/problems/{problemId}/submissions")
	public Submission sendSubmission(@PathParam("id") String id,
			@PathParam("problemId") String problemId, Submission submission)
			throws AbstractServerException;
}
