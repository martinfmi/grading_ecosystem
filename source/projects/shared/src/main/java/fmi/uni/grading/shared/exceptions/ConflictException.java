package fmi.uni.grading.shared.exceptions;


/**
 * Thrown in case a conflict occurs (e.g. concurrent access to a shared resource that causes a conflict).
 * 
 * @author Martin Toshev
 */
public class ConflictException extends AbstractServerException {

	private static final long serialVersionUID = -1906692108480161617L;

	public static final int HTTP_CODE = 409;

	public static final String DEFAULT_MESSAGE = "Conflict.";

	public ConflictException() {
		super(HTTP_CODE, DEFAULT_MESSAGE);
	}

	public ConflictException(Integer subcode) {
		super(HTTP_CODE, subcode, DEFAULT_MESSAGE);
	}

	public ConflictException(String message) {
		super(HTTP_CODE, message);
	}

	public ConflictException(Integer subcode, String message) {
		super(HTTP_CODE, subcode, message);
	}

	public ConflictException(Integer subcode, String message, String moreInfo) {
		super(HTTP_CODE, subcode, message, moreInfo);
	}
}
