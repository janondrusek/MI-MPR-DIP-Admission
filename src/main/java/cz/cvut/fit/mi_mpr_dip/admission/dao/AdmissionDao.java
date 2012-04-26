package cz.cvut.fit.mi_mpr_dip.admission.dao;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.Person;

public interface AdmissionDao {
	public Admission getAdmission(String code);

	public Admission getAdmission(Person person);
}
