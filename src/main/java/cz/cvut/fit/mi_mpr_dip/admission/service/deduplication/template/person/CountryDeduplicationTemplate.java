package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template.person;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.address.Address;
import cz.cvut.fit.mi_mpr_dip.admission.domain.address.Country;
import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.Person;

@Service
public class CountryDeduplicationTemplate extends CommonPersonDeduplicationTemplate<Country> {

	@Override
	protected Set<Country> collectDirectlyDescendant(Person person) {
		Set<Country> countries = new HashSet<Country>();
		countries.add(person.getCitizenship());
		countries.add(person.getCountryOfBirth());
		return countries;
	}

	@Override
	protected Set<Country> collect(Address address) {
		return wrap(address.getCountry());
	}

	@Override
	protected List<Country> findByNameEquals(Country country) {
		return Country.findCountrysByNameEquals(country.getName()).getResultList();
	}

	@Override
	protected void deduplicatePersonDirectlyDescendant(Person person, Country country) {
		if (country.equals(person.getCitizenship())) {
			person.setCitizenship(country);
		}

		if (country.equals(person.getCountryOfBirth())) {
			person.setCountryOfBirth(country);
		}
	}

	@Override
	protected void deduplicateItem(Country country, Address address) {
		if (country.equals(address.getCountry())) {
			address.setCountry(country);
		}
	}
}