package cz.cvut.fit.mi_mpr_dip.admission.endpoint.mapper;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.ext.ExceptionMapper;
import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Component;

import cz.cvut.fit.mi_mpr_dip.admission.exception.BusinessException;

@Component
public class JaxbExceptionMapper extends BaseExceptionMapper<JAXBException> implements
		ExceptionMapper<JAXBException> {

	@Override
	protected void logErrorResponse(JAXBException exception) {
		getLoggingService().logErrorResponse(new BusinessException(getResponseCode(exception), exception));
	}

	@Override
	protected String createMessage(JAXBException exception) {
		return exception.getMessage();
	}

	@Override
	protected Integer getResponseCode(JAXBException exception) {
		return HttpServletResponse.SC_BAD_REQUEST;
	}

}
