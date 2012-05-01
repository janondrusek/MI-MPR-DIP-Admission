package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template.person;

import java.util.Set;

import cz.cvut.fit.mi_mpr_dip.admission.domain.address.Address;
import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.Person;
import cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template.CollectionDeduplicationTemplate;

public abstract class AddressDeduplicationTemplate<T> extends CollectionDeduplicationTemplate<T, Address, Person>
		implements PersonDeduplicationTemplate {

	@Override
	protected Set<Address> getCollection(Person person) {
		return person.getAddresses();
	}
}