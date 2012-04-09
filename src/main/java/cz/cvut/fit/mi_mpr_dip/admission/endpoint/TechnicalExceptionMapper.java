package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import org.springframework.stereotype.Component;

import cz.cvut.fit.mi_mpr_dip.admission.exception.TechnicalException;

@Component
public class TechnicalExceptionMapper extends AdmissionExceptionMapper<TechnicalException> {

	@Override
	protected String createMessage(TechnicalException exception) {
		return exception.getMessage();
	}

	@Override
	protected Integer getResponseCode(TechnicalException exception) {
		return exception.getResponseCode();
	}

}
