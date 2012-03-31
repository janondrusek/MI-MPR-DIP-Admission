package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.person;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.address.Address;
import cz.cvut.fit.mi_mpr_dip.admission.domain.address.Country;
import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.Person;

@Service
public class CountryDeduplicationTemplate implements PersonDeduplicationTemplate {

	@Override
	public void deduplicate(Person person) {
		Set<Country> countries = collectCountries(person);
		deduplicateCountries(countries);
		deduplicateCountries(person, countries);
	}

	private Set<Country> collectCountries(Person person) {
		Set<Country> countries = new HashSet<Country>();
		countries.add(person.getCitizenship());
		countries.add(person.getCountryOfBirth());
		countries.addAll(collectCountries(person.getAddresses()));

		return countries;
	}

	private Set<Country> collectCountries(Set<Address> addresses) {
		Set<Country> countries = new HashSet<Country>();
		for (Address address : addresses) {
			countries.add(address.getCountry());
		}
		return countries;
	}

	private void deduplicateCountries(Set<Country> countries) {
		Set<Country> replacements = new HashSet<Country>();
		for (Iterator<Country> iterator = countries.iterator(); iterator.hasNext();) {
			Country country = iterator.next();
			List<Country> dbCountries = Country.findCountrysByNameEquals(country.getName()).getResultList();
			if (CollectionUtils.isNotEmpty(dbCountries)) {
				iterator.remove();
				replacements.add(dbCountries.get(0));
			}
		}
		countries.addAll(replacements);
	}

	private void deduplicateCountries(Person person, Set<Country> countries) {
		deduplicatePersonCountries(person, countries);
	}

	private void deduplicatePersonCountries(Person person, Set<Country> countries) {
		for (Country country : countries) {
			deduplicatePersonCountries(person, country);
		}
	}

	private void deduplicatePersonCountries(Person person, Country country) {
		deduplicatePersonDirectlyDescendantCountries(person, country);
		deduplicateAddressCountries(person, country);
	}

	private void deduplicatePersonDirectlyDescendantCountries(Person person, Country country) {
		if (country.equals(person.getCitizenship())) {
			person.setCitizenship(country);
		}

		if (country.equals(person.getCountryOfBirth())) {
			person.setCountryOfBirth(country);
		}
	}

	private void deduplicateAddressCountries(Person person, Country country) {
		for (Address address : person.getAddresses()) {
			if (country.equals(address.getCountry())) {
				address.setCountry(country);
			}
		}
	}
}