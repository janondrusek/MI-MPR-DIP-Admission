package cz.cvut.fit.mi_mpr_dip.admission.exception;

import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 4213606111816337546L;

	private Set<ConstraintViolation<Object>> constraintViolations;
	private Integer responseCode;

	public BusinessException(Set<ConstraintViolation<Object>> constraintViolations) {
		this(constraintViolations, HttpServletResponse.SC_BAD_REQUEST);
	}

	public BusinessException(Set<ConstraintViolation<Object>> constraintViolations, Integer responseCode) {
		super();
		this.constraintViolations = constraintViolations;
		this.responseCode = responseCode;
	}

	public Set<ConstraintViolation<Object>> getConstraintViolations() {
		return constraintViolations;
	}

	public Integer getResponseCode() {
		return responseCode;
	}

}
