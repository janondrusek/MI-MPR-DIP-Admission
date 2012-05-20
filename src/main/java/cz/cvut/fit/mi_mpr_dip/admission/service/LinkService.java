package cz.cvut.fit.mi_mpr_dip.admission.service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Appendix;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Link;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Term;

public interface LinkService {

	public Link getAdmissionLink(Admission admission);

	public Link getAppendixLink(String admissionCode, Appendix appendix);

	public Link getAppendixLink(String admissionCode, Term term, Appendix appendix);

	public Link getTermLink(Term term);
}
