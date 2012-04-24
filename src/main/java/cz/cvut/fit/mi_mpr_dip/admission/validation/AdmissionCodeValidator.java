package cz.cvut.fit.mi_mpr_dip.admission.validation;

import java.lang.annotation.ElementType;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.engine.ConstraintViolationImpl;
import org.hibernate.validator.engine.PathImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.stereotype.Component;

import cz.cvut.fit.mi_mpr_dip.admission.dao.AdmissionDao;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.exception.BusinessException;

@RooJavaBean
@Component
public class AdmissionCodeValidator {

	private static final Logger log = LoggerFactory.getLogger(AdmissionCodeValidator.class);

	@Autowired
	private AdmissionDao admissionDao;

	public void validate(Admission admission) {
		Admission duplicate = admissionDao.getAdmission(admission.getCode());
		if (StringUtils.equals(admission.getCode(), duplicate.getCode())) {
			Set<ConstraintViolation<Object>> constraintViolations = getConstraintViolations(admission);
			log.debug("Will return constraint violations [{}]", constraintViolations);
			throw new BusinessException(constraintViolations);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Set<ConstraintViolation<Object>> getConstraintViolations(Admission admission) {
		Set<ConstraintViolation<Object>> constraintViolations = new HashSet<ConstraintViolation<Object>>();
		ConstraintViolationImpl<Object> constraintViolation = new ConstraintViolationImpl<Object>(
				"Duplicate code [{}]", "Duplicate code [" + admission.getCode() + "]", (Class) Admission.class,
				admission, admission, admission.getCode(), PathImpl.createPathFromString("code"), null,
				ElementType.FIELD);
		constraintViolations.add(constraintViolation);
		return constraintViolations;
	}
}
