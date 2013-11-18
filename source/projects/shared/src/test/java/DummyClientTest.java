import org.apache.commons.codec.binary.Base64;

public class DummyClientTest {

	public static void createProblem() {
		// List<String> categories = new LinkedList<String>();
		// categories.add("dynamic programming");
		// List<String> authors = new LinkedList<String>();
		// authors.add("Martin Toshev");
		// ProblemClient client = new ProblemClient(
		// "http://localhost:6607/services", "admin", "admin");
		// Problem problem = client.addProblem("standard", "sample problem 234",
		// "some sample problem", 3000l, 32, "FMI-test", categories,
		// authors);
		//
		// System.err.println(problem.getId());
	}

	public static void getProblem() {
		
	}
	
	public static void main(String[] args) {
		byte[] bytes = new Base64().encode("test description !".getBytes());
		System.err.println(new String(bytes));
	}
}
