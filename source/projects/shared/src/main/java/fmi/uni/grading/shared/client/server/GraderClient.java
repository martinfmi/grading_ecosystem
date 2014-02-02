package fmi.uni.grading.shared.client.server;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import fmi.uni.grading.shared.AbstractClient;
import fmi.uni.grading.shared.beans.Contest;
import fmi.uni.grading.shared.beans.GraderInstance;
import fmi.uni.grading.shared.beans.Problem;
import fmi.uni.grading.shared.beans.Series;
import fmi.uni.grading.shared.beans.Submission;
import fmi.uni.grading.shared.exceptions.AbstractServerException;
import fmi.uni.grading.shared.exceptions.client.ServerResponseException;
import fmi.uni.grading.shared.services.server.IGraderService;

public class GraderClient extends AbstractClient<IGraderService> {

	public GraderClient(String url, String user, String password) {
		super(url, IGraderService.class, user, password);
	}

	public Set<String> getGraderTypes() throws ServerResponseException {
		Set<String> graderTypes = null;
		try {
			graderTypes = getService().getGraderTypes();
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}

		return graderTypes;
	}

	public Collection<GraderInstance> getGraderInstances()
			throws ServerResponseException {
		Collection<GraderInstance> instances = null;
		try {
			instances = getService().getGraderInstances();
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}

		return instances;
	}

	public GraderInstance getGraderInstance(String graderId)
			throws ServerResponseException {
		GraderInstance instance = null;
		try {
			instance = getService().getGraderInstance(graderId);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}

		return instance;
	}

	public GraderInstance registerGraderInstance(String type, String name,
			String url, Boolean adminEnabled, Boolean requresPass)
			throws ServerResponseException {
		GraderInstance instance = buildGraderInstance(null, type, name, url,
				adminEnabled, requresPass);
		GraderInstance registeredInstance = registerGraderInstance(instance);
		return registeredInstance;
	}

	public GraderInstance registerGraderInstance(GraderInstance instance)
			throws ServerResponseException {
		GraderInstance registeredInstance = null;
		try {
			registeredInstance = getService().registerGraderInstance(instance);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}

		return registeredInstance;
	}

	public GraderInstance editGraderInstance(String instanceId, String type,
			String name, String url, Boolean adminEnabled, Boolean requresPass)
			throws ServerResponseException {
		GraderInstance instance = buildGraderInstance(instanceId, type, name,
				url, adminEnabled, requresPass);
		GraderInstance editedInstance = editGraderInstance(instance);
		return editedInstance;
	}
	
	public GraderInstance editGraderInstance(GraderInstance instance)
			throws ServerResponseException {
		GraderInstance editedInstance = null;
		try {
			editedInstance = getService().editGraderInstance(instance);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}

		return editedInstance;
	}

	public void deleteGraderInstance(String graderId)
			throws ServerResponseException {
		try {
			getService().deleteGraderInstance(graderId);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}
	}

	public List<Series> getRootSeries(String graderId)
			throws ServerResponseException {
		List<Series> rootSeries = null;
		try {
			rootSeries = getService().getRootSeries(graderId);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}

		return rootSeries;
	}

	public List<Series> getChildSeries(String graderId, String seriesId)
			throws ServerResponseException {
		List<Series> childSeries = null;
		try {
			childSeries = getService().getChildSeries(graderId, seriesId);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}

		return childSeries;
	}

	public Series getSeries(String graderId, String seriesId)
			throws ServerResponseException {
		Series series = null;
		try {
			series = getService().getSeries(graderId, seriesId);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}

		return series;
	}

	public Series createSeries(String graderId, String title, String about,
			List<String> notes, List<String> contestOrder,
			List<String> problemOrder, String parent)
			throws ServerResponseException {
		Series series = buildSeries(null, title, about, notes, contestOrder,
				problemOrder, parent);
		Series createdSeries = createSeries(graderId, series);
		return createdSeries;
	}

	public Series createSeries(String graderId, Series series)
			throws ServerResponseException {
		Series createSeries = null;
		try {
			createSeries = getService().createSeries(graderId, series);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}

		return createSeries;
	}

	public Series editSeries(String graderId, String seriesId, String title,
			String about, List<String> notes, List<String> contestOrder,
			List<String> problemOrder, String parent)
			throws ServerResponseException {
		Series series = buildSeries(seriesId, title, about, notes,
				contestOrder, problemOrder, parent);
		Series editSeries = editSeries(graderId, series);
		return editSeries;
	}

	public Series editSeries(String graderId, Series series)
			throws ServerResponseException {
		Series editedSeries = null;
		try {
			editedSeries = getService().editSeries(graderId, series);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}

		return editedSeries;
	}

	public void deleteSeries(String graderId, String seriesId)
			throws ServerResponseException {
		try {
			getService().deleteSeries(graderId, seriesId);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}
	}

	public List<Contest> getContests(String graderId, String seriesId)
			throws ServerResponseException {
		List<Contest> contests = null;
		try {
			contests = getService().getContests(graderId, seriesId);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}

		return contests;
	}

	public Contest getContest(String graderId, String seriesId, String contestId)
			throws ServerResponseException {
		Contest contest = null;
		try {
			contest = getService().getContest(graderId, seriesId, contestId);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}

		return contest;
	}

	public Contest createContest(String graderId, String seriesId, String type,
			String title, String startTime, Long duration, String about,
			String gradingStyle, List<String> problemOrder,
			List<String> problemScores) throws ServerResponseException {
		Contest contest = buildContest(null, type, title, startTime, duration,
				about, gradingStyle, problemOrder, problemScores);
		Contest createdContest = createContest(graderId, seriesId, contest);
		return createdContest;
	}

	public Contest createContest(String graderId, String seriesId,
			Contest contest) throws ServerResponseException {
		Contest createdContest = null;
		try {
			createdContest = getService().createContest(graderId, seriesId,
					contest);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}

		return createdContest;
	}

	public Contest editContest(String graderId, String seriesId,
			String contestId, String type, String title, String startTime,
			Long duration, String about, String gradingStyle,
			List<String> problemOrder, List<String> problemScores)
			throws ServerResponseException {

		Contest contest = buildContest(null, type, title, startTime, duration,
				about, gradingStyle, problemOrder, problemScores);
		Contest editedContest = editContest(graderId, seriesId, contest);
		return editedContest;
	}

	public Contest editContest(String graderId, String seriesId, Contest contest)
			throws ServerResponseException {
		Contest editedContest = null;
		try {
			editedContest = getService().editContest(graderId, seriesId,
					editedContest);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}

		return editedContest;
	}

	public void deleteContest(String graderId, String seriesId, String contestId)
			throws ServerResponseException {
		try {
			getService().deleteContest(graderId, seriesId, contestId);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}
	}

	public List<Problem> getProblems(String graderId, String seriesId)
			throws ServerResponseException {
		List<Problem> problems = null;
		try {
			problems = getService().getProblems(graderId, seriesId);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}

		return problems;
	}

	public List<Problem> getContestProblems(String graderId, String contestId)
			throws ServerResponseException {
		List<Problem> problems = null;
		try {
			problems = getService().getContestProblems(graderId, contestId);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}

		return problems;
	}

	public Problem getContestProblem(String graderId, String contestId,
			String problemId) throws ServerResponseException {
		Problem problem = null;
		try {
			problem = getService().getContestProblem(graderId, contestId,
					problemId);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}

		return problem;
	}

	public Problem createContestProblem(String graderId, String contestId,
			String type, String title, String description, Long timeLimit,
			Long memoryLimit, String origin, List<String> categories,
			List<String> authors) throws ServerResponseException {
		Problem problem = buildProblem(null, type, title, description,
				timeLimit, memoryLimit, origin, categories, authors);
		Problem createdProblem = createContestProblem(graderId, contestId,
				problem);
		return createdProblem;
	}

	public Problem createContestProblem(String graderId, String contestId,
			Problem problem) throws ServerResponseException {
		Problem createdProblem = null;
		try {
			createdProblem = getService().createContestProblem(graderId,
					contestId, problem);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}

		return createdProblem;
	}

	public Problem editContestProblem(String graderId, String contestId,
			String problemId, String type, String title, String description,
			Long timeLimit, Long memoryLimit, String origin,
			List<String> categories, List<String> authors)
			throws ServerResponseException {
		Problem problem = buildProblem(null, type, title, description,
				timeLimit, memoryLimit, origin, categories, authors);
		Problem editedProblem = editContestProblem(graderId, contestId, problem);
		return editedProblem;
	}

	public Problem editContestProblem(String graderId, String contestId,
			Problem problem) throws ServerResponseException {
		Problem editedProblem = null;
		try {
			editedProblem = getService().editContestProblem(graderId,
					contestId, problem);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}

		return editedProblem;
	}

	public void deleteContestProblem(String graderId, String contestId,
			String problemId) throws ServerResponseException {
		try {
			getService().deleteContestProblem(graderId, contestId, problemId);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}
	}

	public List<Submission> getSubmissions(String graderId, String problemId)
			throws ServerResponseException {
		List<Submission> submissions = null;
		try {
			submissions = getService().getSubmissions(graderId, problemId);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}

		return submissions;
	}

	public Submission getSubmission(String graderId, String problemId,
			String submissionId) throws ServerResponseException {
		Submission submission = null;
		try {
			submission = getService().getSubmission(graderId, problemId,
					submissionId);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}

		return submission;
	}

	public Submission sendSubmission(String graderId, String problemId,
			String graderInstanceId, String series, String contest,
			String problem, String source, String lang, String status,
			String log) throws ServerResponseException {
		Submission submission = buildSubmission(null, graderInstanceId, series,
				contest, problem, source, lang, status, log);
		Submission sentSubmission = sendSubmission(graderId, problemId,
				submission);
		return sentSubmission;
	}

	public Submission sendSubmission(String graderId, String problemId,
			Submission submission) throws ServerResponseException {
		Submission sentSubmission = null;
		try {
			sentSubmission = getService().sendSubmission(graderId, problemId,
					submission);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}

		return sentSubmission;
	}

	private GraderInstance buildGraderInstance(String graderId, String type,
			String name, String url, Boolean adminEnabled, Boolean requresPass) {

		GraderInstance instance = new GraderInstance();

		instance.setId(graderId);
		instance.setType(type);
		instance.setName(name);
		instance.setUrl(url);
		instance.setAdminEnabled(adminEnabled);
		instance.setRequresPass(requresPass);

		return instance;
	}

	private Series buildSeries(String graderId, String title, String about,
			List<String> notes, List<String> contestOrder,
			List<String> problemOrder, String parent) {
		Series series = new Series();

		series.setId(graderId);
		series.setTitle(title);
		series.setAbout(about);
		series.setNotes(notes);
		series.setContestOrder(contestOrder);
		series.setProblemOrder(problemOrder);
		series.setParent(parent);

		return series;
	}

	private Contest buildContest(String graderId, String type, String title,
			String startTime, Long duration, String about, String gradingStyle,
			List<String> problemOrder, List<String> problemScores) {
		Contest contest = new Contest();

		contest.setId(graderId);
		contest.setType(type);
		contest.setTitle(title);
		contest.setStartTime(startTime);
		contest.setDuration(duration);
		contest.setAbout(about);
		contest.setGradingStyle(gradingStyle);
		contest.setProblemOrder(problemOrder);
		contest.setProblemScores(problemScores);

		return contest;
	}

	private Problem buildProblem(String graderId, String type, String title,
			String description, Long timeLimit, Long memoryLimit,
			String origin, List<String> categories, List<String> authors) {

		Problem problem = new Problem();

		problem.setId(graderId);
		problem.setType(type);
		problem.setTitle(title);
		problem.setDescription(description);
		problem.setTimeLimit(timeLimit);
		problem.setMemoryLimit(memoryLimit);
		problem.setOrigin(origin);
		problem.setCategories(categories);
		problem.setAuthors(authors);

		return problem;
	}

	private Submission buildSubmission(String graderId,
			String graderInstanceId, String series, String contest,
			String problem, String source, String lang, String status,
			String log) {

		Submission submission = new Submission();

		submission.setId(graderId);
		submission.setGraderInstanceId(graderInstanceId);
		submission.setSeries(series);
		submission.setContest(contest);
		submission.setProblem(problem);
		submission.setSource(source);
		submission.setLang(lang);
		submission.setStatus(status);
		submission.setLog(log);

		return submission;
	}
}
