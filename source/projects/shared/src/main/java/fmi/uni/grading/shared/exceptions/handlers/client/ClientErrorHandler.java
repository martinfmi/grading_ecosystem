package fmi.uni.grading.shared.exceptions.handlers.client;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.ResponseExceptionMapper;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import fmi.uni.grading.shared.beans.ErrorResponse;
import fmi.uni.grading.shared.exceptions.AbstractServerException;
import fmi.uni.grading.shared.exceptions.AccessDeniedException;
import fmi.uni.grading.shared.exceptions.AuthException;
import fmi.uni.grading.shared.exceptions.BadRequestException;
import fmi.uni.grading.shared.exceptions.ConflictException;
import fmi.uni.grading.shared.exceptions.InvalidFormatException;
import fmi.uni.grading.shared.exceptions.MissingResourceException;
import fmi.uni.grading.shared.exceptions.PreconditionFailedException;
import fmi.uni.grading.shared.exceptions.RequestLimitExceededException;
import fmi.uni.grading.shared.exceptions.ServerException;
import fmi.uni.grading.shared.exceptions.ServerOverloadException;

/**
 * Maps a client response to an exception.
 * 
 * @author Martin Toshev
 */
public class ClientErrorHandler implements
		ResponseExceptionMapper<AbstractServerException> {

	private static final Logger LOGGER = Logger
			.getLogger(ResponseExceptionMapper.class);

	public AbstractServerException fromResponse(Response response) {

		ObjectMapper mapper = new ObjectMapper();
		ErrorResponse errorResponse = null;

		try {
			errorResponse = mapper.readValue(
					(InputStream) response.getEntity(), ErrorResponse.class);
			
		} catch (JsonParseException e) {
			errorResponse = getUnparsedErrorResponse(e, response);
		} catch (JsonMappingException e) {
			errorResponse = getUnparsedErrorResponse(e, response);
		} catch (IOException e) {
			errorResponse = getUnparsedErrorResponse(e, response);
		}
		
		AbstractServerException result = null;
		Integer subcode = errorResponse.getCode();
		String message = errorResponse.getMessage();
		String moreInfo = errorResponse.getMoreInfo();

		switch (response.getStatus()) {
		case AccessDeniedException.HTTP_CODE:
			result = new AccessDeniedException(subcode, message, moreInfo);
			break;
		case AuthException.HTTP_CODE:
			result = new AuthException(subcode, message, moreInfo);
			break;
		case BadRequestException.HTTP_CODE:
			result = new BadRequestException(subcode, message, moreInfo);
			break;
		case ConflictException.HTTP_CODE:
			result = new BadRequestException(subcode, message, moreInfo);
			break;
		case InvalidFormatException.HTTP_CODE:
			result = new BadRequestException(subcode, message, moreInfo);
			break;
		case MissingResourceException.HTTP_CODE:
			result = new BadRequestException(subcode, message, moreInfo);
			break;
		case PreconditionFailedException.HTTP_CODE:
			result = new BadRequestException(subcode, message, moreInfo);
			break;
		case RequestLimitExceededException.HTTP_CODE:
			result = new BadRequestException(subcode, message, moreInfo);
			break;
		case ServerException.HTTP_CODE:
			result = new BadRequestException(subcode, message, moreInfo);
			break;
		case ServerOverloadException.HTTP_CODE:
			result = new BadRequestException(subcode, message, moreInfo);
			break;
		default:
			result = new ServerException(subcode, message, moreInfo);
		}

		return result;
	}

	private ErrorResponse getUnparsedErrorResponse(Exception exception,
			Response response) {
		LOGGER.warn("Failed to parse error response.", exception);
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage(response.getEntity().toString());

		return errorResponse;
		
	}
}
