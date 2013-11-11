package fmi.uni.grading.shared.exceptions.client;

import fmi.uni.grading.shared.exceptions.AbstractServerException;

/**
 * Cheched exception thrown that wraps an {@link AbstractServerException} and
 * must be handled by users of the client. The original
 * {@link AbstractServerException} exception might be retrieved by invoking the
 * <b>getCause()</b> method.
 * 
 * @author Martin Toshev
 */
public class ServerResponseException extends Exception {

	private static final long serialVersionUID = -5065227992243528558L;

	/**
	 * @param exception
	 *            The original {@link AbstractServerException} that is being
	 *            wrapped.
	 */
	public ServerResponseException(AbstractServerException exception) {
		super(exception);
	}
}
