package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template.AdmissionDeduplicationTemplate;
import cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template.DeduplicationTemplate;
import cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template.person.PersonDeduplicationTemplate;

@Service
public class AdmissionDeduplicationService implements DeduplicationService<Admission> {

	@Autowired
	private Set<AdmissionDeduplicationTemplate> admissionDeduplicationTemplates;

	@Autowired
	private Set<PersonDeduplicationTemplate> personDeduplicationTemplates;

	@Transactional
	@Override
	public void deduplicateAndStore(Admission admission) {
		deduplicate(admission);

		admission.persist();
	}

	@Override
	public void deduplicateAndMerge(Admission admission) {
		deduplicate(admission);

		admission.merge();
	}

	private void deduplicate(Admission admission) {
		deduplicate(admission, admissionDeduplicationTemplates);
		deduplicate(admission.getPerson(), personDeduplicationTemplates);
	}

	private <T> void deduplicate(T deduplicant, Set<? extends DeduplicationTemplate<T>> templates) {
		for (DeduplicationTemplate<T> template : templates) {
			template.deduplicate(deduplicant);
		}
	}

}