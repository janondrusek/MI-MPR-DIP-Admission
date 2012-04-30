package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template.person;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.Gender;
import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.Person;

@Service
public class GenderDeduplicationTemplate implements PersonDeduplicationTemplate {

	@Override
	public void deduplicate(Person person) {
		List<Gender> genders = Gender.findGendersByNameEquals(person.getGender().getName()).getResultList();
		if (CollectionUtils.isNotEmpty(genders)) {
			person.setGender(genders.get(0));
		}
	}

}
