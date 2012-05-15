package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Programme;
import cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.util.ProgrammeDeduplicationUtil;

@Service
public class ProgrammeDeduplicationService implements DeduplicationService<Programme> {

	@Autowired
	private ProgrammeDeduplicationUtil programmeDeduplicationUtil;

	@Transactional
	@Override
	public void deduplicateAndStore(Programme programme) {
		programme = deduplicateProgramme(programme);

		programme.persist();
	}

	@Transactional
	@Override
	public void deduplicateAndMerge(Programme programme) {
		programme = deduplicateProgramme(programme);

		programme.merge();
	}

	@Override
	public void deduplicate(Programme programme) {
		programme = deduplicateProgramme(programme);
	}

	private Programme deduplicateProgramme(Programme programme) {
		return programmeDeduplicationUtil.deduplicate(programme);
	}
}
