package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template.term;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Programme;
import cz.cvut.fit.mi_mpr_dip.admission.domain.study.StudyMode;

@Service
public class StudyModeTermDeduplicationTemplate extends CommonTermDeduplicationTemplate<StudyMode> {

	@Override
	protected Set<StudyMode> collect(Programme programme) {
		return wrap(programme.getStudyMode());
	}

	@Override
	protected List<StudyMode> findByNameEquals(StudyMode studyMode) {
		return StudyMode.findStudyModesByNameEquals(studyMode.getName()).getResultList();
	}

	@Override
	protected void deduplicateItem(StudyMode studyMode, Programme programme) {
		if (studyMode.equals(programme.getStudyMode())) {
			programme.setStudyMode(studyMode);
		}
	}
}
