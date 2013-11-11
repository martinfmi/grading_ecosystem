package fmi.uni.grading.shared.exceptions;


/**
 * Thrown in case of a missing resource.
 * 
 * @author Martin Toshev
 */
public class MissingResourceException extends AbstractServerException {

	private static final long serialVersionUID = 8716069744760208233L;

	public static final int HTTP_CODE = 404;

	public static final String DEFAULT_MESSAGE = "Resource not found.";

	public MissingResourceException() {
		super(HTTP_CODE, DEFAULT_MESSAGE);
	}

	public MissingResourceException(Integer subcode) {
		super(HTTP_CODE, subcode, DEFAULT_MESSAGE);
	}

	public MissingResourceException(String message) {
		super(HTTP_CODE, message);
	}

	public MissingResourceException(Integer subcode, String message) {
		super(HTTP_CODE, subcode, message);
	}

	public MissingResourceException(Integer subcode, String message, String moreInfo) {
		super(HTTP_CODE, subcode, message, moreInfo);
	}
}
