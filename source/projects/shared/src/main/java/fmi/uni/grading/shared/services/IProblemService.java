package fmi.uni.grading.shared.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fmi.uni.grading.shared.beans.Problem;

@Path("/problems")
public interface IProblemService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Problem> getProblems();
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Problem getProblem(@PathParam("id") String id);
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addProblem(Problem bean) throws WebApplicationException;
	
	@POST
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Problem editProblem(Problem bean);
	
	@DELETE
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteProblem(@PathParam("id") String id);
}
