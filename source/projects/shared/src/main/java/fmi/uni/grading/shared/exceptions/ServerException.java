package fmi.uni.grading.shared.exceptions;


/**
 * Thrown in case of an unknown server exception.
 * 
 * @author Martin Toshev
 */
public class ServerException extends AbstractServerException {

	private static final long serialVersionUID = 3847076240890083394L;

	public static final int HTTP_CODE = 500;

	public static final String DEFAULT_MESSAGE = "Internal server error.";

	public ServerException() {
		super(HTTP_CODE, DEFAULT_MESSAGE);
	}

	public ServerException(Integer subcode) {
		super(HTTP_CODE, subcode, DEFAULT_MESSAGE);
	}

	public ServerException(String message) {
		super(HTTP_CODE, message);
	}

	public ServerException(Integer subcode, String message) {
		super(HTTP_CODE, subcode, message);
	}

	public ServerException(Integer subcode, String message, String moreInfo) {
		super(HTTP_CODE, subcode, message, moreInfo);
	}
}
