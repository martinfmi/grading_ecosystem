package fmi.uni.grading.repository;

/**
 * Error thrown by the repository instance in case of a critical failure that prevents
 * the system for continuing with normal operation (e.g. due to configuration problems) occurs.
 * 
 * @author Martin Toshev
 */
public class RepositoryError extends Error {
	
	private static final long serialVersionUID = 4500809492127790282L;

	public RepositoryError() {
		super();
	}

	public RepositoryError(String message) {
		super(message);
	}

	public RepositoryError(Throwable message) {
		super(message);
	}

	public RepositoryError(String message, Throwable throwable) {
		super(message, throwable);
	}
}
