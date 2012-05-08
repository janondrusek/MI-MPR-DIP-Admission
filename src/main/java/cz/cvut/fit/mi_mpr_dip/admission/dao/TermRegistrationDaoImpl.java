package cz.cvut.fit.mi_mpr_dip.admission.dao;

import java.util.Date;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Term;
import cz.cvut.fit.mi_mpr_dip.admission.domain.TermRegistration;

@Repository
public class TermRegistrationDaoImpl extends AbstractDao<TermRegistration> implements TermRegistrationDao {

	@Transactional(readOnly = true)
	@Override
	public TermRegistration getTermRegistration(String admissionCode, Date dateOfTerm, String room) {
		return getTermRegistrationQuietly(admissionCode, dateOfTerm, room);
	}

	private TermRegistration getTermRegistrationQuietly(String admissionCode, Date dateOfTerm, String room) {
		TermRegistration termRegistration;
		try {
			termRegistration = getTermRegistrationQuietly(getAdmission(admissionCode), getTerm(dateOfTerm, room));
		} catch (Exception e) {
			termRegistration = processException(e);
		}
		return termRegistration;
	}

	private Admission getAdmission(String admissionCode) {
		return Admission.findAdmissionsByCodeEquals(admissionCode).getSingleResult();
	}

	private Term getTerm(Date dateOfTerm, String room) {
		return Term.findTermsByDateOfTermEqualsAndRoomEquals(dateOfTerm, room).getSingleResult();
	}

	private TermRegistration getTermRegistrationQuietly(Admission admission, Term term) {
		return uniqueResult(TermRegistration.findTermRegistrationsByAdmissionAndTerm(admission, term));
	}

	@Override
	protected TermRegistration createEmptyResult() {
		TermRegistration termRegistration = new TermRegistration();
		termRegistration.setAdmission(new Admission());
		termRegistration.setTerm(new Term());

		return termRegistration;
	}
}
