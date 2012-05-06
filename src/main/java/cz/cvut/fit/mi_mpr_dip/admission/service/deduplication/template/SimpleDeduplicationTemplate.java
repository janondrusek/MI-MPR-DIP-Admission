package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template;

import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;

public abstract class SimpleDeduplicationTemplate<T, E> implements DeduplicationTemplate<T> {

	@Override
	public void deduplicate(T deduplicant) {
		List<E> collection = findDegreesByNameEquals(findDegreesByNameEquals(deduplicant));
		if (CollectionUtils.isNotEmpty(collection)) {
			setFound(deduplicant, collection.get(0));
		}
	}

	protected List<E> findDegreesByNameEquals(TypedQuery<E> query) {
		return query.getResultList();
	}

	protected abstract TypedQuery<E> findDegreesByNameEquals(T deduplicant);

	protected abstract void setFound(T deduplicant, E found);
}
