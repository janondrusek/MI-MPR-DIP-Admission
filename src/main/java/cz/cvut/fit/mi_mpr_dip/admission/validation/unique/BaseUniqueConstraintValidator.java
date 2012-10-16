package cz.cvut.fit.mi_mpr_dip.admission.validation.unique;

import java.lang.annotation.ElementType;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.hibernate.validator.internal.engine.PathImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.cvut.fit.mi_mpr_dip.admission.exception.BusinessException;

public abstract class BaseUniqueConstraintValidator<T> {

	private static final Logger log = LoggerFactory.getLogger(BaseUniqueConstraintValidator.class);

	public void validate(T o) {
		if (isDuplicate(o)) {
			Set<ConstraintViolation<Object>> constraintViolations = getConstraintViolations(o);
			log.debug("Will return constraint violations [{}]", constraintViolations);
			throw new BusinessException(constraintViolations);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Set<ConstraintViolation<Object>> getConstraintViolations(T o) {
		Set<ConstraintViolation<Object>> constraintViolations = new HashSet<>();
		ConstraintViolationImpl<Object> constraintViolation = new ConstraintViolationImpl<Object>("Duplicate [{}]",
				"Duplicate [" + getDuplicateValue(o) + "]", (Class) o.getClass(), o, o, getDuplicateValue(o),
				PathImpl.createPathFromString(getPath()), null, ElementType.FIELD);
		constraintViolations.add(constraintViolation);
		return constraintViolations;
	}

	abstract protected String getPath();

	abstract protected String getDuplicateValue(T o);

	abstract protected boolean isDuplicate(T o);
}
