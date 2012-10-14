package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template.person;

import java.util.HashSet;
import java.util.Set;

import cz.cvut.fit.mi_mpr_dip.admission.domain.address.Address;
import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.Person;

public abstract class CommonPersonDeduplicationTemplate<T> extends AddressDeduplicationTemplate<T> {

	@Override
	public void deduplicate(Person person) {
		Set<T> collected = collect(person);
		deduplicate(collected);
		deduplicatePerson(person, collected);
	}

	private Set<T> collect(Person person) {
		Set<T> collected = new HashSet<>();
		collected.addAll(collectDirectlyDescendant(person));
		collected.addAll(collect(person.getAddresses()));
		return collected;
	}

	abstract protected Set<T> collectDirectlyDescendant(Person person);

	private void deduplicatePerson(Person person, Set<T> collected) {
		for (T item : collected) {
			deduplicatePerson(person, item);
		}
	}

	private void deduplicatePerson(Person person, T item) {
		deduplicatePersonDirectlyDescendant(person, item);
		deduplicateItems(person, item);
	}

	abstract protected void deduplicatePersonDirectlyDescendant(Person person, T item);

	private void deduplicateItems(Person person, T item) {
		for (Address address : person.getAddresses()) {
			deduplicateItem(item, address);
		}
	}
}
