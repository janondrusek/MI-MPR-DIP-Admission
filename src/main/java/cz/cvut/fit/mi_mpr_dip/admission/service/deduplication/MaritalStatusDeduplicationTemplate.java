package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.MaritalStatus;
import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.Person;

@Service
public class MaritalStatusDeduplicationTemplate implements PersonDeduplicationTemplate {

	@Override
	public void deduplicate(Person person) {
		List<MaritalStatus> maritalStatuses = MaritalStatus.findMaritalStatusesByNameEquals(
				person.getMaritalStatus().getName()).getResultList();
		if (CollectionUtils.isNotEmpty(maritalStatuses)) {
			person.setMaritalStatus(maritalStatuses.get(0));
		}
	}

}
