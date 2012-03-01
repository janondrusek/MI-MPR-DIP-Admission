package cz.cvut.fit.mi_mpr_dip.admission.endpoint.action;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;

public interface AdmissionAction<T> {

	public void performAction(Admission admission, T actor);
}
