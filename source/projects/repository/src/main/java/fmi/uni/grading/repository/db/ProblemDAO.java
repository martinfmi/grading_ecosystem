package fmi.uni.grading.repository.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import fmi.uni.grading.repository.RepositoryCache;
import fmi.uni.grading.shared.beans.AuthorSolution;
import fmi.uni.grading.shared.beans.Checker;
import fmi.uni.grading.shared.beans.Problem;
import fmi.uni.grading.shared.beans.Test;
import fmi.uni.grading.shared.exceptions.MissingResourceException;

public class ProblemDAO {

	@Autowired
	private CategoryDAO categoryDAO;

	public List<Problem> getProblems() {
		MongoTemplate mongoTemplate = RepositoryCache.getMongoTemplate();
		Query query = new Query();
		query.fields().include("id").include("title").include("categories");
		List<Problem> problems = mongoTemplate.find(query, Problem.class);
		return problems;
	}

	public Problem getProblem(String id) {
		MongoTemplate mongoTemplate = RepositoryCache.getMongoTemplate();
		Query query = new Query(Criteria.where("id").is(id));
		Problem problem = mongoTemplate.findOne(query, Problem.class);
		return problem;
	}

	public Problem addProblem(Problem problem) {
		checkForMissingCategory(problem);

		MongoTemplate mongoTemplate = RepositoryCache.getMongoTemplate();
		mongoTemplate.insert(problem);

		return problem;
	}

	public Problem editProblem(Problem problem) {
		checkForMissingCategory(problem);

		MongoTemplate mongoTemplate = RepositoryCache.getMongoTemplate();
		mongoTemplate.save(problem);

		return problem;
	}

	public void deleteProblem(String id) {
		MongoTemplate mongoTemplate = RepositoryCache.getMongoTemplate();

		deleteTests(id);
		deleteAuthorSolutions(id);
		deleteCheckers(id);

		Query query = new Query(Criteria.where("id").is(id));
		mongoTemplate.remove(query, Problem.class);
	}

	public List<Test> getTests(String problemId) {
		MongoTemplate mongoTemplate = RepositoryCache.getMongoTemplate();
		Query query = new Query(Criteria.where("problemId").is(problemId));
		List<Test> tests = mongoTemplate.find(query, Test.class);
		return tests;
	}

	public Test getTest(String testId) {
		MongoTemplate mongoTemplate = RepositoryCache.getMongoTemplate();
		Query query = new Query(Criteria.where("id").is(testId));
		Test test = mongoTemplate.findOne(query, Test.class);
		return test;
	}

	public Test addTest(Test test) {
		MongoTemplate mongoTemplate = RepositoryCache.getMongoTemplate();
		mongoTemplate.insert(test);
		return test;
	}

	public Test editTest(Test test) {
		MongoTemplate mongoTemplate = RepositoryCache.getMongoTemplate();
		mongoTemplate.save(test);
		return test;
	}

	public void deleteTest(String testId) {
		MongoTemplate mongoTemplate = RepositoryCache.getMongoTemplate();
		Query query = new Query(Criteria.where("id").is(testId));
		mongoTemplate.remove(query, Test.class);
	}

	public void deleteTests(String problemId) {
		MongoTemplate mongoTemplate = RepositoryCache.getMongoTemplate();
		Query query = new Query(Criteria.where("problemId").is(problemId));
		mongoTemplate.remove(query, Test.class);
	}

	public List<AuthorSolution> getAuthorSolutions(String problemId) {
		MongoTemplate mongoTemplate = RepositoryCache.getMongoTemplate();
		List<AuthorSolution> authorSolutions = mongoTemplate
				.findAll(AuthorSolution.class);
		return authorSolutions;
	}

	public AuthorSolution getAuthorSolution(String solutionId) {
		MongoTemplate mongoTemplate = RepositoryCache.getMongoTemplate();
		Query query = new Query(Criteria.where("id").is(solutionId));
		AuthorSolution authorSolution = mongoTemplate.findOne(query,
				AuthorSolution.class);
		return authorSolution;
	}

	public AuthorSolution addAuthorSolution(AuthorSolution authorSolution) {
		MongoTemplate mongoTemplate = RepositoryCache.getMongoTemplate();
		mongoTemplate.insert(authorSolution);

		return authorSolution;
	}

	public AuthorSolution editAuthorSolution(AuthorSolution authorSolution) {
		MongoTemplate mongoTemplate = RepositoryCache.getMongoTemplate();
		mongoTemplate.save(authorSolution);
		return authorSolution;
	}

	public void deleteAuthorSolution(String solutionId) {
		MongoTemplate mongoTemplate = RepositoryCache.getMongoTemplate();

		Query query = new Query(Criteria.where("id").is(solutionId));
		mongoTemplate.remove(query, AuthorSolution.class);
	}

	public void deleteAuthorSolutions(String problemId) {
		MongoTemplate mongoTemplate = RepositoryCache.getMongoTemplate();

		Query query = new Query(Criteria.where("problemId").is(problemId));
		mongoTemplate.remove(query, AuthorSolution.class);
	}

	public List<Checker> getCheckers(String problemId) {
		MongoTemplate mongoTemplate = RepositoryCache.getMongoTemplate();
		Query query = new Query(Criteria.where("problemId").is(problemId));
		List<Checker> checkers = mongoTemplate.find(query, Checker.class);
		return checkers;
	}

	public Checker getChecker(String checkerId) {
		MongoTemplate mongoTemplate = RepositoryCache.getMongoTemplate();
		Query query = new Query(Criteria.where("id").is(checkerId));
		Checker checker = mongoTemplate.findOne(query, Checker.class);
		return checker;
	}

	public Checker addChecker(Checker checker) {
		MongoTemplate mongoTemplate = RepositoryCache.getMongoTemplate();
		mongoTemplate.insert(checker);
		return checker;
	}

	public Checker editChecker(Checker checker) {
		MongoTemplate mongoTemplate = RepositoryCache.getMongoTemplate();
		mongoTemplate.save(checker);
		return checker;
	}

	public void deleteChecker(String checkerId) {
		MongoTemplate mongoTemplate = RepositoryCache.getMongoTemplate();

		Query query = new Query(Criteria.where("id").is(checkerId));
		mongoTemplate.remove(query, Checker.class);
	}

	public void deleteCheckers(String problemId) {
		MongoTemplate mongoTemplate = RepositoryCache.getMongoTemplate();
		Query query = new Query(Criteria.where("problemId").is(problemId));
		mongoTemplate.remove(query, Checker.class);
	}

	private void checkForMissingCategory(Problem problem) {
		if (problem.getCategories() != null) {
			for (String category : problem.getCategories()) {
				if (categoryDAO.getCategoryByName(category) == null) {
					throw new MissingResourceException(String.format(
							"No category with name '%s' found in repository.",
							category));
				}
			}
		}
	}
}
