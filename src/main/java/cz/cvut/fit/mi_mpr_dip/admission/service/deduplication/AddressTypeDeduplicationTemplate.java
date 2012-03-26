package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import cz.cvut.fit.mi_mpr_dip.admission.domain.address.Address;
import cz.cvut.fit.mi_mpr_dip.admission.domain.address.AddressType;
import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.Person;

public class AddressTypeDeduplicationTemplate implements PersonDeduplicationTemplate {

	@Override
	public void deduplicate(Person person) {
		Set<Address> addresses = person.getAddresses();
		if (CollectionUtils.isNotEmpty(addresses)) {
			deduplicateAddresses(addresses);
		}
	}

	private void deduplicateAddresses(Set<Address> addresses) {
		Set<AddressType> addressTypes = collectAddressTypes(addresses);
		deduplicateAddressTypes(addressTypes);
		deduplicateAddresses(addresses, addressTypes);
	}

	private Set<AddressType> collectAddressTypes(Set<Address> addresses) {
		Set<AddressType> addressTypes = new HashSet<AddressType>();
		for (Address address : addresses) {
			addressTypes.add(address.getAddressType());
		}
		return addressTypes;
	}

	private void deduplicateAddressTypes(Set<AddressType> addressTypes) {
		Set<AddressType> replacements = new HashSet<AddressType>();
		for (Iterator<AddressType> iterator = addressTypes.iterator(); iterator.hasNext();) {
			AddressType addressType = iterator.next();
			List<AddressType> dbAddressTypes = AddressType.findAddressTypesByNameEquals(addressType.getName())
					.getResultList();
			if (CollectionUtils.isNotEmpty(dbAddressTypes)) {
				iterator.remove();
				replacements.add(dbAddressTypes.get(0));
			}
		}
		addressTypes.addAll(replacements);
	}

	private void deduplicateAddresses(Set<Address> addresses, Set<AddressType> addressTypes) {
		for (AddressType addressType : addressTypes) {
			for (Address address : addresses) {
				if (address.getAddressType().equals(addressType)) {
					address.setAddressType(addressType);
				}
			}
		}
	}
}
