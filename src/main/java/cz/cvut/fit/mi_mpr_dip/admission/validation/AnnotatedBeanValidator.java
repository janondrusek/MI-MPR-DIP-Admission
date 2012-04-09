package cz.cvut.fit.mi_mpr_dip.admission.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.exception.BusinessException;

@Service
public class AnnotatedBeanValidator implements BeanValidator {

	private static final Logger log = LoggerFactory.getLogger(AnnotatedBeanValidator.class);

	@Autowired
	private Validator validator;

	@Override
	public void validate(Object annotated) {
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(annotated);
		log.debug("Violations found [{}]", constraintViolations);
		if (CollectionUtils.isNotEmpty(constraintViolations)) {
			throw new BusinessException(constraintViolations);
		}
	}
}
