package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.person;

import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.DisabilityType;
import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.Person;

public class DisabilityDeduplicationTemplate implements PersonDeduplicationTemplate {

	@Override
	public void deduplicate(Person person) {
		Set<DisabilityType> disabilityTypes = person.getDisabilities();
		if (CollectionUtils.isNotEmpty(disabilityTypes)) {
			deduplicateDisabilities(disabilityTypes);
		}
	}

	private void deduplicateDisabilities(Set<DisabilityType> disabilityTypes) {
		for (DisabilityType disabilityType : disabilityTypes) {
			List<DisabilityType> dbDisabilityTypes = DisabilityType.findDisabilityTypesByNameEquals(
					disabilityType.getName()).getResultList();
			if (CollectionUtils.isNotEmpty(dbDisabilityTypes)) {
				disabilityType.setDisabilityTypeId(dbDisabilityTypes.get(0).getDisabilityTypeId());
			}
		}
	}
}
