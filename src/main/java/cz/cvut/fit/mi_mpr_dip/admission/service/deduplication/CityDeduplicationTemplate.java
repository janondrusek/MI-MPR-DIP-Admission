package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.address.City;
import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.Person;

@Service
public class CityDeduplicationTemplate implements PersonDeduplicationTemplate {

	@Override
	public void deduplicate(Person person) {
		List<City> cities = City.findCitysByNameEquals(person.getCityOfBirth().getName()).getResultList();
		if (CollectionUtils.isNotEmpty(cities)) {
			person.setCityOfBirth(cities.get(0));
		}
	}

}
