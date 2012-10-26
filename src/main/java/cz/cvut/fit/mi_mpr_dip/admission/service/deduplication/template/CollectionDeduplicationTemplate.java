package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template;

import java.util.Iterator;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import com.google.common.collect.Sets;

public abstract class CollectionDeduplicationTemplate<C, D> implements DeduplicationTemplate<D> {

	@Override
	public void deduplicate(D deduplicant) {
		Set<C> collection = getCollection(deduplicant);
		if (CollectionUtils.isNotEmpty(collection)) {
			deduplicateCollection(collection);
		}
	}

	abstract protected Set<C> getCollection(D deduplicant);

	protected void deduplicateCollection(Set<C> collection) {
		deduplicate(collection);
	}

	protected void deduplicate(Set<C> collection) {
		Set<C> replacements = Sets.newHashSet();
		for (Iterator<C> iterator = collection.iterator(); iterator.hasNext();) {
			C item = iterator.next();
			C duplicate = findDuplicate(item);
			if (item != duplicate && duplicate.equals(item)) {
				iterator.remove();
				replacements.add(duplicate);
			}
		}
		collection.addAll(replacements);
	}

	abstract protected C findDuplicate(C item);
}
