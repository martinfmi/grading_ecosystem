package fmi.uni.grading.shared.client;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import fmi.uni.grading.shared.AbstractClient;
import fmi.uni.grading.shared.beans.Problem;
import fmi.uni.grading.shared.services.IProblemService;

/**
 * Client API for the {@link IProblemService}.
 * 
 * @author Martin Toshev
 */
public class ProblemClient extends AbstractClient<IProblemService> {

	public ProblemClient(String url, String user, String pass) {
		super(url, IProblemService.class, user, pass);
	}

	public Response addProblem(String type, String title, String description,
			long timeLimit, long memoryLimit, String origin,
			List<String> categories, List<String> authors)
			throws WebApplicationException {

		Problem problem = new Problem();

		problem.setType(type);
		problem.setTitle(title);
		problem.setDescription(description);
		problem.setTimeLimit(timeLimit);
		problem.setMemoryLimit(memoryLimit);
		problem.setOrigin(origin);
		problem.setCategories(categories);
		problem.setAuthors(authors);
		
		Response response = addProblem(problem);
		return response;
	}

	public Response addProblem(Problem bean) throws WebApplicationException {
		Response response = getService().addProblem(bean);
		return response;
	}

	public static void main(String[] args) {

		List<String> categories = new LinkedList<String>();
		categories.add("dynamic programming");
		List<String> authors = new LinkedList<String>();
		authors.add("Martin Toshev");
		ProblemClient client = new ProblemClient(
				"http://localhost:6607/services", "admin", "admin");
		Response response = client.addProblem("standard", "sample problem 234",
				"some sample problem", 3000l, 32, "FMI-test", categories, authors);
		
		System.err.println(response.getStatus());
	}
}
