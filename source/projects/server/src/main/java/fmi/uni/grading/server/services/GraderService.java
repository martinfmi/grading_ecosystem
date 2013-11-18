package fmi.uni.grading.server.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;

import fmi.uni.grading.server.ServerCache;
import fmi.uni.grading.server.ServerError;
import fmi.uni.grading.server.db.GraderDAO;
import fmi.uni.grading.shared.beans.Contest;
import fmi.uni.grading.shared.beans.GraderInstance;
import fmi.uni.grading.shared.beans.Problem;
import fmi.uni.grading.shared.beans.Series;
import fmi.uni.grading.shared.beans.Submission;
import fmi.uni.grading.shared.exceptions.AbstractServerException;
import fmi.uni.grading.shared.exceptions.BadRequestException;
import fmi.uni.grading.shared.services.server.IGraderService;

public class GraderService implements IGraderService {
	
	@Context
	private UriInfo uriInfo;
	
	@Autowired
	private GraderDAO graderDAO;
	
	public List<String> getGraderTypes() throws AbstractServerException {
		
		return null;
	}
	
	public List<GraderInstance> getGraderInstances()
			throws AbstractServerException {
		// TODO Auto-generated method stub
		return null;
	}

	public GraderInstance getGraderInstance(String id)
			throws AbstractServerException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public GraderInstance registerGraderInstance(GraderInstance instance)
			throws AbstractServerException {
		try {
			ServerCache.addGraderInstance(instance);
		} catch(ServerError e) {
			throw new BadRequestException(e.getMessage());
		}
		
		return null;
	}

	public GraderInstance editGraderInstance(GraderInstance instance)
			throws AbstractServerException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void deleteGraderInstance(@PathParam("id") String id)
			throws AbstractServerException {
		// TODO Auto-generated method stub
		
	}

	@GET
	@Path("{id}/series")
	@Produces("application/json")
	public List<Series> getRootSeries(@PathParam("id") String id)
			throws AbstractServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@GET
	@Produces("application/json")
	@Path("{id}/series/{seriesId}/series")
	public List<Series> getChildSeries(@PathParam("id") String id,
			@PathParam("seriesId") String seriesId)
			throws AbstractServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@GET
	@Produces("application/json")
	@Path("{id}/series/{seriesId}")
	public Series getSeries(@PathParam("id") String id,
			@PathParam("seriesId") String seriesId)
			throws AbstractServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@PUT
	@Produces("application/json")
	@Consumes("application/json")
	@Path("{id}/series")
	public Series createSeries(@PathParam("id") String id, Series series)
			throws AbstractServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("{id}/series")
	public Series editSeries(@PathParam("id") String id, Series series)
			throws AbstractServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@DELETE
	@Path("{id}/series/{seriesId}")
	public void deleteSeries(@PathParam("id") String id,
			@PathParam("seriesId") String seriesId)
			throws AbstractServerException {
		// TODO Auto-generated method stub
		
	}

	@GET
	@Produces("application/json")
	@Path("{id}/series/{seriesId}/contests")
	public List<Contest> getContests(@PathParam("id") String id,
			@PathParam("seriesId") String seriesId)
			throws AbstractServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@GET
	@Produces("application/json")
	@Path("{id}/series/{seriesId}/contests/{contestId}")
	public Contest getContest(@PathParam("id") String id,
			@PathParam("seriesId") String seriesId,
			@PathParam("contestId") String contestId)
			throws AbstractServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@PUT
	@Produces("application/json")
	@Consumes("application/json")
	@Path("{id}/series/{seriesId}/contests")
	public Contest createContest(@PathParam("id") String id,
			@PathParam("seriesId") String seriesId, Contest contest)
			throws AbstractServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("{id}/series/{seriesId}/contests")
	public Contest editContest(@PathParam("id") String id,
			@PathParam("seriesId") String seriesId, Contest contest)
			throws AbstractServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@DELETE
	@Path("{id}/series/{seriesId}/contests/{contestId}")
	public void deleteContest(@PathParam("id") String id,
			@PathParam("seriesId") String seriesId,
			@PathParam("contestId") String contestId)
			throws AbstractServerException {
		// TODO Auto-generated method stub
		
	}

	@GET
	@Produces("application/json")
	@Path("{id}/series/{seriesId}/problems")
	public List<Problem> getProblems(@PathParam("id") String id,
			@PathParam("seriesId") String seriesId)
			throws AbstractServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@GET
	@Produces("application/json")
	@Path("{id}/contests/{constestId}/problems")
	public List<Problem> getContestProblems(@PathParam("id") String id,
			@PathParam("contestId") String contestId)
			throws AbstractServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@GET
	@Produces("application/json")
	@Path("{id}/contests/{constestId}/problems/{problemId}")
	public List<Problem> getContestProblem(@PathParam("id") String id,
			@PathParam("contestId") String contestId,
			@PathParam("problemId") String problemId)
			throws AbstractServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@PUT
	@Produces("application/json")
	@Consumes("application/json")
	@Path("{id}/contests/{constestId}/problems")
	public Problem createContestProblem(@PathParam("id") String id,
			@PathParam("contestId") String contestId, Problem problem)
			throws AbstractServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("{id}/contests/{constestId}/problems")
	public Problem editContestProblem(@PathParam("id") String id,
			@PathParam("contestId") String contestId, Problem problem)
			throws AbstractServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@DELETE
	@Produces("application/json")
	@Consumes("application/json")
	@Path("{id}/contests/{constestId}/problems/{problemId}")
	public void deleteContestProblem(@PathParam("id") String id,
			@PathParam("contestId") String contestId,
			@PathParam("problemId") String problemId)
			throws AbstractServerException {
		// TODO Auto-generated method stub
		
	}

	@GET
	@Produces("application/json")
	@Path("{id}/problems/{problemId}/submissions")
	public List<Submission> getSubmissions(@PathParam("id") String id,
			@PathParam("problemId") String problemId)
			throws AbstractServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@GET
	@Produces("application/json")
	@Path("{id}/problems/{problemId}/submissions/{submissionId}")
	public Submission getSubmission(@PathParam("id") String id,
			@PathParam("problemId") String problemId,
			@PathParam("submissionId") String submissionId)
			throws AbstractServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@PUT
	@Produces("application/json")
	@Consumes("application/json")
	@Path("{id}/problems/{problemId}/submissions")
	public Submission sendSubmission(@PathParam("id") String id,
			@PathParam("problemId") String problemId, Submission submission)
			throws AbstractServerException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
