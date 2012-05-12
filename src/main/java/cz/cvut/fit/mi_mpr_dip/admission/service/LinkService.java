package cz.cvut.fit.mi_mpr_dip.admission.service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Link;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Term;

public interface LinkService {

	public Link getAdmissionLink(Admission admission);

	public Link getTermLink(Term term);
}
