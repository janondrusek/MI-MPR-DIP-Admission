package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import com.google.common.collect.Sets;

public abstract class NestedCollectionDeduplicationTemplate<T, C, D> implements DeduplicationTemplate<D> {

	@Override
	public void deduplicate(D deduplicant) {
		Set<C> collection = getCollection(deduplicant);
		if (CollectionUtils.isNotEmpty(collection)) {
			deduplicateCollection(collection);
		}
	}

	abstract protected Set<C> getCollection(D deduplicant);

	protected void deduplicateCollection(Set<C> collection) {
		Set<T> collected = collect(collection);
		deduplicate(collected);
		deduplicateCollection(collection, collected);
	}

	protected Set<T> collect(Set<C> collection) {
		Set<T> collected = getNewHashSet();
		for (C item : collection) {
			collected.addAll(collect(item));
		}
		return collected;
	}

	abstract protected Set<T> collect(C item);

	protected Set<T> wrap(T item) {
		Set<T> wrapper = getNewHashSet();
		if (item != null) {
			wrapper.add(item);
		}
		return wrapper;
	}

	protected void deduplicate(Set<T> collected) {
		Set<T> replacements = getNewHashSet();
		for (Iterator<T> iterator = collected.iterator(); iterator.hasNext();) {
			T item = iterator.next();
			List<T> items = findByNameEquals(item);
			if (CollectionUtils.isNotEmpty(items)) {
				iterator.remove();
				replacements.add(items.get(0));
			}
		}
		collected.addAll(replacements);
	}

	abstract protected List<T> findByNameEquals(T item);

	private void deduplicateCollection(Set<C> collection, Set<T> collected) {
		for (C cItem : collection) {
			for (T item : collected) {
				deduplicateItem(item, cItem);
			}
		}
	}

	abstract protected void deduplicateItem(T item, C cItem);

	private Set<T> getNewHashSet() {
		return Sets.newHashSet();
	}
}
