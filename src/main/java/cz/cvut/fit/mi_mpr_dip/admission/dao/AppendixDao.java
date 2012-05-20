package cz.cvut.fit.mi_mpr_dip.admission.dao;

import cz.cvut.fit.mi_mpr_dip.admission.dao.persistence.AppendixUniqueConstraint;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Appendix;

public interface AppendixDao {

	public Appendix getAppendix(String identifier);

	public Appendix getAppendix(AppendixUniqueConstraint uniqueConstraint);
}
