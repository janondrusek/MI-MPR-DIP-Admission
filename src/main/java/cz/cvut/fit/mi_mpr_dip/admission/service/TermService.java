package cz.cvut.fit.mi_mpr_dip.admission.service;

import java.util.Date;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Term;

public interface TermService {

	public void addLinks(Term term);

	public Term getTerm(String dateOfTerm, String room);

	public Term getTerm(Date dateOfTerm, String room);

	public Date getDate(String dateOfTerm);

}
