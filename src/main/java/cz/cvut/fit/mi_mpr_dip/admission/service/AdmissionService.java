package cz.cvut.fit.mi_mpr_dip.admission.service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;

public interface AdmissionService {

	public void deduplicateAndStore(Admission admission);
}
