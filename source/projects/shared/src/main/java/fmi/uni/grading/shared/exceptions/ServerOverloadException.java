package fmi.uni.grading.shared.exceptions;

/**
 * Thrown in case of server overload (e.g. memory limit or CPU usage limit reached).
 * 
 * @author Martin Toshev
 */
public class ServerOverloadException extends AbstractServerException {

	private static final long serialVersionUID = -8880078904425709098L;
	
	public static final int HTTP_CODE = 503;

	public static final String DEFAULT_MESSAGE = "Service unavailable.";
	
	public ServerOverloadException() {
		super(HTTP_CODE, DEFAULT_MESSAGE);
	}

	public ServerOverloadException(Integer subcode) {
		super(HTTP_CODE, subcode, DEFAULT_MESSAGE);
	}

	public ServerOverloadException(String message) {
		super(HTTP_CODE, message);
	}
	
	public ServerOverloadException(Integer subcode, String message) {
		super(HTTP_CODE, subcode, message);
	}

	public ServerOverloadException(Integer subcode, String message, String moreInfo) {
		super(HTTP_CODE, subcode, message, moreInfo);
	}
}
