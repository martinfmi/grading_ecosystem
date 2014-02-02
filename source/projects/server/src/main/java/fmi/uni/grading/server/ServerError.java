package fmi.uni.grading.server;

/**
 * Error thrown by the application server instance in case of a critical failure that prevents
 * the system for continuing with normal operation (e.g. due to configuration problems) occurs.
 * 
 * @author Martin Toshev
 */
public class ServerError extends Error {
	
	private static final long serialVersionUID = 4500809492127790282L;
	
	public ServerError() {
		super();
	}

	public ServerError(String message) {
		super(message);
	}

	public ServerError(Throwable message) {
		super(message);
	}
	
	public ServerError(String message, Throwable throwable) {
		super(message, throwable);
	}
}
