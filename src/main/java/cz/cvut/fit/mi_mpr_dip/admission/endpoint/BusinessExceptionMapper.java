package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import org.springframework.stereotype.Component;

import cz.cvut.fit.mi_mpr_dip.admission.exception.BusinessException;

@Component
public class BusinessExceptionMapper extends AdmissionExceptionMapper<BusinessException> {

	@Override
	protected String createMessage(BusinessException exception) {
		return exception.getConstraintViolations().toString();
	}

	@Override
	protected Integer getResponseCode(BusinessException exception) {
		return exception.getResponseCode();
	}

}
