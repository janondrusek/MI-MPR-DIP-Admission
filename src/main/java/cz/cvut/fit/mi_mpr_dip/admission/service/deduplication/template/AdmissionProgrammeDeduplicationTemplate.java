package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Programme;
import cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.util.ProgrammeDeduplicationUtil;

@Service
public class AdmissionProgrammeDeduplicationTemplate implements AdmissionDeduplicationTemplate {

	@Autowired
	private ProgrammeDeduplicationUtil programmeDeduplicationUtil;

	@Override
	public void deduplicate(Admission admission) {
		Programme programme = admission.getProgramme();
		admission.setProgramme(deduplicate(programme));
	}

	private Programme deduplicate(Programme programme) {
		return programmeDeduplicationUtil.deduplicate(programme);
	}
}
