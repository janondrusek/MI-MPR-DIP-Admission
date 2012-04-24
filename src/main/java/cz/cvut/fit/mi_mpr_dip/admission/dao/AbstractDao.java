package cz.cvut.fit.mi_mpr_dip.admission.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

@Repository
public abstract class AbstractDao<T> {

	private static final Logger log = LoggerFactory.getLogger(AbstractDao.class);

	protected T uniqueResult(TypedQuery<T> query) {
		T result;
		try {
			result = query.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			result = createEmptyResult();
			log.debug("Returning empty result for [{}]", result.getClass().getSimpleName());

		}
		return result;
	}

	protected T processException(Exception e) {
		T o = createEmptyResult();
		log.debug("Unable to get result for [{}]", o.getClass());

		return o;
	}

	abstract protected T createEmptyResult();

	protected List<T> processListException(Exception e) {
		log.debug("Unable to find entiries [{}]", String.valueOf(e));
		return createEmptyList();
	}

	protected List<T> createEmptyList() {
		return new ArrayList<T>();
	}

}
