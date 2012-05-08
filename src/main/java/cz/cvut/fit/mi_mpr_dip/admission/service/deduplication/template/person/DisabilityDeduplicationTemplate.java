package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template.person;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.DisabilityType;
import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.Person;

@Service
public class DisabilityDeduplicationTemplate implements PersonDeduplicationTemplate {

	@Override
	public void deduplicate(Person person) {
		Set<DisabilityType> disabilityTypes = person.getDisabilities();
		if (CollectionUtils.isNotEmpty(disabilityTypes)) {
			deduplicateDisabilities(disabilityTypes);
		}
	}

	private void deduplicateDisabilities(Set<DisabilityType> disabilityTypes) {
		Set<DisabilityType> replacements = new HashSet<DisabilityType>();
		for (Iterator<DisabilityType> iterator = disabilityTypes.iterator(); iterator.hasNext();) {
			DisabilityType disabilityType = iterator.next();
			List<DisabilityType> dbDisabilityTypes = DisabilityType.findDisabilityTypesByNameEquals(
					disabilityType.getName()).getResultList();
			if (CollectionUtils.isNotEmpty(dbDisabilityTypes)) {
				iterator.remove();
				replacements.add(dbDisabilityTypes.get(0));
			}
		}
		disabilityTypes.addAll(replacements);
	}
}
