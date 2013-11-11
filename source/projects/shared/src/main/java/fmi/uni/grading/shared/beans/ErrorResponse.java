package fmi.uni.grading.shared.beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Returned in case of HTTP error response.
 * 
 * @author Martin Toshev
 */
@XmlRootElement(name = "error")
public class ErrorResponse {

	private Integer code;

	private String message;

	private String moreInfo;

	/**
	 * @return Subcode of the error response (the code is the HTTP code).
	 */
	@XmlElement(name = "code")
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	@XmlElement(name = "message")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return Additional info about the problem. Might be a URL to the problem
	 *         description.
	 */
	@XmlElement(name = "more_info")
	public String getMoreInfo() {
		return moreInfo;
	}

	public void setMoreInfo(String moreInfo) {
		this.moreInfo = moreInfo;
	}
}
