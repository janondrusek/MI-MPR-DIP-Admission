package cz.cvut.fit.mi_mpr_dip.admission.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;

public class AdmissionValidator implements Validator {

	private static final Logger log = LoggerFactory.getLogger(AdmissionValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return Admission.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		log.debug("Validating [{}]", target);
	}

}
