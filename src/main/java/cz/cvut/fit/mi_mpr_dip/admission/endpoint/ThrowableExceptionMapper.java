package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.logging.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.cvut.fit.mi_mpr_dip.admission.exception.BusinessException;
import cz.cvut.fit.mi_mpr_dip.admission.exception.TechnicalException;
import cz.cvut.fit.mi_mpr_dip.admission.service.logging.LoggingService;
import cz.cvut.fit.mi_mpr_dip.admission.util.WebKeys;

@Component
public class ThrowableExceptionMapper extends AdmissionExceptionMapper<Throwable> {

	@Autowired
	private BusinessExceptionMapper businessExceptionMapper;

	@Autowired
	private TechnicalExceptionMapper technicalExceptionMapper;

	@Autowired
	private LoggingService loggingService;

	@Override
	public Response toResponse(Throwable exception) {
		MDC.put(WebKeys.MDC_KEY_ERROR_RESPONSE, Boolean.TRUE);
		Response response;
		if (exception instanceof BusinessException) {
			response = businessExceptionMapper.toResponse((BusinessException) exception);
		} else if (exception instanceof TechnicalException) {
			response = technicalExceptionMapper.toResponse((TechnicalException) exception);
		} else {
			loggingService.logErrorResponse(exception);
			response = Response.fromResponse(super.toResponse(exception)).type(MediaType.APPLICATION_XML).build();
		}
		return response;
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
