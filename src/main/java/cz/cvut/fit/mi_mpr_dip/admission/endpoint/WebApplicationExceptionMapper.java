package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import cz.cvut.fit.mi_mpr_dip.admission.exception.BusinessException;

@Component
public class WebApplicationExceptionMapper extends AdmissionExceptionMapper<WebApplicationException> implements
		ExceptionMapper<WebApplicationException> {

	@Override
	protected void logErrorResponse(WebApplicationException exception) {
		getLoggingService().logErrorResponse(
				new BusinessException(new Integer(exception.getResponse().getStatus()), exception));
	}

	@Override
	protected ResponseBuilder getResponseBuilder(WebApplicationException exception) {
		return super.getResponseBuilder(exception).type(MediaType.APPLICATION_XML);
	}

	@Override
	protected String createMessage(WebApplicationException exception) {
		return ObjectUtils.toString(exception.getResponse().getEntity());
	}

	@Override
	protected Integer getResponseCode(WebApplicationException exception) {
		return exception.getResponse().getStatus();
	}

}
