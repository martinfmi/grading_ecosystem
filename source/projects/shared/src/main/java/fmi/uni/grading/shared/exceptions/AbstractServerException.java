package fmi.uni.grading.shared.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Super class for all mapped exceptions that provides additional error code, message and error description.
 * All of the additional error information is optional and is set depending on the error conditions.
 * 
 * @author Martin Toshev
 */
public abstract class AbstractServerException extends WebApplicationException {

	private static final long serialVersionUID = 8571056389059832122L;

	private Integer subcode;
	
	private String moreInfo;
	
	/**
	 * @param code HTTP response code.
	 */
	public AbstractServerException(Integer code) {
		super(code);
	}
	
	/**
	 * @param code HTTP response code.
	 * @param subcode Error subcode
	 */
	public AbstractServerException(Integer code, Integer subcode) {
		super(code);
		this.subcode = subcode;
	}
	
	/**
	 * @param code HTTP response code
	 * @param message Error message.
	 */
	public AbstractServerException(Integer code, String message) {
		super(Response.status(code).entity(message).build());
	}
	
	/**
	 * @param code HTTP response code
	 * @param subcode Error subcode
	 * @param message Error messageAbstractServerException
	 */
	public AbstractServerException(Integer code, Integer subcode, String message) {
		super(Response.status(code).entity(message).build());
		this.subcode = subcode;
	}
	
	/**
	 * @param code HTTP response code
	 * @param subcode Error subcode
	 * @param message Error message
	 * @param moreInfo URL to additional error information
	 */
	public AbstractServerException(Integer code, Integer subcode, String message, String moreInfo) {
		this(code, subcode, message);
		this.moreInfo = moreInfo;
	}
	
	public Integer getSubcode() {
		return subcode;
	}
	
	public String getMoreInfo() {
		return moreInfo;
	}
}
