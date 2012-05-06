package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template.person;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.MaritalStatus;
import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.Person;
import cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template.SimpleDeduplicationTemplate;

@Service
public class MaritalStatusDeduplicationTemplate extends SimpleDeduplicationTemplate<Person, MaritalStatus> implements
		PersonDeduplicationTemplate {

	@Override
	protected TypedQuery<MaritalStatus> findDegreesByNameEquals(Person person) {
		return MaritalStatus.findMaritalStatusesByNameEquals(person.getMaritalStatus().getName());
	}

	@Override
	protected void setFound(Person person, MaritalStatus maritalStatus) {
		person.setMaritalStatus(maritalStatus);
	}

}
