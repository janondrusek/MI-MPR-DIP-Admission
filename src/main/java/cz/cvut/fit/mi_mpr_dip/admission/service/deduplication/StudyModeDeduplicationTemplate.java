package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Programme;
import cz.cvut.fit.mi_mpr_dip.admission.domain.study.StudyMode;

@Service
public class StudyModeDeduplicationTemplate implements ProgrammeDeduplicationTemplate {

	@Override
	public void deduplicate(Programme programme) {
		List<StudyMode> studyModes = StudyMode.findStudyModesByNameEquals(programme.getStudyMode().getName())
				.getResultList();
		if (CollectionUtils.isNotEmpty(studyModes)) {
			programme.setStudyMode(studyModes.get(0));
		}
	}

}
