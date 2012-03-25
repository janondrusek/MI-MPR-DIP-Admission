package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication;

import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Programme;

@Service
public class DefaultProgrammeDeduplicationTemplate implements AdmissionDeduplicationTemplate {

	@Autowired
	private Set<ProgrammeDeduplicationTemplate> deduplicationTemplates;

	@Override
	public void deduplicate(Admission admission) {
		Programme programme = admission.getProgramme();
		admission.setProgramme(deduplicate(programme));
	}

	private Programme deduplicate(Programme programme) {
		if (programme != null) {
			List<Programme> programs = Programme.findProgrammesByNameEquals(programme.getName()).getResultList();
			if (CollectionUtils.isNotEmpty(programs)) {
				if (programme.equals(programs.get(0))) {
					programme = programs.get(0);
				}
			} else {
				for (ProgrammeDeduplicationTemplate deduplicationTemplate : deduplicationTemplates) {
					deduplicationTemplate.deduplicate(programme);
				}
			}
		}
		return programme;
	}

}
