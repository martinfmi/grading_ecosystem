package fmi.uni.grading.shared.exceptions;

/**
 * Thrown in case access to a particular resource or operation is denied.
 * 
 * @author Martin Toshev
 */
public class AccessDeniedException extends AbstractServerException {

	private static final long serialVersionUID = 990588067853187976L;
 
	public static final int HTTP_CODE = 401;
	
	public static final String DEFAULT_MESSAGE = "Access denied.";
	
	public AccessDeniedException() {
		super(HTTP_CODE, DEFAULT_MESSAGE);
	}
	 
	public AccessDeniedException(Integer subcode) {
		super(HTTP_CODE, subcode, DEFAULT_MESSAGE);
	}
	
	public AccessDeniedException(String message) {
		super(HTTP_CODE, message);
	}
	
	public AccessDeniedException(Integer subcode, String message) {
		super(HTTP_CODE, subcode, message);
	}
	
	public AccessDeniedException(Integer subcode, String message, String moreInfo) {
		super(HTTP_CODE, subcode, message, moreInfo);
	}
}
