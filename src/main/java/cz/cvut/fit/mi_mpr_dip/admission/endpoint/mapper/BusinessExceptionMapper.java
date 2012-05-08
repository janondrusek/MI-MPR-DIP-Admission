package cz.cvut.fit.mi_mpr_dip.admission.endpoint.mapper;

import javax.ws.rs.ext.ExceptionMapper;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import cz.cvut.fit.mi_mpr_dip.admission.exception.BusinessException;

@Component
public class BusinessExceptionMapper extends BaseExceptionMapper<BusinessException> implements
		ExceptionMapper<BusinessException> {

	@Override
	protected void logErrorResponse(BusinessException exception) {
		getLoggingService().logErrorResponse(exception);
	}

	@Override
	protected String createMessage(BusinessException exception) {
		String message = exception.getMessage();
		if (CollectionUtils.isNotEmpty(exception.getConstraintViolations())) {
			message = exception.getConstraintViolations().toString();
		}
		return message;
	}

	@Override
	protected Integer getResponseCode(BusinessException exception) {
		return exception.getResponseCode();
	}

}
