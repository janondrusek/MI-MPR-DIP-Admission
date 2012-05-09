package cz.cvut.fit.mi_mpr_dip.admission.dao;

import java.util.Date;

import cz.cvut.fit.mi_mpr_dip.admission.domain.TermRegistration;

public interface TermRegistrationDao {

	public TermRegistration getTermRegistration(String admissionCode, Date dateOfTerm, String room);

	public void delete(TermRegistration termRegistration);
}
