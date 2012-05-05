package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Programme;
import cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.util.ProgrammeDeduplicationUtil;

@Service
public class ProgrammeDeduplicationService implements DeduplicationService<Programme> {

	@Autowired
	private ProgrammeDeduplicationUtil programmeDeduplicationUtil;

	@Override
	public void deduplicateAndStore(Programme programme) {
		programme = deduplicate(programme);

		programme.persist();
	}

	@Override
	public void deduplicateAndMerge(Programme programme) {
		programme = deduplicate(programme);

		programme.merge();
	}

	private Programme deduplicate(Programme programme) {
		return programmeDeduplicationUtil.deduplicate(programme);
	}
}
