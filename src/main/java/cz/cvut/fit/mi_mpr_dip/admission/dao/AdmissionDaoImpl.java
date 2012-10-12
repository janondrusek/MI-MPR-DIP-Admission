package cz.cvut.fit.mi_mpr_dip.admission.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.Person;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentity;

@Repository
public class AdmissionDaoImpl extends Dao<Admission> implements AdmissionDao {

	@Transactional(readOnly = true)
	@Override
	public Admission getAdmission(String code) {
		return getAdmissionQuietly(code);
	}

	private Admission getAdmissionQuietly(String code) {
		Admission admission;
		try {
			admission = uniqueResult(Admission.findAdmissionsByCodeEquals(code));
		} catch (Exception e) {
			admission = processException(e);
		}
		return admission;
	}

	@Transactional(readOnly = true)
	@Override
	public Admission getAdmission(Person person) {
		return getAdmissionQuietly(person);
	}

	private Admission getAdmissionQuietly(Person person) {
		Admission admission;
		try {
			admission = Admission.findAdmissionsByPerson(person).getSingleResult();
		} catch (Exception e) {
			admission = processException(e);
		}
		return admission;
	}

	@Transactional(readOnly = true)
	@Override
	public Admission getAdmission(UserIdentity userIdentity) {
		return getAdmissionQuietly(userIdentity);
	}

	private Admission getAdmissionQuietly(UserIdentity userIdentity) {
		Admission admission;
		try {
			admission = Admission.findAdmissionsByUserIdentity(userIdentity).getSingleResult();
		} catch (Exception e) {
			admission = processException(e);
		}
		return admission;
	}

	@Override
	protected Admission createEmptyResult() {
		return new Admission();
	}

}