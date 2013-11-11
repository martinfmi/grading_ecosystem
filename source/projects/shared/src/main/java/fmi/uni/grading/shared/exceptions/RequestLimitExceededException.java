package fmi.uni.grading.shared.exceptions;


/**
 * Thrown in case maximum number of requests is reached.
 * 
 * @author Martin Toshev
 */
public class RequestLimitExceededException extends AbstractServerException {

	private static final long serialVersionUID = -2780574987911283274L;

	public static final int HTTP_CODE = 429;

	public static final String DEFAULT_MESSAGE = "Too many requests.";

	public RequestLimitExceededException() {
		super(HTTP_CODE, DEFAULT_MESSAGE);
	}

	public RequestLimitExceededException(Integer subcode) {
		super(HTTP_CODE, subcode, DEFAULT_MESSAGE);
	}

	public RequestLimitExceededException(String message) {
		super(HTTP_CODE, message);
	}

	public RequestLimitExceededException(Integer subcode, String message) {
		super(HTTP_CODE, subcode, message);
	}

	public RequestLimitExceededException(Integer subcode, String message, String moreInfo) {
		super(HTTP_CODE, subcode, message, moreInfo);
	}
}
