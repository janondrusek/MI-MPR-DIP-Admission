package cz.cvut.fit.mi_mpr_dip.admission.builder;

import java.util.List;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Admissions;

public interface AdmissionsBuilder extends Builder<Admissions> {

	public void buildLimit(Integer count, Integer page);

	public void buildAdmissions();

	public void buildAdmissions(List<Admission> admissions);
}
