package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template.person;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.Gender;
import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.Person;
import cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template.SimpleDeduplicationTemplate;

@Service
public class GenderDeduplicationTemplate extends SimpleDeduplicationTemplate<Person, Gender> implements
		PersonDeduplicationTemplate {

	@Override
	protected TypedQuery<Gender> findDegreesByNameEquals(Person person) {
		return Gender.findGendersByNameEquals(person.getGender().getName());
	}

	@Override
	protected void setFound(Person person, Gender gender) {
		person.setGender(gender);
	}
}
