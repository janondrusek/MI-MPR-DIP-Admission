package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.person;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.address.Address;
import cz.cvut.fit.mi_mpr_dip.admission.domain.address.AddressType;

@Service
public class AddressTypeDeduplicationTemplate extends AddressDeduplicationTemplate<AddressType> {

	@Override
	protected Set<AddressType> collect(Address address) {
		return collect(address.getAddressType());
	}

	@Override
	protected List<AddressType> findByNameEquals(AddressType addressType) {
		return AddressType.findAddressTypesByNameEquals(addressType.getName()).getResultList();
	}

	@Override
	protected void deduplicateAddress(AddressType addressType, Address address) {
		if (address.getAddressType().equals(addressType)) {
			address.setAddressType(addressType);
		}
	}
}
