package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.person;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import cz.cvut.fit.mi_mpr_dip.admission.domain.address.Address;
import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.Person;

public abstract class AddressDeduplicationTemplate<T> implements PersonDeduplicationTemplate {

	@Override
	public void deduplicate(Person person) {
		Set<Address> addresses = person.getAddresses();
		if (CollectionUtils.isNotEmpty(addresses)) {
			deduplicateAddresses(addresses);
		}
	}

	private void deduplicateAddresses(Set<Address> addresses) {
		Set<T> collected = collect(addresses);
		deduplicate(collected);
		deduplicateAddresses(addresses, collected);
	}

	protected Set<T> collect(Set<Address> addresses) {
		Set<T> collected = new HashSet<T>();
		for (Address address : addresses) {
			collected.addAll(collect(address));
		}
		return collected;
	}

	abstract protected Set<T> collect(Address address);

	protected Set<T> collect(T item) {
		Set<T> wrapper = new HashSet<T>();
		if (item != null) {
			wrapper.add(item);
		}
		return wrapper;
	}

	protected void deduplicate(Set<T> collected) {
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

	private void deduplicateAddresses(Set<Address> addresses, Set<T> collected) {
		for (Address address : addresses) {
			for (T item : collected) {
				deduplicateAddress(item, address);
			}
		}
	}

	abstract protected void deduplicateAddress(T item, Address address);
}