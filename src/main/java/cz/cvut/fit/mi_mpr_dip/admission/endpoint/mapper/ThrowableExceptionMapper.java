package cz.cvut.fit.mi_mpr_dip.admission.endpoint.mapper;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.stereotype.Component;

import cz.cvut.fit.mi_mpr_dip.admission.util.WebKeys;

@Component
public class ThrowableExceptionMapper extends BaseExceptionMapper<Throwable> {

	@Override
	protected void logErrorResponse(Throwable throwable) {
		getLoggingService().logErrorResponse(throwable, getResponseCode(throwable));
	}

	@Override
	protected ResponseBuilder getResponseBuilder(Throwable throwable) {
		return super.getResponseBuilder(throwable).type(MediaType.APPLICATION_XML);
	}

	@Override
	protected String createMessage(Throwable exception) {
		return WebKeys.UNEXPECTED_ERROR;
	}

	@Override
	protected Integer getResponseCode(Throwable exception) {
		return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
	}

}
