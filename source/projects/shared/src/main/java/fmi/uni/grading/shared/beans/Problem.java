package fmi.uni.grading.shared.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a programming problem.
 * 
 * @author Martin Toshev
 */
@XmlRootElement(name = "problem")
@Document(collection = "problems")
@TypeAlias("problem") // stored in the '_class' attribute instead of the class FQDN \
public class Problem {

	@Id
	private String id;

	private String type;

	private String title;

	private String description;

	private Long timeLimit;

	private Long memoryLimit;

	private String origin;

	private List<String> categories;

	private List<String> authors;

	// private List<Integer> testWeights;
	//
	// private String checker;
	//
	// private List<String> authorSolutions;
	//
	// private List<String> tests;

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Retrieves the problem ID once the problem is persisted in the repository.
	 * 
	 * @return The problem ID.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the type of the problem. The type is provided by the expected input.
	 * Standard algorithm problems that require full problem submissions might
	 * have a type of "standard" while quiz problem might have a type of "quiz".
	 * Other problem types might be derived from the particular artifact being
	 * submitted - e.g. "block", "method", "class". Types are not restricted by
	 * the system.
	 * 
	 * @return The type of the problem.
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type of a problem.
	 * 
	 * @param type
	 *            Problem type.
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return The title of the problem.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title of a problem.
	 * 
	 * @param title
	 *            Problem title.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return The problem description with (possibly) HTML markup for display.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the problem's description. Might contain HTML markup for display.
	 * 
	 * @param description
	 *            The problem description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return The time limit for the problem in milliseconds.
	 */
	public Long getTimeLimit() {
		return timeLimit;
	}

	/**
	 * Sets the time limit for a problem.
	 * 
	 * @param timeLimit
	 *            Time limit in milliseconds.
	 */
	public void setTimeLimit(Long timeLimit) {
		this.timeLimit = timeLimit;
	}

	/**
	 * @return The memory limit for the problem in milliseconds.
	 */
	public Long getMemoryLimit() {
		return memoryLimit;
	}

	/**
	 * Sets the memory limit of a problem.
	 * 
	 * @param memoryLimit
	 *            Memory limit for the problem in milliseconds.
	 */
	public void setMemoryLimit(long memoryLimit) {
		this.memoryLimit = memoryLimit;
	}

	/**
	 * @return The origin of the problem.
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * Sets the origin of a problem. This could be a particular grading system,
	 * book or other source from which the problem originated.
	 * 
	 * @param origin
	 *            Problem origin.
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * @return The problem' categories (e.g. "graphs", "dynamic programming").
	 */
	public List<String> getCategories() {
		return categories;
	}

	/**
	 * @param categories
	 *            Sets the problem's categories.
	 */
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	/**
	 * @return Retrieves the problem's authors.
	 */
	public List<String> getAuthors() {
		return authors;
	}

	/**
	 * Sets the problem's authors.
	 * 
	 * @param authors
	 *            Problem's authors.
	 */
	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}

	// /**
	// * @return The weights of the test cases. Weights represent the importance
	// * of the test data upon the problem. The number of weights must be
	// * the same as the number of test cases. Typically weights do not
	// * apply for problems with checkers.
	// */
	// public List<Integer> getTestWeights() {
	// return testWeights;
	// }
	//
	// /**
	// * Sets the weights of the test cases.
	// *
	// * @param testWeights
	// * Weights of the test cases.
	// */
	// public void setTestWeights(List<Integer> testWeights) {
	// this.testWeights = testWeights;
	// }
	//
	// /**
	// * @return The source code for the problem's checker (if any).
	// */
	// public String getChecker() {
	// return checker;
	// }
	//
	// /**
	// * Sets the source code for the checker (if any).
	// *
	// * @param checker
	// * The source code of the checker.
	// */
	// public void setChecker(String checker) {
	// this.checker = checker;
	// }
	//
	// /**
	// * @return Source code of solutions from the authors of the problem.
	// */
	// public List<String> getAuthorSolutions() {
	// return authorSolutions;
	// }
	//
	// /**
	// * Sets the source code for the solutions from the authors of the problem.
	// *
	// * @param authorSolutions
	// * The solutions of the authors.
	// */
	// public void setAuthorSolutions(List<String> authorSolutions) {
	// this.authorSolutions = authorSolutions;
	// }
	//
	// /**
	// * @return The test cases for the problem.
	// */
	// public List<String> getTests() {
	// return tests;
	// }
	//
	// /**
	// * Sets the test cases for the problem.
	// *
	// * @param tests
	// * Test data.
	// */
	// public void setTests(List<String> tests) {
	// this.tests = tests;
	// }

	@Override
	public String toString() {
		String result = "[type = " + type + ",\\n" //
				+ "title = " + title + ",\\n" //
				+ "description = " + description + ",\\n" //
				+ "timeLimit = " + timeLimit + ",\\n" //
				+ "memoryLimit = " + memoryLimit + ",\\n" //
				+ "origin = " + memoryLimit + ",\\n" //
				+ "categories = " + categories + ",\\n" //
				+ "authors = " + categories + "]\\n"; //

		return result;
	}
}
