package cz.cvut.fit.mi_mpr_dip.admission.endpoint.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;

import cz.cvut.fit.mi_mpr_dip.admission.domain.ErrorResponse;
import cz.cvut.fit.mi_mpr_dip.admission.service.logging.LoggingService;
import cz.cvut.fit.mi_mpr_dip.admission.util.WebKeys;

@RooJavaBean
public abstract class BaseExceptionMapper<E extends Throwable> implements ExceptionMapper<E> {

	private static final Logger log = LoggerFactory.getLogger(BaseExceptionMapper.class);

	@Autowired
	private LoggingService loggingService;

	@Override
	public Response toResponse(E exception) {
		log.info("In exception mapper [{}]", String.valueOf(exception));
		log.debug("Exception occured", exception);

		setLogErrorResponse();
		ErrorResponse response = new ErrorResponse();
		response.setMessage(createMessage(exception));
		response.setInternalRequestId(MDC.get(WebKeys.MDC_KEY_INTERNAL_REQUEST_ID));

		logErrorResponse(exception);
		return getResponseBuilder(exception).entity(response).build();
	}

	private void setLogErrorResponse() {
		MDC.put(WebKeys.MDC_KEY_ERROR_RESPONSE, Boolean.TRUE.toString());
	}

	protected ResponseBuilder getResponseBuilder(E exception) {
		return Response.status(getResponseCode(exception));
	}

	abstract protected void logErrorResponse(E exception);

	abstract protected String createMessage(E exception);

	abstract protected Integer getResponseCode(E exception);
}