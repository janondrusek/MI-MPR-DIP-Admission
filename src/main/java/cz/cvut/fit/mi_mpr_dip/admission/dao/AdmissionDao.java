package cz.cvut.fit.mi_mpr_dip.admission.dao;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;

public interface AdmissionDao {
	public Admission getAdmission(String code);
}
