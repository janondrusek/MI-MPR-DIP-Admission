package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.address.Country;
import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.Person;

@Service
public class CountryDeduplicationTemplate implements PersonDeduplicationTemplate {

	@Override
	public void deduplicate(Person person) {
		if (person.getCountryOfBirth().equals(person.getCitizenship())) {
			deduplicateEqualCountries(person);
		} else {
			deduplicateNonEqualCountries(person);
		}
	}

	private void deduplicateEqualCountries(Person person) {
		List<Country> countries = Country.findCountrysByNameEquals(person.getCountryOfBirth().getName())
				.getResultList();
		if (CollectionUtils.isNotEmpty(countries)) {
			setCountries(person, countries.get(0));
		} else {
			Country country = person.getCitizenship();
			country.persist();
			setCountries(person, country);
		}
	}

	private void deduplicateNonEqualCountries(Person person) {
		Country citizenship = person.getCitizenship();
		Country countryOfBirth = person.getCountryOfBirth();
		List<Country> citizenships = Country.findCountrysByNameEquals(person.getCitizenship().getName())
				.getResultList();
		if (CollectionUtils.isNotEmpty(citizenships)) {
			citizenship = citizenships.get(0);
		}

		List<Country> countriesOfBirth = Country.findCountrysByNameEquals(person.getCountryOfBirth().getName())
				.getResultList();
		if (CollectionUtils.isNotEmpty(countriesOfBirth)) {
			countryOfBirth = countriesOfBirth.get(0);
		}

		setCountries(person, citizenship, countryOfBirth);
	}

	private void setCountries(Person person, Country country) {
		setCountries(person, country, country);
	}

	private void setCountries(Person person, Country citizenship, Country countryOfBirth) {
		person.setCitizenship(citizenship);
		person.setCountryOfBirth(countryOfBirth);
	}

}
