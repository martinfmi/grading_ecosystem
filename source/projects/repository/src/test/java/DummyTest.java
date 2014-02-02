import java.util.LinkedList;
import java.util.List;

import fmi.uni.grading.shared.client.UserClient;
import fmi.uni.grading.shared.client.repository.ArticleClient;
import fmi.uni.grading.shared.client.repository.CategoryClient;
import fmi.uni.grading.shared.client.repository.ProblemClient;
import fmi.uni.grading.shared.exceptions.AbstractServerException;
import fmi.uni.grading.shared.exceptions.client.ServerResponseException;

public class DummyTest {

	public static void testArticles() throws ServerResponseException {
		ArticleClient client = new ArticleClient(
				"http://localhost:6608/services", "test", "test");
//		List<String> authors = new LinkedList<String>();
//		authors.add("Ivan Ivanov");
//		authors.add("Petko Petrov");
//		authors.add("Mariq Marieva");
//		List<String> categories = new LinkedList<String>();
//		// categories.add("graph theory");
//		categories.add("dynamic programming");
//		
//		client.createArticle("sample article 123", "txt", "sample content 2", authors,
//				categories, true, "http://someotheref");
		client.deleteArticle("5294c16da518d70e901d56aa");
		// // Article article = client.getArticle("5280c39a55a2ec54ffc06d63");
		// // System.err.println(article.getTitle());
		// // System.err.println(client.getArticles());
		// // System.err.println(client.getArticles());
		// Article article = client.editArticle("5280e7f655a21b1edb1fc967",
		// "test12", "pdf", "", null, null, false, null);
		// //
		// // System.err.println(article.getId());
		// // client.deleteArticle("5280df3655a2075785bf6e59");
		// System.err.println(client.getArticles());
	}

	public static void testProblems() throws ServerResponseException {
		ProblemClient client = new ProblemClient(
				"http://localhost:6608/services", "test", "test");

		List<String> authors = new LinkedList<String>();
		authors.add("Ivan Ivanov");
		authors.add("Petko Petrov");
		List<String> categories = new LinkedList<String>();
		// categories.add("graph theory");
		categories.add("dynamic programming");

		// client.createProblem("text",
		// "Sample problem 4", "Sample problem descr 4.", 2000l, 32l,
		// "USACO", categories, authors);
		// client.editProblem("528a1b5b92e4b8e5cee9d3e0", "text",
		// "Sample problem 3", "Sample problem descr 3.", 2000l, 32l,
		// "USACO", categories, authors);
		// client.getProblem("528a196392e4c5a153a86520");
		// client.deleteProblem("528a1b5b92e4b8e5cee9d3e0");
		// client.addTest("528a1ebb92e4b8e5cee9d3e1", "1", "test data 3");
		// client.getTest("528a1ebb92e4b8e5cee9d3e1",
		// "528a229492e4a1fb1012d4ef");
		// client.editTest("528a1ebb92e4b8e5cee9d3e1",
		// "528a229492e4a1fb1012d4ef", "2", "sample test content");
		// client.deleteTest("528a1ebb92e4b8e5cee9d3e1",
		// "528a229492e4a1fb1012d4ef");
		// client.editAuthorSolution("528a1ebb92e4b8e5cee9d3e1",
		// "528a2b8a92e45bb46517377f", "//", "c");
		// client.getAuthorSolution("528a1ebb92e4b8e5cee9d3e1",
		// "528a2b1c92e45bb46517377e");
		// client.deleteAuthorSolution("528a1ebb92e4b8e5cee9d3e1",
		// "528a2b8a92e45bb46517377f");
		// client.addChecker("528a1ebb92e4b8e5cee9d3e1", "", "java", "");
		// client.editChecker("528a1ebb92e4b8e5cee9d3e1",
		// "528a2c3892e45bb465173781", "123", "c", "xyz");
		// client.getCheckers("528a1ebb92e4b8e5cee9d3e1");
		// client.deleteChecker("528a1ebb92e4b8e5cee9d3e1",
		// "528a2c3892e45bb465173781");
	}

	public static void testUser() throws ServerResponseException {
		UserClient client = new UserClient("http://localhost:6608/services",
				"test", "test");
		// client.createUser("martinfmi", "Martin", "Testaa1", Role.CONTESTANT,
		// "city:Sofia", Permissions.READ_ONLY);
		// client.editUser("528a381292e4ef31ff77aac0", "martinfmi", "Martin3",
		// "Testaa3", "marto8808@gmail.com", Role.TEACHER, "city:Sofia",
		// Permissions.READ_ONLY);
		// client.getUser("528a379a92e4ef31ff77aabf");
		// client.getUsers();
		// client.deleteUser("528a381292e4ef31ff77aac0");
	}
	
	public static void testCategories() throws ServerResponseException {
		CategoryClient client = new CategoryClient(
				"http://localhost:6608/services", "test", "test");
		// Category category = client.createCategory("dynamic programming 2",
		// "Dynamic programming problems.", "123");
		// System.err.println(category);
		// client.editCategory("528a0e1792e470881248d719",
		// "dynamic programming 3", "Dynamic programming problems 3.",
		// "456");
		// client.deleteCategory("528a0e1792e470881248d719");
		// client.getCategoryArticles("528a0d7e92e470881248d717");
		client.getCategoryProblems("528a0d7e92e470881248d717");
	}

	public static void main(String[] args) throws ServerResponseException {
		try {
			// testCategories();
			 testArticles();
			// testProblems();
			 // testUser();
		} catch (ServerResponseException ex) {
			ex.getCause().printStackTrace();
			AbstractServerException serverEx = (AbstractServerException) ex
					.getCause();
			System.err.println(serverEx.getResponse().getEntity());
		}
	}

}
