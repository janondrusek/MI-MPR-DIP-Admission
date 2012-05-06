package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template.programme;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Programme;
import cz.cvut.fit.mi_mpr_dip.admission.domain.study.StudyMode;
import cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template.SimpleDeduplicationTemplate;

@Service
public class StudyModeDeduplicationTemplate extends SimpleDeduplicationTemplate<Programme, StudyMode> implements
		ProgrammeDeduplicationTemplate {

	@Override
	protected TypedQuery<StudyMode> findByNameEquals(Programme programme) {
		return StudyMode.findStudyModesByNameEquals(programme.getStudyMode().getName());
	}

	@Override
	protected void setFound(Programme programme, StudyMode studyMode) {
		programme.setStudyMode(studyMode);
	}
}
