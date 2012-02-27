package cz.cvut.fit.mi_mpr_dip.admission.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;

@Repository
public class AdmissionDaoImpl extends AbstractDao implements AdmissionDao {

	@Transactional(readOnly = true)
	@Override
	public Admission getAdmission(String code) {
		return uniqueResult(Admission.class, Admission.findAdmissionsByCodeEquals(code));
	}

}
