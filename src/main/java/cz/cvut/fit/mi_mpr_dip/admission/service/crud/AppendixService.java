package cz.cvut.fit.mi_mpr_dip.admission.service.crud;

import java.util.Set;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Appendix;
import cz.cvut.fit.mi_mpr_dip.admission.domain.TermRegistration;

public interface AppendixService extends CrudService<Appendix> {

	public void addLinks(Admission admission);

	public void addLinks(TermRegistration termRegistration);

	public void addContent(Appendix appendix);

	public void addContents(Set<Appendix> appendices);

	public void addIdentifier(Appendix appendix);

	public void addIdentifiers(Set<Appendix> appendices);
}
