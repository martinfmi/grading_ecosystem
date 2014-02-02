import java.util.LinkedList;
import java.util.List;

import fmi.uni.grading.shared.beans.TutorialEntry;
import fmi.uni.grading.shared.beans.TutorialEntry.EntryType;
import fmi.uni.grading.shared.client.server.GraderClient;
import fmi.uni.grading.shared.client.server.TutorialClient;
import fmi.uni.grading.shared.exceptions.AbstractServerException;
import fmi.uni.grading.shared.exceptions.client.ServerResponseException;

public class DummyTest {

	public static void testTutorials() throws ServerResponseException {
		TutorialClient client = new TutorialClient(
				"http://localhost:6608/services", "test", "test");
		List<TutorialEntry> entries = new LinkedList<TutorialEntry>();

		// article 1
		TutorialEntry article = new TutorialEntry();
		article.setLocation("some location");
		article.setName("Article 1");
		article.setType(EntryType.ARTICLE);
		article.setRef("1");
		entries.add(article);

		// problem 1
		TutorialEntry problem1 = new TutorialEntry();
		problem1.setLocation("some location");
		problem1.setName("Article 1");
		problem1.setType(EntryType.PROBLEM);
		problem1.setRef("1");
		entries.add(problem1);

		// problem 2
		TutorialEntry problem2 = new TutorialEntry();
		problem2.setLocation("some other location");
		problem2.setName("Article 2");
		problem2.setType(EntryType.PROBLEM);
		problem2.setRef("2");
		entries.add(problem2);

		client.createTutorial("Graph theory", entries);
	}

	public static void testGrader() throws ServerResponseException {
		GraderClient client = new GraderClient(
				"http://localhost:6608/services", "test", "test");
		
	}
	
	public static void main(String[] args) throws ServerResponseException {
		try {
			testTutorials();
		} catch (ServerResponseException ex) {
			ex.getCause().printStackTrace();
			AbstractServerException serverEx = (AbstractServerException) ex
					.getCause();
			System.err.println(serverEx.getResponse().getEntity());
		}
	}

}
