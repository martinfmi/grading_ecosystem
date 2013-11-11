package fmi.uni.grading.shared.exceptions;


/**
 * Thrown in case invalid format is provided in a query request (e.g. when
 * searching for a resource).
 * 
 * @author Martin Toshev
 */
public class InvalidFormatException extends AbstractServerException {

	private static final long serialVersionUID = -4896385868916426464L;

	public static final int HTTP_CODE = 406;

	public static final String DEFAULT_MESSAGE = "Invalid format.";

	public InvalidFormatException() {
		super(HTTP_CODE, DEFAULT_MESSAGE);
	}

	public InvalidFormatException(Integer subcode) {
		super(HTTP_CODE, subcode, DEFAULT_MESSAGE);
	}

	public InvalidFormatException(String message) {
		super(HTTP_CODE, message);
	}

	public InvalidFormatException(Integer subcode, String message) {
		super(HTTP_CODE, subcode, message);
	}

	public InvalidFormatException(Integer subcode, String message, String moreInfo) {
		super(HTTP_CODE, subcode, message, moreInfo);
	}
}
