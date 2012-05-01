package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.util;

import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Programme;
import cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template.programme.ProgrammeDeduplicationTemplate;

@Service
public class ProgrammeDeduplicationUtil {

	@Autowired
	private Set<ProgrammeDeduplicationTemplate> deduplicationTemplates;

	public Programme deduplicate(Programme programme) {
		if (programme != null) {
			deduplicateProgramme(programme);
			storeProgrammeDescendants(programme);
			List<Programme> programs = Programme.findProgrammesByNameEqualsAndStudyModeAndDegreeAndLanguage(
					programme.getName(), programme.getStudyMode(), programme.getDegree(), programme.getLanguage())
					.getResultList();
			if (CollectionUtils.isNotEmpty(programs)) {
				if (programme.equals(programs.get(0))) {
					programme = programs.get(0);
				}
			}
		}
		return programme;
	}

	private void deduplicateProgramme(Programme programme) {
		for (ProgrammeDeduplicationTemplate deduplicationTemplate : deduplicationTemplates) {
			deduplicationTemplate.deduplicate(programme);
		}
	}

	private void storeProgrammeDescendants(Programme programme) {
		programme.getDegree().persist();
		programme.getLanguage().persist();
		programme.getStudyMode().persist();
	}
}
