package fmi.uni.grading.shared.exceptions.handlers.server;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.apache.log4j.Logger;

import fmi.uni.grading.shared.beans.ErrorResponse;
import fmi.uni.grading.shared.exceptions.AbstractServerException;
import fmi.uni.grading.shared.exceptions.ServerException;

/**
 * Maps a server exception to an appropriate HTTP response in the format:
 * 
 * <pre>
 * {{@code	
 * 		"code" : 401
 * 		"message" : "Unauthorized"
 * }
 * }
 * </pre>
 * 
 * @author Martin Toshev
 * 
 */
public class ServerExceptionHandler implements
		ExceptionMapper<AbstractServerException> {

	private static final Logger LOGGER = Logger
			.getLogger(ServerExceptionHandler.class);

	public Response toResponse(AbstractServerException exception) {
		Response errorResponse = buildResponse(exception);
		return errorResponse;
	}

	private Response buildResponse(AbstractServerException exception) {

		ErrorResponse response = new ErrorResponse();
		response.setCode(exception.getSubcode());
		response.setMoreInfo(exception.getMoreInfo());

		Response exceptionResponse = exception.getResponse();

		Response result = null;
		if (exceptionResponse != null) {

			if (exceptionResponse.getEntity() != null) {
				response.setMessage(exceptionResponse.getEntity().toString());
			}

			result = Response.status(exceptionResponse.getStatus())
					.entity(response).build();
		} else {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Empty response for exception of type: "
						+ exception.getClass().getName());
			}
			result = Response.status(ServerException.HTTP_CODE)
					.entity(response).build();
		}

		return result;
	}
}
