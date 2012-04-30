package cz.cvut.fit.mi_mpr_dip.admission.dao;

import java.util.Date;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Term;

public interface TermDao {

	public Term getTerm(Date dateOfTerm, String room);
}
