package fmi.uni.grading.repository.services;

import java.util.List;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import fmi.uni.grading.repository.db.ProblemDAO;
import fmi.uni.grading.shared.beans.AuthorSolution;
import fmi.uni.grading.shared.beans.Checker;
import fmi.uni.grading.shared.beans.Problem;
import fmi.uni.grading.shared.beans.Test;
import fmi.uni.grading.shared.exceptions.AbstractServerException;
import fmi.uni.grading.shared.exceptions.BadRequestException;
import fmi.uni.grading.shared.exceptions.MissingResourceException;
import fmi.uni.grading.shared.services.repository.IProblemService;

public class ProblemService implements IProblemService {

	@Context
	private UriInfo uriInfo;

	@Autowired
	private ProblemDAO problemDAO;

	public List<Problem> getProblems() throws AbstractServerException {
		List<Problem> problems = null;
		try {
			problems = problemDAO.getProblems();
		} catch (DataAccessException ex) {
			throw new BadRequestException(ex.getMessage());
		}
		return problems;
	}

	public Problem getProblem(String id) throws AbstractServerException {
		Problem problem = null;
		try {
			problem = problemDAO.getProblem(id);
		} catch (DataAccessException ex) {
			throw new BadRequestException(ex.getMessage());
		}

		if (problem == null) {
			throw new MissingResourceException(String.format(
					"No problem with ID '%s' found in repository.", id));
		}

		return problem;
	}

	public Problem createProblem(Problem problem)
			throws AbstractServerException {
		if (problem.getTitle() == null) {
			throw new BadRequestException("No problem title provided.");
		}

		if (problem.getId() != null) {
			throw new BadRequestException(
					"Problem ID must not be specified upon creation.");
		}

		Problem createdProblem = null;
		try {
			createdProblem = problemDAO.addProblem(problem);
		} catch (DataAccessException ex) {
			throw new BadRequestException(ex.getMessage());
		}
		return createdProblem;
	}

	public Problem editProblem(Problem problem) throws AbstractServerException {
		if (problem.getId() == null) {
			throw new BadRequestException("No problem ID provided.");
		}

		if (problem.getTitle() == null) {
			throw new BadRequestException("No problem title provided.");
		}

		Problem editedProblem = null;
		try {
			editedProblem = problemDAO.editProblem(problem);
		} catch (DataAccessException e) {
			throw new BadRequestException(e.getMessage());
		}

		return editedProblem;
	}

	public void deleteProblem(String id) throws AbstractServerException {
		try {
			problemDAO.deleteProblem(id);
		} catch (DataAccessException e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	public List<Test> getTests(String id) throws AbstractServerException {
		List<Test> tests = null;
		try {
			tests = problemDAO.getTests(id);
		} catch (DataAccessException e) {
			throw new BadRequestException(e.getMessage());
		}

		return tests;
	}

	public Test getTest(String id, String testId)
			throws AbstractServerException {
		Test test = null;
		try {
			test = problemDAO.getTest(testId);
		} catch (DataAccessException e) {
			throw new BadRequestException(e.getMessage());
		}

		if (test == null) {
			throw new MissingResourceException(String.format(
					"No test with id: '%s'.", testId));
		}

		if (!id.equals(test.getProblemId())) {
			throw new MissingResourceException(String.format(
					"No test with ID: '%s' for problem with ID: '%s'.", testId,
					id));
		}

		return test;
	}

	public Test addTest(String id, Test test) throws AbstractServerException {
		if (test.getId() != null) {
			throw new BadRequestException(
					"Test ID must not be specified upon creation.");
		}

		Test addedTest = null;
		try {
			Problem problem = problemDAO.getProblem(id);
			if (problem == null) {
				throw new MissingResourceException(String.format(
						"No problem with ID '%s' exists in repository.", id));
			}

			test.setProblemId(id);
			addedTest = problemDAO.addTest(test);
		} catch (DataAccessException ex) {
			throw new BadRequestException(ex.getMessage());
		}

		return addedTest;
	}

	public Test editTest(String id, Test test) throws AbstractServerException {
		if (test.getId() == null) {
			throw new BadRequestException("No test ID provided.");
		}

		Test editedTest = null;
		try {
			Problem problem = problemDAO.getProblem(id);
			if (problem == null) {
				throw new MissingResourceException(String.format(
						"No problem with ID '%s' exists in repository.", id));
			}

			test.setProblemId(id);
			editedTest = problemDAO.editTest(test);
		} catch (DataAccessException ex) {
			throw new BadRequestException(ex.getMessage());
		}

		return editedTest;
	}

	public void deleteTest(String id, String testId)
			throws AbstractServerException {
		try {
			Problem problem = problemDAO.getProblem(id);
			Test test = problemDAO.getTest(testId);

			if (problem == null) {
				throw new MissingResourceException(String.format(
						"No problem with ID '%s' exists in repository.", id));
			}

			if (test == null) {
				throw new MissingResourceException(String.format(
						"No test with ID '%s' exists in repository.", testId));
			}

			if (!id.equals(test.getProblemId())) {
				throw new MissingResourceException(
						String.format(
								"No test with ID '%s' exists for problem with ID '%s'.",
								testId, id));
			}

			problemDAO.deleteTest(testId);
		} catch (DataAccessException ex) {
			throw new BadRequestException(ex.getMessage());
		}
	}

	public List<AuthorSolution> getAuthorSolutions(String id)
			throws AbstractServerException {
		List<AuthorSolution> authorSolutions = null;
		try {
			authorSolutions = problemDAO.getAuthorSolutions(id);
		} catch (DataAccessException e) {
			throw new BadRequestException(e.getMessage());
		}

		return authorSolutions;
	}

	public AuthorSolution getAuthorSolution(String id, String solutionId)
			throws AbstractServerException {
		AuthorSolution solution = null;
		try {
			solution = problemDAO.getAuthorSolution(solutionId);
		} catch (DataAccessException e) {
			throw new BadRequestException(e.getMessage());
		}

		if (solution == null) {
			throw new MissingResourceException(String.format(
					"No author solution with id: '%s'.", solutionId));
		}

		if (!id.equals(solution.getProblemId())) {
			throw new MissingResourceException(
					String.format(
							"No author solution with ID: '%s' for problem with ID: '%s'",
							solutionId, id));
		}

		return solution;
	}

	public AuthorSolution addAuthorSolution(String id, AuthorSolution solution)
			throws AbstractServerException {
		if (solution.getId() != null) {
			throw new BadRequestException(
					"Author solution ID must not be specified upon creation.");
		}

		AuthorSolution addedSolution = null;
		try {
			Problem problem = problemDAO.getProblem(id);
			if (problem == null) {
				throw new MissingResourceException(String.format(
						"No problem with ID '%s' exists in repository.", id));
			}

			solution.setProblemId(id);
			addedSolution = problemDAO.addAuthorSolution(solution);
		} catch (DataAccessException ex) {
			throw new BadRequestException(ex.getMessage());
		}

		return addedSolution;
	}

	public AuthorSolution editAuthorSolution(String id, AuthorSolution solution)
			throws AbstractServerException {
		if (solution.getId() == null) {
			throw new BadRequestException("No author solution ID provided.");
		}

		AuthorSolution editedSolution = null;
		try {
			Problem problem = problemDAO.getProblem(id);
			if (problem == null) {
				throw new MissingResourceException(String.format(
						"No problem with ID '%s' exists in repository.", id));
			}

			solution.setProblemId(id);
			editedSolution = problemDAO.editAuthorSolution(solution);
		} catch (DataAccessException ex) {
			throw new BadRequestException(ex.getMessage());
		}

		return editedSolution;
	}

	public void deleteAuthorSolution(String id, String solutionId)
			throws AbstractServerException {
		try {
			Problem problem = problemDAO.getProblem(id);
			AuthorSolution solution = problemDAO.getAuthorSolution(solutionId);

			if (problem == null) {
				throw new MissingResourceException(String.format(
						"No problem with ID '%s' exists in repository.", id));
			}

			if (solution == null) {
				throw new MissingResourceException(String.format(
						"No solution with ID '%s' exists in repository.",
						solutionId));
			}

			if (!id.equals(solution.getProblemId())) {
				throw new MissingResourceException(
						String.format(
								"No solution with ID '%s' exists for problem with ID '%s'.",
								solutionId, id));
			}

			problemDAO.deleteTest(solutionId);
		} catch (DataAccessException ex) {
			throw new BadRequestException(ex.getMessage());
		}
	}
	
	public List<Checker> getCheckers(String id) throws AbstractServerException {
		List<Checker> checkers = null;
		try {
			checkers = problemDAO.getCheckers(id);
		} catch (DataAccessException e) {
			throw new BadRequestException(e.getMessage());
		}

		return checkers;
	}

	public Checker getChecker(String id, String checkerId)
			throws AbstractServerException {
		Checker checker = null;
		try {
			checker = problemDAO.getChecker(checkerId);
		} catch (DataAccessException e) {
			throw new BadRequestException(e.getMessage());
		}

		if (checker == null) {
			throw new MissingResourceException(String.format(
					"No checker with id: '%s'.", checkerId));
		}

		if (!id.equals(checker.getProblemId())) {
			throw new MissingResourceException(String.format(
					"No checker with ID: '%s' for problem with ID: '%s'",
					checkerId, id));
		}

		return checker;
	}

	public Checker addChecker(String id, Checker checker)
			throws AbstractServerException {
		if (checker.getId() != null) {
			throw new BadRequestException(
					"Checker ID must not be specified upon creation.");
		}

		Checker addedChecker = null;
		try {
			Problem problem = problemDAO.getProblem(id);
			if (problem == null) {
				throw new MissingResourceException(String.format(
						"No problem with ID '%s' exists in repository.", id));
			}

			checker.setProblemId(id);
			addedChecker = problemDAO.addChecker(checker);
		} catch (DataAccessException ex) {
			throw new BadRequestException(ex.getMessage());
		}

		return addedChecker;
	}

	public Checker editChecker(String id, Checker checker)
			throws AbstractServerException {
		if (checker.getId() == null) {
			throw new BadRequestException("No checker ID provided.");
		}

		Checker editedChecker = null;
		try {
			Problem problem = problemDAO.getProblem(id);
			if (problem == null) {
				throw new MissingResourceException(String.format(
						"No problem with ID '%s' exists in repository.", id));
			}

			checker.setProblemId(id);
			editedChecker = problemDAO.editChecker(checker);
		} catch (DataAccessException ex) {
			throw new BadRequestException(ex.getMessage());
		}

		return editedChecker;
	}

	public void deleteChecker(String id, String checkerId)
			throws AbstractServerException {
		try {
			Problem problem = problemDAO.getProblem(id);
			Checker checker = problemDAO.getChecker(checkerId);
			
			if (problem == null) {
				throw new MissingResourceException(String.format(
						"No problem with ID '%s' exists in repository.", id));
			}

			if (checker == null) {
				throw new MissingResourceException(String.format(
						"No checker with ID '%s' exists in repository.",
						checkerId));
			}

			if (!id.equals(checker.getProblemId())) {
				throw new MissingResourceException(
						String.format(
								"No checker with ID '%s' exists for problem with ID '%s'.",
								checkerId, id));
			}
			
			problemDAO.deleteChecker(checkerId);
		} catch (DataAccessException ex) {
			throw new BadRequestException(ex.getMessage());
		}
	}
}
