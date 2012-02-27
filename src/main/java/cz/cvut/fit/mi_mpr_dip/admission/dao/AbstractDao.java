package cz.cvut.fit.mi_mpr_dip.admission.dao;

import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

@Repository
public abstract class AbstractDao {

	private static final Logger log = LoggerFactory.getLogger(AbstractDao.class);

	protected <T> T uniqueResult(Class<T> clazz, TypedQuery<T> query) {
		T result;
		try {
			result = query.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			try {
				result = createEmptyResult(clazz);
			} catch (Exception ex) {
				log.debug("Unable to instantiate", ex);
				result = null;
			}
		}
		return result;
	}

	private <T> T createEmptyResult(Class<T> clazz) throws InstantiationException, IllegalAccessException {
		return clazz.newInstance();
	}
}
