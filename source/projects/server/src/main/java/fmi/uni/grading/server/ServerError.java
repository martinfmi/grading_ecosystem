package fmi.uni.grading.server;

/**
 * Exception thrown by the server in case of a critical failure that prevents
 * the system for continuing with normal operation.
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
