package fmi.uni.grading.server.grader;

import java.util.LinkedList;
import java.util.List;

import fmi.uni.grading.shared.beans.Contest;
import fmi.uni.grading.shared.beans.Problem;
import fmi.uni.grading.shared.beans.Series;
import fmi.uni.grading.shared.beans.Submission;

/**
 * Represent an external grader system in terms of a unified set of operations
 * that can be invoked on that system from the application server. Each concrete
 * application (bridge) should provide a concrete implementation of this class
 * that is used to provide the integration with the external grading system.
 * Most of the methods (operations) that are not abstract are by default
 * considered not supported (@see {@link NotSupported}) for the external grader
 * instance and must be overriden by the concrete implementation in order to
 * provide support for them.
 * 
 * @author Martin Toshev
 */
public abstract class Grader {
	
	/**
	 * Returns a global user-readable name for the grader. 
	 * Must be unique.
	 */
	public abstract String getGraderType();
	
	/**
	 * @param location
	 *            The root location (typically URL-formatted) of a grader
	 *            instance
	 * @return A list that contains the system default root series (see
	 *         {@link Series#ROOT})
	 */
	public List<Series> getRootSeries(String location) {
		List<Series> series = new LinkedList<Series>();
		series.add(Series.ROOT);
		return series;
	}

	@NotSupported
	public List<Series> getChildSeries(String location, String seriesId) {
		return null;
	}

	@NotSupported
	public Series getSeries(String location, String seriesId) {
		return null;
	}

	@NotSupported
	public Series createSeries(String location, Series series) {
		return null;
	}

	@NotSupported
	public Series editSeries(String location, Series series) {
		return null;
	}

	@NotSupported
	public void deleteSeries(String location, String seriesId) {
	}

	public abstract List<Contest> getContests(String location, String seriesId);

	public abstract Contest getContest(String location, String seriesId,
			String contestId);

	@NotSupported
	public Contest createContest(String id, String seriesId, Contest contest) {
		return null;
	}

	@NotSupported
	public Contest editContest(String id, String seriesId, Contest contest) {
		return null;
	}

	@NotSupported
	public void deleteContest(String id, String seriesId, String contestId) {
	}

	public abstract List<Problem> getProblems(String location, String seriesId);

	public abstract List<Problem> getContestProblems(String id, String contestId);

	public abstract Problem getContestProblem(String location,
			String contestId, String problemId);

	@NotSupported
	public Problem createContestProblem(String location, String contestId,
			Problem problem) {
		return null;
	}

	@NotSupported
	public Problem editContestProblem(String location, String contestId,
			Problem problem) {
		return null;
	}

	@NotSupported
	public void deleteContestProblem(String id, String contestId,
			String problemId) {
	}

	public abstract List<Submission> getSubmissions(String location,
			String problemId);

	public abstract Submission getSubmission(String id, String problemId,
			String submissionId);

	public abstract Submission sendSubmission(String id, String problemId,
			Submission submission);
}
