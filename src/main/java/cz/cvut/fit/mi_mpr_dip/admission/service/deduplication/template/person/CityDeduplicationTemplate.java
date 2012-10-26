package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template.person;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.google.common.collect.Sets;

import cz.cvut.fit.mi_mpr_dip.admission.domain.address.Address;
import cz.cvut.fit.mi_mpr_dip.admission.domain.address.City;
import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.Person;

@Service
public class CityDeduplicationTemplate extends CommonPersonDeduplicationTemplate<City> {

	@Override
	protected Set<City> collectDirectlyDescendant(Person person) {
		Set<City> cities = Sets.newHashSet();
		cities.add(person.getCityOfBirth());
		return cities;
	}

	@Override
	protected Set<City> collect(Address address) {
		return wrap(address.getCity());
	}

	@Override
	protected List<City> findByNameEquals(City city) {
		return City.findCitysByNameEquals(city.getName()).getResultList();
	}

	@Override
	protected void deduplicatePersonDirectlyDescendant(Person person, City city) {
		if (person.getCityOfBirth().equals(city)) {
			person.setCityOfBirth(city);
		}
	}

	@Override
	protected void deduplicateItem(City city, Address address) {
		if (city.equals(address.getCity())) {
			address.setCity(city);
		}
	}
}