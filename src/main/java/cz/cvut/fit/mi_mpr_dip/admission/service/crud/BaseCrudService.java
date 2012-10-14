package cz.cvut.fit.mi_mpr_dip.admission.service.crud;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import cz.cvut.fit.mi_mpr_dip.admission.dao.persistence.UniqueConstraint;
import cz.cvut.fit.mi_mpr_dip.admission.exception.util.BusinessExceptionUtil;
import cz.cvut.fit.mi_mpr_dip.admission.util.WebKeys;

public abstract class BaseCrudService<T> implements CrudService<T> {

	@Autowired
	private BusinessExceptionUtil businessExceptionUtil;

	protected void validateNotFound(UniqueConstraint<T> uniqueConstraint) {
		if (uniqueConstraint.isNotFound()) {
			throwNotFoundBusinessException();
		}
	}

	protected void throwNotFoundBusinessException() {
		businessExceptionUtil.throwException(HttpServletResponse.SC_NOT_FOUND, WebKeys.NOT_FOUND);
	}
}
