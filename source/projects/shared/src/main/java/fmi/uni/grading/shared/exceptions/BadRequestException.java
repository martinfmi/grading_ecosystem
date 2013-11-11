package fmi.uni.grading.shared.exceptions;

/**
 * Thrown in case a malformed request is being sent.
 * 
 * @author Martin Toshev
 */
public class BadRequestException extends AbstractServerException {

	private static final long serialVersionUID = 6827128581012242711L;

	public static final int HTTP_CODE = 400;

	public static final String DEFAULT_MESSAGE = "Bad request.";

	public BadRequestException() {
		super(HTTP_CODE, DEFAULT_MESSAGE);
	}

	public BadRequestException(Integer subcode) {
		super(HTTP_CODE, subcode, DEFAULT_MESSAGE);
	}

	public BadRequestException(String message) {
		super(HTTP_CODE, message);
	}

	public BadRequestException(Integer subcode, String message) {
		super(HTTP_CODE, subcode, message);
	}

	public BadRequestException(Integer subcode, String message, String moreInfo) {
		super(HTTP_CODE, subcode, message, moreInfo);
	}
}
