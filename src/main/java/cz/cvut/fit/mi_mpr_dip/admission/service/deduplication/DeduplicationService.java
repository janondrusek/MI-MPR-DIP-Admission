package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;

public interface DeduplicationService {

	public void deduplicateAndStore(Admission admission);
}
