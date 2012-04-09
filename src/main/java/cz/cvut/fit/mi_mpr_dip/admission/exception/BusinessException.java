package cz.cvut.fit.mi_mpr_dip.admission.exception;

import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;

public class BusinessException extends AdmissionException {

	private static final long serialVersionUID = 4213606111816337546L;

	private Set<ConstraintViolation<Object>> constraintViolations;

	public BusinessException(Set<ConstraintViolation<Object>> constraintViolations) {
		this(constraintViolations, HttpServletResponse.SC_BAD_REQUEST);
	}

	public BusinessException(Set<ConstraintViolation<Object>> constraintViolations, Integer responseCode) {
		super(responseCode);
		this.constraintViolations = constraintViolations;
	}

	public Set<ConstraintViolation<Object>> getConstraintViolations() {
		return constraintViolations;
	}
}
