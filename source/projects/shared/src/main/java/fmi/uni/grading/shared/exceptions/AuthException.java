package fmi.uni.grading.shared.exceptions;

/**
 * Thrown in case unauthorized access to a resource or operation is attempted.
 * 
 * @author Martin Toshev
 */
public class AuthException extends AbstractServerException {
	
	private static final long serialVersionUID = 4646329689482215755L;

	public static final int HTTP_CODE = 403;
	
	public static final String DEFAULT_MESSAGE = "Unauthorized access.";
	
	public AuthException() {
		super(HTTP_CODE, DEFAULT_MESSAGE);
	}
	 
	public AuthException(Integer subcode) {
		super(HTTP_CODE, subcode, DEFAULT_MESSAGE);
	}
	
	public AuthException(String message) {
		super(HTTP_CODE, message);
	}
	
	public AuthException(Integer subcode, String message) {
		super(HTTP_CODE, subcode, message);
	}
	
	public AuthException(Integer subcode, String message, String moreInfo) {
		super(HTTP_CODE, subcode, message, moreInfo);
	}
}
