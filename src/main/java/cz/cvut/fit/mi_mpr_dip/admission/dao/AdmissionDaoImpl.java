package cz.cvut.fit.mi_mpr_dip.admission.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;

@Repository
public class AdmissionDaoImpl implements AdmissionDao {

	@Transactional(readOnly = true)
	@Override
	public Admission getAdmission(String code) {
		Admission admission;
		try {
			admission = Admission.findAdmissionsByCodeEquals(code).getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			admission = createAdmission();
		}
		return admission;
	}

	private Admission createAdmission() {
		return new Admission();
	}
}
