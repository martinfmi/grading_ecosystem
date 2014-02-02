package fmi.uni.grading.server.services;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import fmi.uni.grading.server.ServerCache;
import fmi.uni.grading.server.ServerError;
import fmi.uni.grading.server.db.GraderDAO;
import fmi.uni.grading.server.grader.Grader;
import fmi.uni.grading.server.grader.NotSupported;
import fmi.uni.grading.shared.beans.Contest;
import fmi.uni.grading.shared.beans.GraderInstance;
import fmi.uni.grading.shared.beans.Problem;
import fmi.uni.grading.shared.beans.Series;
import fmi.uni.grading.shared.beans.Submission;
import fmi.uni.grading.shared.exceptions.AbstractServerException;
import fmi.uni.grading.shared.exceptions.BadRequestException;
import fmi.uni.grading.shared.exceptions.MissingResourceException;
import fmi.uni.grading.shared.exceptions.ServerException;
import fmi.uni.grading.shared.services.server.IGraderService;

public class GraderService implements IGraderService {

	@Context
	private UriInfo uriInfo;

	@Autowired
	private GraderDAO graderDAO;

	public Set<String> getGraderTypes() throws AbstractServerException {
		Set<String> types = ServerCache.getGraderTypes();
		return types;
	}

	public Collection<GraderInstance> getGraderInstances()
			throws AbstractServerException {
		Collection<GraderInstance> instances = ServerCache.getGraderInstances();
		return instances;
	}

	public GraderInstance getGraderInstance(String id)
			throws AbstractServerException {
		GraderInstance instance = ServerCache.getGraderInstance(id);
		if (instance == null) {
			throw new MissingResourceException("No grader instance with id: "
					+ id + " exists in system.");
		}
		return instance;
	}

	public GraderInstance registerGraderInstance(GraderInstance instance)
			throws AbstractServerException {

		if (instance.getId() != null) {
			throw new BadRequestException(
					"Grader instance id must not be provided upon creation.");
		}

		checkGraderInstance(instance);

		GraderInstance createdInstance = null;
		try {
			createdInstance = graderDAO.createGraderInstance(instance);
			ServerCache.addGraderInstance(instance);
		} catch (DataAccessException e) {
			throw new BadRequestException(e.getMessage());
		} catch (ServerError e) {
			throw new BadRequestException(e.getMessage());
		}

		return createdInstance;
	}

	public GraderInstance editGraderInstance(GraderInstance instance)
			throws AbstractServerException {
		if (instance.getId() == null) {
			throw new BadRequestException("No grader instance id provided.");
		}

		checkGraderInstance(instance);

		GraderInstance editedInstance = null;
		try {
			GraderInstance existingInstance = graderDAO
					.getGraderInstance(instance.getId());
			if (existingInstance == null) {
				throw new BadRequestException("Grader instance with id: "
						+ instance.getId()
						+ " is either not existing or is not editable.");
			}

			editedInstance = graderDAO.editGraderInstance(instance);
			ServerCache.updateGraderInstance(instance);
		} catch (DataAccessException e) {
			throw new BadRequestException(e.getMessage());
		} catch (ServerError e) {
			throw new BadRequestException(e.getMessage());
		}

		return editedInstance;
	}

	public void deleteGraderInstance(String id) throws AbstractServerException {
		try {
			graderDAO.deleteGraderInstance(id);
			ServerCache.removeGraderInstance(id);
		} catch (DataAccessException ex) {
			throw new BadRequestException(ex.getMessage());
		}
	}

	public List<Series> getRootSeries(String id) throws AbstractServerException {

		GraderInstance instance = getGraderInstance(id);
		Grader grader = ServerCache.getGrader(instance.getType());

		if (!supports(grader, "getRootSeries", String.class)) {
			throw new BadRequestException(
					"Retrieval of root series is not supported for grader of type: "
							+ instance.getType());
		}

		return grader.getRootSeries(instance.getUrl());
	}

	public List<Series> getChildSeries(String id, String seriesId)
			throws AbstractServerException {

		GraderInstance instance = getGraderInstance(id);
		Grader grader = ServerCache.getGrader(instance.getType());

		if (!supports(grader, "getChildSeries", String.class, String.class)) {
			throw new BadRequestException(
					"Retrieval of child series is not supported for grader of type: "
							+ instance.getType());
		}

		return grader.getChildSeries(instance.getUrl(), seriesId);
	}

	public Series getSeries(String id, String seriesId)
			throws AbstractServerException {
		GraderInstance instance = getGraderInstance(id);
		Grader grader = ServerCache.getGrader(instance.getType());

		if (!supports(grader, "getSeries", String.class, String.class)) {
			throw new BadRequestException(
					"Retrieval of series is not supported for grader of type: "
							+ instance.getType());
		}

		return grader.getSeries(instance.getUrl(), seriesId);
	}

	public Series createSeries(String id, Series series)
			throws AbstractServerException {
		GraderInstance instance = getGraderInstance(id);
		Grader grader = ServerCache.getGrader(instance.getType());

		if (!supports(grader, "createSeries", String.class, Series.class)) {
			throw new BadRequestException(
					"Creation of series is not supported for grader of type: "
							+ instance.getType());
		}

		return grader.createSeries(instance.getUrl(), series);
	}

	public Series editSeries(String id, Series series)
			throws AbstractServerException {
		GraderInstance instance = getGraderInstance(id);
		Grader grader = ServerCache.getGrader(instance.getType());

		if (!supports(grader, "editSeries", String.class, Series.class)) {
			throw new BadRequestException(
					"Update of series is not supported for grader of type: "
							+ instance.getType());
		}

		return grader.editSeries(instance.getUrl(), series);
	}

	public void deleteSeries(String id, String seriesId)
			throws AbstractServerException {
		GraderInstance instance = getGraderInstance(id);
		Grader grader = ServerCache.getGrader(instance.getType());

		if (!supports(grader, "deleteSeries", String.class, String.class)) {
			throw new BadRequestException(
					"Deletion of series is not supported for grader of type: "
							+ instance.getType());
		}

		grader.deleteSeries(instance.getUrl(), seriesId);
	}

	public List<Contest> getContests(String id, String seriesId)
			throws AbstractServerException {
		GraderInstance instance = getGraderInstance(id);
		Grader grader = ServerCache.getGrader(instance.getType());

		if (!supports(grader, "getContests", String.class, String.class)) {
			throw new BadRequestException(
					"Retrieval of contests is not supported for grader of type: "
							+ instance.getType());
		}

		return grader.getContests(instance.getUrl(), seriesId);
	}

	public Contest getContest(String id, String seriesId, String contestId)
			throws AbstractServerException {
		GraderInstance instance = getGraderInstance(id);
		Grader grader = ServerCache.getGrader(instance.getType());

		if (!supports(grader, "getContest", String.class, String.class,
				String.class)) {
			throw new BadRequestException(
					"Retrieval of contest from a series is not supported for grader of type: "
							+ instance.getType());
		}

		return grader.getContest(instance.getUrl(), seriesId, contestId);
	}

	public Contest createContest(String id, String seriesId, Contest contest)
			throws AbstractServerException {
		GraderInstance instance = getGraderInstance(id);
		Grader grader = ServerCache.getGrader(instance.getType());

		if (!supports(grader, "createContest", String.class, String.class,
				Contest.class)) {
			throw new BadRequestException(
					"Creation of contest in a series is not supported for grader of type: "
							+ instance.getType());
		}

		return grader.createContest(instance.getUrl(), seriesId, contest);
	}

	public Contest editContest(String id, String seriesId, Contest contest)
			throws AbstractServerException {
		GraderInstance instance = getGraderInstance(id);
		Grader grader = ServerCache.getGrader(instance.getType());

		if (!supports(grader, "editContest", String.class, String.class,
				Contest.class)) {
			throw new BadRequestException(
					"Update of contest in a series is not supported for grader of type: "
							+ instance.getType());
		}

		return grader.editContest(instance.getUrl(), seriesId, contest);
	}

	public void deleteContest(String id, String seriesId, String contestId)
			throws AbstractServerException {
		GraderInstance instance = getGraderInstance(id);
		Grader grader = ServerCache.getGrader(instance.getType());

		if (!supports(grader, "deleteContest", String.class, String.class,
				String.class)) {
			throw new BadRequestException(
					"Deletion of contest from a series is not supported for grader of type: "
							+ instance.getType());
		}

		grader.deleteContest(instance.getUrl(), seriesId, contestId);
	}

	public List<Problem> getProblems(String id, String seriesId)
			throws AbstractServerException {
		GraderInstance instance = getGraderInstance(id);
		Grader grader = ServerCache.getGrader(instance.getType());

		if (!supports(grader, "getProblems", String.class, String.class)) {
			throw new BadRequestException(
					"Retrieval of problems from a series is not supported for grader of type: "
							+ instance.getType());
		}

		return grader.getProblems(instance.getUrl(), seriesId);
	}

	public List<Problem> getContestProblems(String id, String contestId)
			throws AbstractServerException {
		GraderInstance instance = getGraderInstance(id);
		Grader grader = ServerCache.getGrader(instance.getType());

		if (!supports(grader, "getContestProblems", String.class, String.class)) {
			throw new BadRequestException(
					"Retrieval of problems from a contest is not supported for grader of type: "
							+ instance.getType());
		}

		return grader.getContestProblems(instance.getUrl(), contestId);
	}

	public Problem getContestProblem(String id, String contestId,
			String problemId) throws AbstractServerException {
		GraderInstance instance = getGraderInstance(id);
		Grader grader = ServerCache.getGrader(instance.getType());

		if (!supports(grader, "getContestProblem", String.class, String.class,
				String.class)) {
			throw new BadRequestException(
					"Retrieval of problem from a contest is not supported for grader of type: "
							+ instance.getType());
		}

		return grader
				.getContestProblem(instance.getUrl(), contestId, problemId);
	}

	public Problem createContestProblem(String id, String contestId,
			Problem problem) throws AbstractServerException {
		GraderInstance instance = getGraderInstance(id);
		Grader grader = ServerCache.getGrader(instance.getType());

		if (!supports(grader, "createContestProblem", String.class,
				String.class, Problem.class)) {
			throw new BadRequestException(
					"Creation of problem in a contest is not supported for grader of type: "
							+ instance.getType());
		}

		return grader.createContestProblem(instance.getUrl(), contestId,
				problem);
	}

	public Problem editContestProblem(String id, String contestId,
			Problem problem) throws AbstractServerException {
		GraderInstance instance = getGraderInstance(id);
		Grader grader = ServerCache.getGrader(instance.getType());

		if (!supports(grader, "editContestProblem", String.class, String.class,
				Problem.class)) {
			throw new BadRequestException(
					"Update of problem in a contest is not supported for grader of type: "
							+ instance.getType());
		}

		return grader.editContestProblem(instance.getUrl(), contestId, problem);
	}

	public void deleteContestProblem(String id, String contestId,
			String problemId) throws AbstractServerException {
		GraderInstance instance = getGraderInstance(id);
		Grader grader = ServerCache.getGrader(instance.getType());

		if (!supports(grader, "deleteContestProblem", String.class,
				String.class, String.class)) {
			throw new BadRequestException(
					"Deletion of problem in a contest is not supported for grader of type: "
							+ instance.getType());
		}

		grader.deleteContestProblem(instance.getUrl(), contestId, problemId);
	}

	public List<Submission> getSubmissions(String id, String problemId)
			throws AbstractServerException {
		GraderInstance instance = getGraderInstance(id);
		Grader grader = ServerCache.getGrader(instance.getType());

		if (!supports(grader, "getSubmissions", String.class, String.class)) {
			throw new BadRequestException(
					"Retrieval of problem submissions is not supported for grader of type: "
							+ instance.getType());
		}

		return grader.getSubmissions(instance.getUrl(), problemId);
	}

	public Submission getSubmission(String id, String problemId,
			String submissionId) throws AbstractServerException {
		GraderInstance instance = getGraderInstance(id);
		Grader grader = ServerCache.getGrader(instance.getType());

		if (!supports(grader, "getSubmission", String.class, String.class,
				String.class)) {
			throw new BadRequestException(
					"Retrieval of submission is not supported for grader of type: "
							+ instance.getType());
		}

		return grader.getSubmission(instance.getUrl(), problemId, submissionId);
	}

	public Submission sendSubmission(String id, String problemId,
			Submission submission) throws AbstractServerException {
		GraderInstance instance = getGraderInstance(id);
		Grader grader = ServerCache.getGrader(instance.getType());

		if (!supports(grader, "sendSubmission", String.class, String.class,
				Submission.class)) {
			throw new BadRequestException(
					"Sending of submission is not supported for grader of type: "
							+ instance.getType());
		}

		return grader.sendSubmission(instance.getUrl(), problemId, submission);
	}

	private void checkGraderInstance(GraderInstance instance) {
		if (instance.getName() == null) {
			throw new BadRequestException("No grader instance name provided.");
		}

		if (instance.getType() == null) {
			throw new BadRequestException(
					"No grader type provided for grader instance.");
		}

		if (ServerCache.getGrader(instance.getType()) == null) {
			throw new BadRequestException("No grader with type: "
					+ instance.getType() + " is present in system."
					+ "Please check the grader instances in the configuration.");
		}

		if (instance.getUrl() == null) {
			throw new BadRequestException("No grader instance URL provided.");
		}
	}

	private boolean supports(Grader grader, String methodName, Class<?>... args) {
		boolean result = true;
		Method method = null;
		try {
			method = grader.getClass().getMethod(methodName, args);
		} catch (NoSuchMethodException e) {
			throw new ServerException(e.getMessage());
		} catch (SecurityException e) {
			throw new ServerException(e.getMessage());
		}

		NotSupported notSupported = method.getAnnotation(NotSupported.class);
		if (notSupported != null) {
			throw new BadRequestException(
					String.format(
							"Method '%s' is not supported for grader instances of type '%s'.",
							methodName, grader.getGraderType()));
		}

		return result;
	}
}
