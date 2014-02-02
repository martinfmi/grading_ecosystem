package fmi.uni.grading.spoj0;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import fmi.uni.grading.server.grader.Grader;
import fmi.uni.grading.shared.beans.Contest;
import fmi.uni.grading.shared.beans.Problem;
import fmi.uni.grading.shared.beans.Submission;

public class Spoj0Grader extends Grader {

	public static final String TYPE_NAME = "spoj0";

	@Override
	public String getGraderType() {
		return TYPE_NAME;
	}

	@Override
	public List<Contest> getContests(String location, String seriesId) {
		return null;
	}
	
	@Override
	public Contest getContest(String location, String seriesId, String contestId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Problem> getProblems(String location, String seriesId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Problem> getContestProblems(String id, String contestId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Problem getContestProblem(String location, String contestId,
			String problemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Submission> getSubmissions(String location, String problemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Submission getSubmission(String id, String problemId,
			String submissionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Submission sendSubmission(String id, String problemId,
			Submission submission) {
		// TODO Auto-generated method stub
		return null;
	}
}
