package fmi.uni.grading.server.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;

import fmi.uni.grading.repository.db.ProblemDAO;
import fmi.uni.grading.shared.beans.Problem;
import fmi.uni.grading.shared.services.server.IProblemService;

public class ProblemService implements IProblemService {

	private static final Logger LOGGER = Logger.getLogger(ProblemService.class
			.getName());

	@Context
	private UriInfo uriInfo;

	public List<Problem> getProblems() {

		List<Problem> problems = ProblemDAO.getProblems();
		return problems;

		// List<ProblemDAO> problems = new LinkedList<ProblemDAO>();
		// problems.add(new ProblemDAO());
		// return problems;
	}

	public Problem getProblem(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Response addProblem(Problem problem) throws WebApplicationException {

		try {
			Response response;

			boolean exists = false;
			if (problem.getId() != null) {
				LOGGER.info("Problem ID for new problem already set: "
						+ problem.getId());
				Problem bean = ProblemDAO.getProblem(problem.getId());

				if (bean != null) {
					LOGGER.warn("Problem with ID: " + problem.getId()
							+ " already exists. Skipping addition.");
					exists = true;
				}
			}
			
			if (exists) {
				response = Response.ok().build();
			} else {
				ProblemDAO.addProblem(problem);
				URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/"
						+ problem.getId());
				response = Response.created(uri).build();
			}

			return response;
		} catch (URISyntaxException ex) {
			LOGGER.error("Failed to create uri for newly added problem.", ex);
			throw new WebApplicationException();
		}
	}

	public Problem editProblem(Problem bean) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteProblem(String id) {
		// TODO Auto-generated method stub
	}
}
