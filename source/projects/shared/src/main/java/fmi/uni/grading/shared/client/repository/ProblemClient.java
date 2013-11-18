package fmi.uni.grading.shared.client.repository;

import java.util.List;

import fmi.uni.grading.shared.AbstractClient;
import fmi.uni.grading.shared.beans.AuthorSolution;
import fmi.uni.grading.shared.beans.Checker;
import fmi.uni.grading.shared.beans.Problem;
import fmi.uni.grading.shared.beans.Test;
import fmi.uni.grading.shared.exceptions.AbstractServerException;
import fmi.uni.grading.shared.exceptions.client.ServerResponseException;
import fmi.uni.grading.shared.services.repository.IProblemService;

/**
 * Client API for the {@link IProblemService}.
 * 
 * @author Martin Toshev
 */
public class ProblemClient extends AbstractClient<IProblemService> {

	public ProblemClient(String url, String user, String pass) {
		super(url, IProblemService.class, user, pass);
	}

	public List<Problem> getProblems() throws ServerResponseException {
		List<Problem> problems = null;
		try {
			problems = getService().getProblems();
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}

		return problems;
	}

	public Problem getProblem(String id) throws ServerResponseException {
		Problem problem = null;
		try {
			problem = getService().getProblem(id);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}

		return problem;
	}

	public Problem createProblem(String type, String title, String description,
			Long timeLimit, Long memoryLimit, String origin,
			List<String> categories, List<String> authors)
			throws ServerResponseException {

		Problem problem = buildProblem(null, type, title, description,
				timeLimit, memoryLimit, origin, categories, authors);
		Problem createdProblem = createProblem(problem);
		return createdProblem;
	}

	public Problem createProblem(Problem bean) throws ServerResponseException {
		Problem createdProblem = null;
		try {
			createdProblem = getService().createProblem(bean);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}

		return createdProblem;
	}

	public Problem editProblem(String id, String type, String title,
			String description, Long timeLimit, Long memoryLimit,
			String origin, List<String> categories, List<String> authors)
			throws ServerResponseException {

		Problem problem = buildProblem(id, type, title, description, timeLimit,
				memoryLimit, origin, categories, authors);
		Problem createdProblem = editProblem(problem);
		return createdProblem;
	}

	public Problem editProblem(Problem problem) throws ServerResponseException {
		Problem editedProblem = null;
		try {
			editedProblem = getService().editProblem(problem);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}

		return editedProblem;
	}

	public void deleteProblem(String id) throws ServerResponseException {
		try {
			getService().deleteProblem(id);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}
	}

	public List<Test> getTests(String problemId) throws ServerResponseException {
		List<Test> tests = null;
		try {
			tests = getService().getTests(problemId);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}

		return tests;
	}

	public Test getTest(String problemId, String testId)
			throws ServerResponseException {
		Test test = null;
		try {
			test = getService().getTest(problemId, testId);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}

		return test;
	}

	public Test addTest(String problemId, String weight, String content)
			throws ServerResponseException {
		Test test = buildTest(null, problemId, weight, content);
		Test addedTest = addTest(problemId, test);
		return addedTest;
	}

	public Test addTest(String problemId, Test test)
			throws ServerResponseException {
		Test createTest = null;
		try {
			createTest = getService().addTest(problemId, test);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}

		return createTest;
	}

	public Test editTest(String problemId, String testId, String weight,
			String content) throws ServerResponseException {
		Test test = buildTest(testId, problemId, weight, content);
		Test editedTest = editTest(problemId, test);
		return editedTest;
	}

	public Test editTest(String problemId, Test test)
			throws ServerResponseException {
		Test editedTest = null;
		try {
			editedTest = getService().editTest(problemId, test);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}

		return editedTest;
	}

	public void deleteTest(String problemId, String testId)
			throws ServerResponseException {
		try {
			getService().deleteTest(problemId, testId);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}
	}

	public List<AuthorSolution> getAuthorSolutions(String id)
			throws ServerResponseException {
		List<AuthorSolution> solutions = null;
		try {
			solutions = getService().getAuthorSolutions(id);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}

		return solutions;
	}

	public AuthorSolution getAuthorSolution(String problemId, String solutionId)
			throws ServerResponseException {
		AuthorSolution solution = null;
		try {
			solution = getService().getAuthorSolution(problemId, solutionId);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}

		return solution;
	}

	public AuthorSolution addAuthorSolution(String problemId, String source,
			String lang) throws ServerResponseException {
		AuthorSolution solution = buildAuthorSolution(null, problemId, source,
				lang);
		AuthorSolution addedSolution = addAuthorSolution(problemId, solution);
		return addedSolution;
	}

	public AuthorSolution addAuthorSolution(String problemId,
			AuthorSolution solution) throws ServerResponseException {
		AuthorSolution createdSolution = null;
		try {
			createdSolution = getService().addAuthorSolution(problemId,
					solution);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}

		return createdSolution;
	}

	public AuthorSolution editAuthorSolution(String problemId,
			String solutionId, String source, String lang)
			throws ServerResponseException {
		AuthorSolution solution = buildAuthorSolution(solutionId, problemId,
				source, lang);
		AuthorSolution editedSolution = editAuthorSolution(problemId, solution);
		return editedSolution;
	}

	public AuthorSolution editAuthorSolution(String problemId,
			AuthorSolution solution) throws ServerResponseException {
		AuthorSolution editedSolution = null;
		try {
			editedSolution = getService().editAuthorSolution(problemId,
					solution);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}

		return editedSolution;
	}

	public void deleteAuthorSolution(String problemId, String solutionId)
			throws ServerResponseException {
		try {
			getService().deleteAuthorSolution(problemId, solutionId);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}
	}

	public List<Checker> getCheckers(String problemId)
			throws ServerResponseException {
		List<Checker> checkers = null;
		try {
			checkers = getService().getCheckers(problemId);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}

		return checkers;
	}

	public Checker getChecker(String problemId, String checkerId)
			throws ServerResponseException {
		Checker checker = null;
		try {
			checker = getService().getChecker(problemId, checkerId);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}

		return checker;
	}

	public Checker addChecker(String problemId, String source, String lang,
			String binary) throws ServerResponseException {
		Checker checker = buildChecker(null, problemId, source, lang, binary);
		Checker addedChecker = addChecker(problemId, checker);
		return addedChecker;
	}

	public Checker addChecker(String problemId, Checker checker)
			throws ServerResponseException {
		Checker addedChecker = null;
		try {
			addedChecker = getService().addChecker(problemId, checker);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}

		return addedChecker;
	}

	public Checker editChecker(String problemId, String checkerId,
			String source, String lang, String binary)
			throws ServerResponseException {
		Checker checker = buildChecker(checkerId, problemId, source, lang,
				binary);
		Checker editedChecker = editChecker(problemId, checker);
		return editedChecker;
	}

	public Checker editChecker(String problemId, Checker checker)
			throws ServerResponseException {
		Checker editedChecker = null;
		try {
			editedChecker = getService().editChecker(problemId, checker);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}

		return editedChecker;
	}

	public void deleteChecker(String problemId, String checkerId)
			throws ServerResponseException {
		try {
			getService().deleteChecker(problemId, checkerId);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}
	}

	private Problem buildProblem(String id, String type, String title,
			String description, Long timeLimit, Long memoryLimit,
			String origin, List<String> categories, List<String> authors) {

		Problem problem = new Problem();

		problem.setId(id);
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

	private Test buildTest(String id, String problemId, String weight,
			String content) {
		Test test = new Test();
		test.setId(id);
		test.setProblemId(problemId);
		test.setWeight(weight);
		test.setContent(content);
		return test;
	}

	private AuthorSolution buildAuthorSolution(String id, String problemId,
			String source, String lang) {
		AuthorSolution solution = new AuthorSolution();
		solution.setId(id);
		solution.setProblemId(problemId);
		solution.setSource(source);
		solution.setLang(lang);
		return solution;
	}

	private Checker buildChecker(String checkerId, String problemId,
			String source, String lang, String binary) {
		Checker checker = new Checker();
		checker.setId(checkerId);
		checker.setProblemId(problemId);
		checker.setSource(source);
		checker.setLang(lang);
		checker.setBinary(binary);
		return checker;
	}
}
