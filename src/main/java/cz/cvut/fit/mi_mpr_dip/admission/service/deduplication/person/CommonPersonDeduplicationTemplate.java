package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.person;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import cz.cvut.fit.mi_mpr_dip.admission.domain.address.Address;
import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.Person;

public abstract class CommonPersonDeduplicationTemplate<T> implements PersonDeduplicationTemplate {

	@Override
	public void deduplicate(Person person) {
		Set<T> collected = collect(person);
		deduplicate(collected);
		deduplicatePerson(person, collected);
	}

	private Set<T> collect(Person person) {
		Set<T> collected = new HashSet<T>();
		collected.addAll(collectDirectlyDescendant(person));
		collected.addAll(collect(person.getAddresses()));
		return collected;
	}

	abstract protected Set<T> collectDirectlyDescendant(Person person);

	private Set<T> collect(Set<Address> addresses) {
		Set<T> collected = new HashSet<T>();
		for (Address address : addresses) {
			collected.add(collect(address));
		}
		return collected;
	}

	abstract protected T collect(Address address);

	private void deduplicate(Set<T> collected) {
		Set<T> replacements = new HashSet<T>();
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

	private void deduplicatePerson(Person person, Set<T> collected) {
		for (T item : collected) {
			deduplicatePerson(person, item);
		}
	}

	private void deduplicatePerson(Person person, T item) {
		deduplicatePersonDirectlyDescendant(person, item);
		deduplicateAddresses(person, item);
	}

	abstract protected void deduplicatePersonDirectlyDescendant(Person person, T item);

	private void deduplicateAddresses(Person person, T item) {
		for (Address address : person.getAddresses()) {
			deduplicateAddress(item, address);
		}
	}

	abstract protected void deduplicateAddress(T item, Address address);
}
