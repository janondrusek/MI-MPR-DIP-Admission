package cz.cvut.fit.mi_mpr_dip.admission.service;

import java.util.Date;
import java.util.Set;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Term;
import cz.cvut.fit.mi_mpr_dip.admission.domain.TermRegistration;

public interface TermService {

	public void addLinks(Term term);

	public void addLinks(Set<TermRegistration> registrations);

	public Term getTerm(String dateOfTerm, String room);

	public Term getTerm(Date dateOfTerm, String room);

	public Date getDate(String dateOfTerm);

}
