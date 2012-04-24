package cz.cvut.fit.mi_mpr_dip.admission.dao;

import cz.cvut.fit.mi_mpr_dip.admission.domain.AdmissionState;

public interface AdmissionStateDao {
	public AdmissionState getAdmissionState(String code);
}
