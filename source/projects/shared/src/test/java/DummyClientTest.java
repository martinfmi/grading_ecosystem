import org.apache.commons.codec.binary.Base64;



public class DummyClientTest {

	public static void createProblem() {
		
	}
	
	public static void getProblem() { 
		
	}
	
	public static void main(String[] args) {
		byte[] bytes = new Base64().encode("test description !".getBytes());
		System.err.println(new String(bytes));
	}
}
