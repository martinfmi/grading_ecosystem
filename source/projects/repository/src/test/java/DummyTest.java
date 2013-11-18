import java.util.LinkedList;
import java.util.List;

import fmi.uni.grading.shared.client.repository.ArticleClient;
import fmi.uni.grading.shared.exceptions.client.ServerResponseException;

public class DummyTest {
	
	public static void testArticles() {

	}

	public static void testProblems() {

	}

	public static void testCategories() {

	}
	
	public static void main(String[] args) throws ServerResponseException {
		ArticleClient client = new ArticleClient(
				"http://localhost:6608/services", "test", "test");
		List<String> authors = new LinkedList<String>();
		authors.add("Ivan Ivanov");
		authors.add("Petko Petrov");
		authors.add("Mariq Marieva");
		List<String> categories = new LinkedList<String>();
		categories.add("graph theory");
		client.createArticle("testXYZ", "pdf", "sample content", authors,
				categories, true, "http://someref");
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

}
