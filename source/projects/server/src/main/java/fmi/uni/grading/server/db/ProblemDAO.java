package fmi.uni.grading.server.db;

import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import fmi.uni.grading.server.CacheUtil;
import fmi.uni.grading.shared.beans.Problem;

public class ProblemDAO {

	public static List<Problem> getProblems() {
		MongoOperations mongoOps = new MongoTemplate(CacheUtil.getReplica(),
				CacheUtil.getConfiguration().getDbName());
		return mongoOps.findAll(Problem.class);
	}

	public static Problem getProblem(String id) {
		MongoOperations mongoOps = new MongoTemplate(CacheUtil.getReplica(),
				CacheUtil.getConfiguration().getDbName());
		Query query = new Query(Criteria.where("id").is(id));
		
		return mongoOps.findOne(query, Problem.class);
	}

	public static void addProblem(Problem problem) {
		MongoOperations mongoOps = new MongoTemplate(CacheUtil.getReplica(),
				CacheUtil.getConfiguration().getDbName());
		mongoOps.insert(problem);
	}

	public static void editProblem(Problem problem) {
		MongoOperations mongoOps = new MongoTemplate(CacheUtil.getReplica(),
				CacheUtil.getConfiguration().getDbName());
		// Query searchUserQuery = new
		// Query(Criteria.where("username").is("mkyong"));
		// mongoOperation.updateFirst(searchUserQuery,
		// Update.update("password", "new password"),User.class);
		
//		mongoOps.updateFirst(arg0, arg1, ProblemBean.class);
	}
	
	public static void deleteProblem(String id) {
		MongoOperations mongoOps = new MongoTemplate(CacheUtil.getReplica(),
				CacheUtil.getConfiguration().getDbName());
		
		Query query = new Query(Criteria.where("id").is(id));
		mongoOps.remove(query, Problem.class);
	}
}
