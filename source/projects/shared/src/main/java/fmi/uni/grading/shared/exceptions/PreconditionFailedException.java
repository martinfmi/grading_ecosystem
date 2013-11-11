package fmi.uni.grading.shared.exceptions;


/**
 * Thrown in case a precondition is not met when accessing a resource or an operation.
 * 
 * @author Martin Toshev
 */
public class PreconditionFailedException extends AbstractServerException {

	private static final long serialVersionUID = -1606477558157120453L;

	public static final int HTTP_CODE = 412;

	public static final String DEFAULT_MESSAGE = "Precondition failed.";

	public PreconditionFailedException() {
		super(HTTP_CODE, DEFAULT_MESSAGE);
	}

	public PreconditionFailedException(Integer subcode) {
		super(HTTP_CODE, subcode, DEFAULT_MESSAGE);
	}

	public PreconditionFailedException(String message) {
		super(HTTP_CODE, message);
	}

	public PreconditionFailedException(Integer subcode, String message) {
		super(HTTP_CODE, subcode, message);
	}

	public PreconditionFailedException(Integer subcode, String message, String moreInfo) {
		super(HTTP_CODE, subcode, message, moreInfo);
	}
}
