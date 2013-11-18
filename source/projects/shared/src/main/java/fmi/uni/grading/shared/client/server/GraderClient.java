//package fmi.uni.grading.shared.client.server;
//
//import java.util.List;
//
//import javax.ws.rs.Consumes;
//import javax.ws.rs.DELETE;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.PUT;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//
//import fmi.uni.grading.shared.AbstractClient;
//import fmi.uni.grading.shared.beans.Article;
//import fmi.uni.grading.shared.beans.Contest;
//import fmi.uni.grading.shared.beans.GraderInstance;
//import fmi.uni.grading.shared.beans.Problem;
//import fmi.uni.grading.shared.beans.Series;
//import fmi.uni.grading.shared.beans.Submission;
//import fmi.uni.grading.shared.beans.Tutorial;
//import fmi.uni.grading.shared.exceptions.AbstractServerException;
//import fmi.uni.grading.shared.services.server.IGraderService;
//
//public class GraderClient extends AbstractClient<IGraderService> {
//
//	public GraderClient(String url, String user, String password) {
//		super(url, IGraderService.class, user, password);
//	}
//	public List<String> getGraderTypes()
//			throws AbstractServerException;
//	
//	public List<GraderInstance> getGraderInstances()
//			throws AbstractServerException;
//
//	public List<GraderInstance> getGraderInstance(@PathParam("id") String id)
//			throws AbstractServerException;
//
//	public GraderInstance registerGraderInstance(GraderInstance instance)
//			throws AbstractServerException;
//
//	public GraderInstance editGraderInstance(GraderInstance instance)
//			throws AbstractServerException;
//
//	public void deleteGraderInstance(@PathParam("id") String id)
//			throws AbstractServerException;
//
//	public List<Series> getRootSeries(@PathParam("id") String id)
//			throws AbstractServerException;
//
//	public List<Series> getChildSeries(@PathParam("id") String id,
//			@PathParam("seriesId") String seriesId)
//			throws AbstractServerException;
//
//	public Series getSeries(@PathParam("id") String id,
//			@PathParam("seriesId") String seriesId)
//			throws AbstractServerException;
//
//	public Series createSeries(@PathParam("id") String id, Series series)
//			throws AbstractServerException;
//
//	public Series editSeries(@PathParam("id") String id, Series series)
//			throws AbstractServerException;
//
//	public void deleteSeries(@PathParam("id") String id,
//			@PathParam("seriesId") String seriesId)
//			throws AbstractServerException;
//
//	public List<Contest> getContests(@PathParam("id") String id,
//			@PathParam("seriesId") String seriesId)
//			throws AbstractServerException;
//
//	public Contest getContest(@PathParam("id") String id,
//			@PathParam("seriesId") String seriesId,
//			@PathParam("contestId") String contestId)
//			throws AbstractServerException;
//
//	public Contest createContest(@PathParam("id") String id,
//			@PathParam("seriesId") String seriesId, Contest contest)
//			throws AbstractServerException;
//
//	public Contest editContest(@PathParam("id") String id,
//			@PathParam("seriesId") String seriesId, Contest contest)
//			throws AbstractServerException;
//
//	public void deleteContest(@PathParam("id") String id,
//			@PathParam("seriesId") String seriesId,
//			@PathParam("contestId") String contestId)
//			throws AbstractServerException;
//
//	public List<Problem> getProblems(@PathParam("id") String id,
//			@PathParam("seriesId") String seriesId)
//			throws AbstractServerException;
//
//	public List<Problem> getContestProblems(@PathParam("id") String id,
//			@PathParam("contestId") String contestId)
//			throws AbstractServerException;
//
//	public List<Problem> getContestProblems(@PathParam("id") String id,
//			@PathParam("contestId") String contestId,
//			@PathParam("problemId") String problemId)
//			throws AbstractServerException;
//
//	public Problem createContestProblem(@PathParam("id") String id,
//			@PathParam("contestId") String contestId, Problem problem)
//			throws AbstractServerException;
//
//	public Problem editContestProblem(@PathParam("id") String id,
//			@PathParam("contestId") String contestId, Problem problem)
//			throws AbstractServerException;
//
//	public void deleteContestProblem(@PathParam("id") String id,
//			@PathParam("contestId") String contestId,
//			@PathParam("problemId") String problemId)
//			throws AbstractServerException;
//
//	public List<Submission> getSubmissions(@PathParam("id") String id,
//			@PathParam("problemId") String problemId)
//			throws AbstractServerException;
//
//	public Submission getSubmission(@PathParam("id") String id,
//			@PathParam("problemId") String problemId,
//			@PathParam("submissionId") String submissionId)
//			throws AbstractServerException;
//
//	public Submission sendSubmission(@PathParam("id") String id,
//			@PathParam("problemId") String problemId, Submission submission)
//			throws AbstractServerException;
//	
//}
