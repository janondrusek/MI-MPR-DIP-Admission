package cz.cvut.fit.mi_mpr_dip.admission.builder;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admissions;

public interface AdmissionsBuilder extends Builder<Admissions> {

	public void buildLimit(Integer page, Integer count);

	public void buildAdmissions();
}
